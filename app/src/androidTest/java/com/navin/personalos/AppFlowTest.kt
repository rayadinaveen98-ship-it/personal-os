package com.navin.personalos

import android.graphics.Bitmap
import androidx.compose.ui.test.*
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.navin.personalos.feature.PersonalViewModel
import com.navin.personalos.feature.label
import com.navin.personalos.core.database.EntityType
import com.navin.personalos.core.data.Draft
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.*
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class AppFlowTest {
    @get:Rule val ui=createAndroidComposeRule<MainActivity>()
    private lateinit var vm: PersonalViewModel
    @Before fun reset() {
        ui.runOnUiThread {vm=ViewModelProvider(ui.activity)[PersonalViewModel::class.java]}
        runBlocking {vm.reminders.cancelAll();withContext(Dispatchers.IO) {vm.repository.db.clearAllTables()};vm.preferences.clear();vm.unlocked.value=false}
        ui.waitForIdle()
    }
    private fun tap(text: String) {
        ui.onNode(SemanticsMatcher.keyIsDefined(SemanticsActions.ScrollToIndex)).performScrollToNode(hasText(text))
        ui.onNodeWithText(text).performClick();ui.waitForIdle()
    }
    private fun setupEmpty() {
        tap("Make this space mine")
        repeat(4) {tap("Skip for now")}
        tap("Enter Personal OS")
        ui.waitUntil(10_000) {ui.onAllNodesWithText("Your personal space").fetchSemanticsNodes().isNotEmpty() || ui.onAllNodesWithText("Nothing is asking for your attention right now.").fetchSemanticsNodes().isNotEmpty()}
    }
    @Test fun setupDoesNotSeedHistoryAndTaskSavePersistsExactlyOnce() {
        setupEmpty()
        runBlocking {Assert.assertTrue(vm.repository.dao.allTask().isEmpty());Assert.assertTrue(vm.repository.dao.allSession().isEmpty());Assert.assertTrue(vm.repository.dao.allJournalEntry().isEmpty())}
        ui.onNodeWithText("Plan",useUnmergedTree=true).performClick();tap("Add task")
        ui.onNodeWithText("Title").performTextInput("Synthetic UI action")
        tap("Save task")
        ui.waitUntil(10_000) {runBlocking {vm.repository.dao.allTask().size==1}}
        runBlocking {Assert.assertEquals("Synthetic UI action",vm.repository.dao.allTask().single().title)}
        ui.onNodeWithText("Synthetic UI action").performScrollTo().performClick()
        tap("Mark complete")
        ui.waitUntil(10_000) {ui.onAllNodesWithText("Reopen task").fetchSemanticsNodes().isNotEmpty()}
        tap("Reopen task")
        ui.waitUntil(10_000) {runBlocking {vm.repository.dao.allTask().single().status.name=="OPEN"}}
    }
    @Test fun mainDestinationsAndSettingsRemainReachable() {
        setupEmpty()
        listOf("Plan","Journey","Me","Today").forEach {title -> ui.onNodeWithText(title,useUnmergedTree=true).performClick();ui.waitForIdle();snapshot(title.lowercase())}
        ui.onNodeWithText("Me",useUnmergedTree=true).performClick();tap("Settings & profile")
        ui.onNodeWithText("Preferred name").performTextInput("Synthetic profile")
        tap("Save identity")
        ui.waitUntil(10_000) {runBlocking {vm.preferences.flow.first().name=="Synthetic profile"}}
        snapshot("settings")
    }
    @Test fun requiredWidthsFontsAndThemesRemainNavigable() {
        if(android.os.Build.VERSION.SDK_INT!=36) return
        setupEmpty()
        val automation=InstrumentationRegistry.getInstrumentation().uiAutomation
        fun shell(command: String) {automation.executeShellCommand(command).use {descriptor -> android.os.ParcelFileDescriptor.AutoCloseInputStream(descriptor).use {it.readBytes()}}}
        try {
            for((width,font,theme) in listOf(Triple(360,"1.5","LIGHT"),Triple(390,"1.0","DARK"),Triple(411,"1.3","SYSTEM"))) {
                shell("wm size 1080x2160");shell("wm density ${1080*160/width}");shell("settings put system font_scale $font")
                runBlocking {vm.preferences.text("theme",theme);vm.preferences.flag("reducedMotion",true)}
                ui.activityRule.scenario.recreate();ui.waitForIdle()
                listOf("Today","Plan","Journey","Me").forEach {tab ->
                    ui.onNodeWithText(tab,useUnmergedTree=true).assertIsDisplayed().performClick();ui.waitForIdle();snapshot("${width}dp-font${font}-${theme.lowercase()}-${tab.lowercase()}")
                }
            }
        } finally {shell("wm size reset");shell("wm density reset");shell("settings put system font_scale 1.0")}
    }
    @Test fun reminderPermissionStateAndRealAlarmDelivery() {
        setupEmpty()
        val automation=InstrumentationRegistry.getInstrumentation().uiAutomation
        val context=ui.activity.applicationContext
        val id=java.util.UUID.randomUUID().toString()
        if(android.os.Build.VERSION.SDK_INT>=31) automation.executeShellCommand("appops set ${context.packageName} SCHEDULE_EXACT_ALARM allow").use {android.os.ParcelFileDescriptor.AutoCloseInputStream(it).use {stream -> stream.readBytes()}}
        runBlocking {
            vm.repository.dao.put(com.navin.personalos.core.database.Reminder(id=id,title="Synthetic alarm verification",triggerAt=System.currentTimeMillis()+60_000,exactRequired=true))
            vm.reminders.reconcile()
            if(android.os.Build.VERSION.SDK_INT>=33 && context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)!=android.content.pm.PackageManager.PERMISSION_GRANTED)
                Assert.assertEquals(com.navin.personalos.core.database.DeliveryState.NOTIFICATIONS_BLOCKED,vm.repository.dao.getReminder(id)!!.deliveryState)
        }
        if(android.os.Build.VERSION.SDK_INT>=33) automation.grantRuntimePermission(context.packageName,android.Manifest.permission.POST_NOTIFICATIONS)
        runBlocking {
            val reminder=vm.repository.dao.getReminder(id)!!
            vm.repository.dao.put(reminder.copy(triggerAt=System.currentTimeMillis()+5000))
            vm.reminders.reconcile()
            Assert.assertEquals(com.navin.personalos.core.database.DeliveryState.SCHEDULED,vm.repository.dao.getReminder(id)!!.deliveryState)
        }
        ui.waitUntil(30_000) {runBlocking {vm.repository.dao.getReminder(id)?.state==com.navin.personalos.core.database.ReminderState.DELIVERED}}
        val notifications=context.getSystemService(android.app.NotificationManager::class.java).activeNotifications
        Assert.assertTrue(notifications.any {it.tag==id})
        notifications.first {it.tag==id}.notification.contentIntent.send()
        ui.waitUntil(10_000) {ui.onAllNodesWithText("Synthetic alarm verification").fetchSemanticsNodes().isNotEmpty()}
        runBlocking {vm.reminders.cancelAll()}
    }
    @Test fun lockedSpaceHidesRecordsAndSecuresWindowAcrossRecreation() {
        setupEmpty()
        runBlocking {
            vm.repository.save(com.navin.personalos.core.data.Draft(java.util.UUID.randomUUID().toString(),com.navin.personalos.core.database.EntityType.TASK,title="Synthetic private action"))
            vm.preferences.flag("locked",true);vm.unlocked.value=false
        }
        ui.waitUntil(10_000) {ui.onAllNodesWithText("Your space is private.").fetchSemanticsNodes().isNotEmpty()}
        ui.onAllNodesWithText("Synthetic private action").assertCountEquals(0)
        ui.runOnUiThread {Assert.assertTrue(ui.activity.window.attributes.flags and android.view.WindowManager.LayoutParams.FLAG_SECURE != 0)}
        ui.activityRule.scenario.recreate()
        ui.waitUntil(10_000) {ui.onAllNodesWithText("Your space is private.").fetchSemanticsNodes().isNotEmpty()}
        ui.onAllNodesWithText("Synthetic private action").assertCountEquals(0)
        ui.onNodeWithText("Unlock").assertIsDisplayed()
    }
    @Test fun allEntityDetailsAndEditorsWorkOfflineOnCompactLargeText() {
        if(android.os.Build.VERSION.SDK_INT!=36) return
        setupEmpty()
        val automation=InstrumentationRegistry.getInstrumentation().uiAutomation
        fun shell(command: String) {automation.executeShellCommand(command).use {android.os.ParcelFileDescriptor.AutoCloseInputStream(it).use {stream -> stream.readBytes()}}}
        val fixtures=runBlocking {
            val project=Draft(java.util.UUID.randomUUID().toString(),EntityType.PROJECT,title="Synthetic context project")
            vm.repository.save(project)
            EntityType.entries.filter {it!=EntityType.DAILY_REVIEW}.map {type ->
                Draft(java.util.UUID.randomUUID().toString(),type,title="Synthetic ${type.label()}",body="Synthetic recorded writing for device acceptance.",
                    date=java.time.LocalDate.now().plusDays(2).toString(),time=if(type==EntityType.REMINDER) "18:00" else "",
                    projectId=if(type==EntityType.MILESTONE) project.id else null,
                    frequency=if(type==EntityType.HABIT) com.navin.personalos.core.database.Frequency.DAILY else null).also {vm.repository.save(it)}
            }
        }
        try {
            shell("cmd connectivity airplane-mode enable")
            shell("wm size 1080x1920");shell("wm density 480");shell("settings put system font_scale 1.5")
            ui.activityRule.scenario.recreate();ui.waitForIdle()
            for(draft in fixtures) {
                ui.runOnUiThread {vm.pendingDestination.value=draft.type to draft.id}
                ui.waitUntil(10_000) {ui.onAllNodesWithText("Edit").fetchSemanticsNodes().isNotEmpty()}
                snapshot("compact-large-text-${draft.type.name.lowercase()}-detail")
                tap("Edit")
                val titleLabel=if(draft.type==EntityType.JOURNAL) "Title (optional)" else "Title"
                ui.onNodeWithText(titleLabel).performScrollTo().performTextReplacement("Edited synthetic ${draft.type.label()}")
                tap("Save ${draft.type.label().lowercase()}")
                ui.waitUntil(10_000) {runBlocking {vm.repository.edit(draft.type,draft.id)?.title=="Edited synthetic ${draft.type.label()}"}}
                ui.waitUntil(10_000) {ui.onAllNodesWithText("Edit").fetchSemanticsNodes().isNotEmpty()}
                snapshot("compact-large-text-${draft.type.name.lowercase()}-saved")
                tap("Back")
            }
        } finally {shell("cmd connectivity airplane-mode disable");shell("wm size reset");shell("wm density reset");shell("settings put system font_scale 1.0")}
    }
    private fun snapshot(name: String) {
        val instrumentation=InstrumentationRegistry.getInstrumentation()
        val bitmap=instrumentation.uiAutomation.takeScreenshot() ?: return
        val directory=File(ui.activity.getExternalFilesDir(null),"qa-screenshots").apply {mkdirs()}
        File(directory,"$name.png").outputStream().use {bitmap.compress(Bitmap.CompressFormat.PNG,100,it)}
    }
}
