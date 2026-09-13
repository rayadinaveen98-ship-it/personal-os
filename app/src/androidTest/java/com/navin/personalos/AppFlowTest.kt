package com.navin.personalos

import android.graphics.Bitmap
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.navin.personalos.feature.PersonalViewModel
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
    private fun tap(text: String) {ui.onNodeWithText(text).performScrollTo().performClick();ui.waitForIdle()}
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
    private fun snapshot(name: String) {
        val instrumentation=InstrumentationRegistry.getInstrumentation()
        val bitmap=instrumentation.uiAutomation.takeScreenshot() ?: return
        val directory=File(ui.activity.getExternalFilesDir(null),"qa-screenshots").apply {mkdirs()}
        File(directory,"$name.png").outputStream().use {bitmap.compress(Bitmap.CompressFormat.PNG,100,it)}
    }
}
