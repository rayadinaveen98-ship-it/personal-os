package com.navin.personalos.feature

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.navin.personalos.core.data.*
import com.navin.personalos.core.database.*
import com.navin.personalos.core.designsystem.*
import com.navin.personalos.core.domain.PersonalIntelligence
import kotlinx.coroutines.delay
import java.time.*
import java.time.temporal.TemporalAdjusters
import java.util.UUID

@Composable fun SearchScreen(vm: PersonalViewModel,open: (EntityType,String)->Unit,back: ()->Unit) {
    var query by rememberSaveable { mutableStateOf("") };var filter by rememberSaveable { mutableStateOf<EntityType?>(null) }
    var results by remember { mutableStateOf<List<SearchDocument>>(emptyList()) };var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) };var limit by rememberSaveable { mutableIntStateOf(50) }
    LaunchedEffect(query,limit,filter) {
        results=emptyList();error=null
        val tokens=Regex("[\\p{L}\\p{N}_]+").findAll(query).map { it.value }.take(20).toList()
        if(tokens.isEmpty()) { loading=false;return@LaunchedEffect }
        loading=true;delay(200)
        try { results=vm.repository.dao.searchFiltered(tokens.joinToString(" AND ") { "\"$it\"*" },filter,limit) }
        catch(e: kotlinx.coroutines.CancellationException) { throw e }
        catch(e: Exception) { error="Search couldn't load. Try again." }
        finally { loading=false }
    }
    LazyColumn(contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        item { HeaderBack("Find something in your world",back);Field("Search your records",query,{query=it;limit=50});Choice("Type",listOf(null)+listOf(EntityType.TASK,EntityType.PROJECT,EntityType.GOAL,EntityType.JOURNAL,EntityType.IDEA,EntityType.MEMORY,EntityType.CHAPTER,EntityType.HOBBY,EntityType.SKILL,EntityType.SESSION,EntityType.LIFE_AREA,EntityType.WEEKLY_REVIEW),filter,{filter=it;limit=50}) { it?.label() ?: "All types" } }
        if(loading) item { CircularProgressIndicator() }
        if(error!=null) item { Text(error!!) }
        if(query.isBlank()) item { Text("Search titles, writing, and linked contexts. Everything stays on this device.") }
        if(query.isNotBlank() && !loading && error==null && results.none { filter==null || it.entityType==filter }) item { Text("No matching records. Try another word or type.") }
        items(results.filter { filter==null || it.entityType==filter },key={it.rowId}) { result ->
            CalmCard(onClick={open(result.entityType,result.entityId)}) { Eyebrow(result.entityType.label());Text(result.title.orEmpty().ifBlank { result.body.orEmpty().take(100) },style=MaterialTheme.typography.titleMedium);result.body?.takeIf { it.isNotBlank() }?.let { Text(it.take(180)) } }
        }
        if(results.size==limit) item { TextButton(onClick={limit+=50}) { Text("Load more results") } }
    }
}
@Composable fun TimelineScreen(vm: PersonalViewModel,records: List<Record>,open: (EntityType,String)->Unit,back: ()->Unit) {
    val events by vm.repository.dao.observeTimelineEvent().collectAsStateWithLifecycle(emptyList())
    var filter by rememberSaveable { mutableStateOf<EntityType?>(null) };var limit by rememberSaveable { mutableIntStateOf(60) }
    val sorted=events.filter { filter==null || it.sourceType==filter }.sortedWith(compareByDescending<TimelineEvent> { it.occurredAt }.thenBy { it.id })
    LazyColumn(contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        item { HeaderBack("Your timeline",back);Text("A record of real activity, including context that is no longer available.");Choice("Show",listOf(null)+EntityType.entries,filter,{filter=it;limit=60}) { it?.label() ?: "All activity" } }
        if(sorted.isEmpty()) item { CalmCard { Text("Your story will gather here as you use your space.") } }
        items(sorted.take(limit),key={it.id}) { event ->
            val source=records.firstOrNull { it.id==event.sourceId && it.type==event.sourceType }
            CalmCard(onClick=source?.let { { open(it.type,it.id) } }) { Eyebrow(event.localDate);Text(event.titleSnapshot ?: event.sourceType?.label() ?: "Personal OS",style=MaterialTheme.typography.titleMedium);Text(event.eventType.lowercase().replace('_',' '));if(source==null && event.sourceId!=null) Text("Original item is no longer available",style=MaterialTheme.typography.labelMedium) }
        }
        if(sorted.size>limit) item { TextButton(onClick={limit+=60}) { Text("Load earlier activity") } }
    }
}
@Composable fun ReviewScreen(vm: PersonalViewModel,records: List<Record>,open: (EntityType,String)->Unit,back: ()->Unit) {
    var date by rememberSaveable { mutableStateOf(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toString()) }
    var draft by remember { mutableStateOf<Draft?>(null) };var evidence by remember { mutableStateOf("") }
    var rescheduling by remember { mutableStateOf<Task?>(null) };var selectedDate by remember { mutableStateOf("") }
    val tasks by vm.repository.dao.observeTask().collectAsStateWithLifecycle(emptyList())
    val busy by vm.busy.collectAsStateWithLifecycle()
    val start=LocalDate.parse(date).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));val end=start.plusDays(6)
    var moved by remember { mutableStateOf<List<Record>>(emptyList()) }
    LaunchedEffect(date) {
        val old=vm.repository.dao.allWeeklyReview().firstOrNull {it.periodStartLocalDate==start.toString()}
        draft=Draft(old?.id ?: UUID.randomUUID().toString(),EntityType.WEEKLY_REVIEW,old?.highlight.orEmpty(),old?.reflectionBody.orEmpty(),date=start.toString())
    }
    LaunchedEffect(date,records,tasks) {
        val dao=vm.repository.dao
        val e=PersonalIntelligence().week(start,dao.allTask(),dao.allSession(),dao.allJournalEntry(),dao.allMilestone(),dao.allIdea(),ZoneId.systemDefault())
        val completed=dao.allTimelineEvent().filter {it.eventType=="TASK_COMPLETED" && it.localDate>=start.toString() && it.localDate<=end.toString()}
        val projectIds=completed.mapNotNull {event -> tasks.firstOrNull {it.id==event.sourceId}?.projectId}.toSet()+
            dao.allSession().filter {it.localDate>=start.toString() && it.localDate<=end.toString()}.mapNotNull {it.projectId}
        moved=records.filter {it.type==EntityType.PROJECT && it.id in projectIds}
        evidence="${e.start} — ${e.end}\n${completed.size} completed actions · ${e.sessions} sessions · ${e.minutes} recorded minutes\n${e.reflectionDays} days with writing · ${e.milestones} completed milestones · ${e.ideas} ideas captured"
    }
    rescheduling?.let { task -> AlertDialog(onDismissRequest={rescheduling=null},title={Text("Reschedule action")},text={DateField("New date",selectedDate,{selectedDate=it})},confirmButton={TextButton(enabled=selectedDate.isNotBlank() && !busy,onClick={vm.act("Action rescheduled") {vm.repository.reschedule(task.id,LocalDate.parse(selectedDate));rescheduling=null}}) {Text("Reschedule")}},dismissButton={TextButton(onClick={rescheduling=null}) {Text("Cancel")}}) }
    val loops=tasks.filter {it.status==TaskStatus.OPEN && it.dueLocalDate!=null && it.dueLocalDate<=end.toString()}
    LazyColumn(contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        item {HeaderBack("A moment with your week",back);DateField("Week containing",date,{if(it.isNotBlank()) date=it});Text(evidence,style=MaterialTheme.typography.bodyMedium)}
        if(moved.isNotEmpty()) item {Eyebrow("What moved forward")}
        items(moved,key={"project-${it.id}"}) {record -> CalmCard(onClick={open(record.type,record.id)}) {Text(record.title);Text("Recorded actions or sessions this week");TextButton(enabled=!busy,onClick={vm.act("Project paused") {vm.repository.status(EntityType.PROJECT,record.id,"PAUSED")}}) {Text("Pause project")}}}
        item {Eyebrow("Open loops");if(loops.isEmpty()) Text("No dated actions to carry forward. You can still note what mattered.")}
        items(loops,key={"loop-${it.id}"}) {task -> CalmCard {
            TextButton(onClick={open(EntityType.TASK,task.id)}) {Text(task.title)}
            Text("Planned for ${task.dueLocalDate}")
            TextButton(enabled=!busy,onClick={vm.act("Carried to next week") {vm.repository.reschedule(task.id,start.plusWeeks(1))}}) {Text("Carry to next week")}
            TextButton(enabled=!busy,onClick={selectedDate=task.dueLocalDate.orEmpty();rescheduling=task}) {Text("Reschedule")}
            TextButton(enabled=!busy,onClick={vm.act("Action cancelled") {vm.repository.status(EntityType.TASK,task.id,"CANCELLED")}}) {Text("No longer needed")}
            TextButton(enabled=!busy,onClick={vm.act("Focus updated") {vm.repository.pin(task.id,!task.pinnedFocus)}}) {Text(if(task.pinnedFocus) "Clear chosen focus" else "Choose as focus")}
        }}
        draft?.let {d -> item {CalmCard {Eyebrow("Optional reflection");Field("What felt worth the effort?",d.title,{draft=d.copy(title=it)});Field("What deserves your attention next?",d.body,{draft=d.copy(body=it)},multiline=true);PrimaryButton("Close week",!busy) {vm.act("Week closed") {vm.repository.save(requireNotNull(draft));back()}}}}}
    }
}
@Composable fun SettingsScreen(vm: PersonalViewModel,p: PersonalPreferences,back: ()->Unit) {
    val context=LocalContext.current
    val activity=LocalActivity.current as androidx.fragment.app.FragmentActivity
    var name by rememberSaveable { mutableStateOf(p.name) };var description by rememberSaveable { mutableStateOf(p.description) }
    val permission=rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { vm.act("Notification delivery status refreshed") {} }
    val busy by vm.busy.collectAsStateWithLifecycle()
    var preview by remember { mutableStateOf<RestorePreview?>(null) };var deleteConfirmation by remember { mutableStateOf(false) };var deleteText by rememberSaveable { mutableStateOf("") }
    val export=rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/json")) { uri -> uri?.let { vm.act("Backup exported. External attachment files are referenced, not embedded.") { vm.backup.export(it) } } }
    val restore=rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri -> uri?.let { vm.act("") { preview=vm.backup.preview(it) } } }
    preview?.let { candidate -> AlertDialog(onDismissRequest={preview=null},title={Text("Replace your space from backup?")},text={Text("Validated ${candidate.records} records, exported ${Instant.ofEpochMilli(candidate.exportedAt).atZone(ZoneId.systemDefault()).toLocalDate()}. This replaces current records and preferences. ${candidate.externalAttachments} external file references are included; access to original files is not restored automatically. Export your current space first to keep it.")},confirmButton={TextButton(enabled=!busy,onClick={vm.act("Backup restored. Reminder delivery will be checked by Android.") { vm.backup.restore(candidate);preview=null }}) {Text("Replace current data")}},dismissButton={TextButton(onClick={preview=null}) {Text("Cancel")}}) }
    if(deleteConfirmation) AlertDialog(onDismissRequest={deleteConfirmation=false},title={Text("Delete all Personal OS data?")},text={Column {Text("This removes local records and preferences and cancels reminders. Original external files are kept. Type DELETE to confirm.");Field("Confirmation",deleteText,{deleteText=it})}},confirmButton={TextButton(enabled=deleteText=="DELETE" && !busy,onClick={vm.act("Your space has been cleared") {vm.backup.deleteAll();deleteConfirmation=false;vm.unlocked.value=false}}) {Text("Delete all data")}},dismissButton={TextButton(onClick={deleteConfirmation=false;deleteText=""}) {Text("Cancel")}})
    fun flag(key: String,value: Boolean) { vm.act { vm.preferences.flag(key,value) } }
    LazyColumn(contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(20.dp)) {
        item { HeaderBack("Make this space yours",back) }
        item { CalmCard { Eyebrow("Identity");Field("Preferred name",name,{name=it});Field("About you",description,{description=it});PrimaryButton("Save identity",!busy) { vm.act { vm.preferences.text("name",name.trim());vm.preferences.text("description",description.trim()) } } } }
        item { CalmCard { Eyebrow("Appearance");Choice("Theme",listOf("SYSTEM","LIGHT","DARK"),p.theme,{vm.act { vm.preferences.text("theme",it) }}) { it.lowercase().replaceFirstChar { c -> c.titlecase() } };Toggle("Observatory Friend",p.companion,{flag("companion",it)});Toggle("Reduce motion",p.reducedMotion,{flag("reducedMotion",it)}) } }
        item { CalmCard { Eyebrow("Daily rhythm");Toggle("Morning Brief",p.morning,{flag("morning",it)});TimeField("Morning time",LocalTime.ofSecondOfDay(p.morningMinutes*60L).toString(),{if(it.isNotBlank()) vm.act { vm.preferences.number("morningMinutes",LocalTime.parse(it).toSecondOfDay()/60) }});Toggle("Evening Reflection",p.evening,{flag("evening",it)});TimeField("Evening time",LocalTime.ofSecondOfDay(p.eveningMinutes*60L).toString(),{if(it.isNotBlank()) vm.act { vm.preferences.number("eveningMinutes",LocalTime.parse(it).toSecondOfDay()/60) }}) } }
        item { CalmCard { Eyebrow("Notification delivery");Text("Android permissions, channels, and battery settings can affect delivery. Saved reminders show their actual scheduling status.")
            TextButton(onClick={if(Build.VERSION.SDK_INT>=33) permission.launch(Manifest.permission.POST_NOTIFICATIONS) else context.startActivity(Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).putExtra(Settings.EXTRA_APP_PACKAGE,context.packageName))}) { Text("Allow notifications") }
            TextButton(onClick={context.startActivity(Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).putExtra(Settings.EXTRA_APP_PACKAGE,context.packageName))}) { Text("Notification settings") }
            if(Build.VERSION.SDK_INT>=31) TextButton(onClick={context.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,Uri.parse("package:${context.packageName}")))}) { Text("Allow precise reminders") }
            TextButton(onClick={vm.act("Delivery status refreshed") {}}) { Text("Check reminder delivery") }
        } }
        item { CalmCard { Eyebrow("Your data");Text("Backups contain private writing in readable JSON. Choose a destination you trust. External files are not embedded.");if(p.lastBackup>0) Text("Last export: ${Instant.ofEpochMilli(p.lastBackup).atZone(ZoneId.systemDefault()).toLocalDate()}");PrimaryButton("Export full backup",!busy) {export.launch("Personal-OS-${LocalDate.now()}.json")};OutlinedButton(enabled=!busy,onClick={restore.launch(arrayOf("application/json","text/plain","application/octet-stream"))}) {Text("Restore a backup")};TextButton(enabled=!busy,onClick={deleteConfirmation=true}) {Text("Delete all local data",color=MaterialTheme.colorScheme.error)} } }
        item { CalmCard { Eyebrow("Privacy");Toggle("App Lock",p.locked,{ enabled -> authenticate(activity,{vm.unlocked.value=true;flag("locked",enabled)},{ message -> vm.act(message) {} }) });if(p.locked) Choice("Lock after leaving",listOf(0,1,5,15),p.timeoutMinutes,{vm.act { vm.preferences.number("timeoutMinutes",it) }}) { if(it==0) "Immediately" else "$it minutes" };Toggle("Hide notification content",p.hideNotificationText,{flag("hideNotificationText",it)});Text("Your records remain on this device. Personal OS has no Internet permission. Voice availability depends on your device's recognition service.") } }
    }
}
