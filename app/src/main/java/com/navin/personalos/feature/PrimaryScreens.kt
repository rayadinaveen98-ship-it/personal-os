package com.navin.personalos.feature

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.navin.personalos.core.data.*
import com.navin.personalos.core.database.*
import com.navin.personalos.core.designsystem.*
import com.navin.personalos.core.domain.*
import java.time.*
import java.time.format.DateTimeFormatter

@Composable fun TodayScreen(vm: PersonalViewModel,p: PersonalPreferences,records: List<Record>,open: (EntityType,String)->Unit,create: (EntityType)->Unit,go: (String)->Unit) {
    var now by remember { mutableStateOf(ZonedDateTime.now()) }
    LaunchedEffect(Unit) { while(true) { now=ZonedDateTime.now();kotlinx.coroutines.delay(30000) } }
    var focus by remember { mutableStateOf<FocusSuggestion?>(null) }
    LaunchedEffect(records,now) { val dao=vm.repository.dao;focus=PersonalIntelligence().suggest(dao.allTask(),dao.allProject(),dao.allReminder(),dao.allHabit(),dao.allRecurrenceRule(),dao.allEntityLink(),now) }
    val tasks=records.filter { it.type==EntityType.TASK && it.status=="OPEN" };val today=now.toLocalDate().toString()
    val greeting=when(now.hour) { in 5..11 -> "Good morning";in 12..16 -> "Good afternoon";in 17..22 -> "Good evening";else -> "A quiet moment" }
    LazyColumn(contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        item { PageTitle("$greeting${if(p.name.isBlank()) "." else ",\n${p.name}."}","Your day");Spacer(Modifier.height(8.dp));Text(now.format(DateTimeFormatter.ofPattern("EEEE, d MMMM")),color=MaterialTheme.colorScheme.onSurfaceVariant) }
        if(p.morning && now.hour in 5..11) item { CalmCard { Text("${tasks.count { it.date==today }} tasks are due today.");Text("Your brief is based on your saved commitments.",style=MaterialTheme.typography.labelMedium) } }
        item { val f=focus
            if(f==null) QuietEmpty("Nothing is asking for your attention right now.","Choose what deserves focus, or simply leave the day open.","Capture something",{go("capture")},p.companion)
            else CalmCard(tone=1,onClick={open(f.type,f.id)}) { Eyebrow("Today's focus");Text(f.title,style=MaterialTheme.typography.titleLarge);Text(f.reason);if(p.companion) Friend(Modifier.align(Alignment.End),p.reducedMotion,"focused") }
        }
        item { SectionTitle("Keep moving");TextButton(onClick={go("plan")}) { Text("See your plan") } }
        val continuation=records.filter { it.type==EntityType.PROJECT && it.status=="ACTIVE" }.take(2)
        items(continuation,key={it.id}) { project ->
            val next=tasks.filter {it.projectId==project.id}.minByOrNull {it.date ?: "9999"}
            CalmCard(onClick={open(project.type,project.id)}) {
                Eyebrow("Pick up where you left off");Text(project.title,style=MaterialTheme.typography.titleMedium)
                Text("Last updated ${Instant.ofEpochMilli(project.updatedAt).atZone(now.zone).toLocalDate()}")
                if(next!=null) TextButton(onClick={open(next.type,next.id)}) {Text("Next: ${next.title}")} else Text("Choose the next action in this project.")
                if(project.updatedAt<now.minusDays(14).toInstant().toEpochMilli()) TextButton(onClick={vm.act("Project paused") {vm.repository.status(EntityType.PROJECT,project.id,"PAUSED")}}) {Text("Pause for now")}
            }
        }
        if(tasks.isNotEmpty()) item { CalmCard(onClick={go("list/TASK")}) { Text("${tasks.size} open tasks",style=MaterialTheme.typography.titleMedium);Text("See all your captured actions") } }
        val upcoming=records.filter { it.date!=null && it.date>=today && (it.type==EntityType.TASK && it.status=="OPEN" || it.type==EntityType.REMINDER && it.status !in listOf("CANCELLED","DELIVERED")) }.sortedBy { it.date }.take(3)
        if(upcoming.isNotEmpty()) { item { SectionTitle("Coming up") };items(upcoming,key={"up-${it.id}"}) { RecordRow(it,open) };item { TextButton(onClick={go("plan")}) { Text("View all upcoming work") } } }
        item {DueHabits(vm,now.toLocalDate(),open)}
        if(p.evening && now.hour>=17) item { CalmCard(tone=2) { Eyebrow("Close the day");Text("What was worth remembering today?",style=MaterialTheme.typography.titleLarge);PrimaryButton("Reflect",onClick={go("create/JOURNAL?date=${now.toLocalDate()}&reflection=EVENING")}) } }
        item { TextButton(onClick={go("search")}) { Text("Find something") } }
    }
}

@Composable fun PlanScreen(vm: PersonalViewModel,records: List<Record>,open: (EntityType,String)->Unit,create: (EntityType)->Unit,go: (String)->Unit) {
    var mode by rememberSaveable { mutableStateOf("Today") };var completed by rememberSaveable { mutableStateOf(false) }
    val today=LocalDate.now();val tasks=records.filter { it.type==EntityType.TASK }
    LazyColumn(contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        item { PageTitle("Make room for what matters.","Your plan");Choice("Plan view",listOf("Today","Week","Projects"),mode,{mode=it}) }
        item { Row(Modifier.horizontalScroll(rememberScrollState())) { listOf(EntityType.TASK,EntityType.GOAL,EntityType.HABIT,EntityType.REMINDER).forEach { t -> TextButton(onClick={go("list/${t.name}")}) { Text(t.label()+"s") } } } }
        if(mode=="Projects") {
            val projects=records.filter { it.type==EntityType.PROJECT && it.status!="ARCHIVED" }
            item { PrimaryButton("Create project",onClick={create(EntityType.PROJECT)}) }
            if(projects.isEmpty()) item { Text("Give ongoing work a home. Your first project can begin with just a name.") }
            items(projects,key={it.id}) { RecordRow(it,open) }
        } else {
            item { PrimaryButton("Add task",onClick={create(EntityType.TASK)}) }
            val dates=if(mode=="Week") (0..6).map { today.plusDays(it.toLong()) } else listOf(today)
            dates.forEach { day ->
                item { SectionTitle(day.format(DateTimeFormatter.ofPattern("EEEE, d MMM"))) }
                val entries=records.filter { it.date==day.toString() && ((it.type==EntityType.TASK && it.status=="OPEN") || (it.type==EntityType.REMINDER && it.status !in listOf("CANCELLED","DELIVERED"))) }
                if(entries.isEmpty()) item { Text("Nothing scheduled.",color=MaterialTheme.colorScheme.onSurfaceVariant) }
                items(entries,key={"$day-${it.id}"}) { r -> RecordRow(r,open,if(r.type==EntityType.TASK) ({vm.act("Completed") { vm.repository.completeTask(r.id,r.date) }}) else null) }
            }
            item {DueHabits(vm,today,open)}
            val overdue=tasks.filter { it.status=="OPEN" && it.date!=null && it.date<today.toString() }
            if(overdue.isNotEmpty()) { item { SectionTitle("Still open");Text("Keep, reschedule, complete or cancel when you're ready.") };items(overdue,key={"overdue-${it.id}"}) { r -> RecordRow(r,open) } }
            val undated=tasks.filter { it.status=="OPEN" && it.date==null }
            if(undated.isNotEmpty()) { item { SectionTitle("Without a date") };items(undated,key={"undated-${it.id}"}) { RecordRow(it,open) } }
            item { TextButton(onClick={completed=!completed}) { Text(if(completed) "Hide completed" else "Show completed") } }
            if(completed) items(tasks.filter { it.status=="COMPLETED" },key={"done-${it.id}"}) { r -> RecordRow(r,open) {vm.act("Reopened") { vm.repository.reopenTask(r.id) }} }
        }
    }
}
@Composable fun JourneyScreen(vm: PersonalViewModel,p: PersonalPreferences,records: List<Record>,open: (EntityType,String)->Unit,create: (EntityType)->Unit,go: (String)->Unit) {
    var date by rememberSaveable { mutableStateOf(LocalDate.now().toString()) };val selected=runCatching { LocalDate.parse(date) }.getOrDefault(LocalDate.now())
    val week=selected.with(java.time.temporal.TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val history=records.filter { it.date==selected.toString() && it.type in listOf(EntityType.JOURNAL,EntityType.MEMORY,EntityType.SESSION,EntityType.IDEA) }
    LazyColumn(contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        item { PageTitle("The days that make a life.","Your journey",true) }
        item { Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())) { (0..6).forEach { n -> val day=week.plusDays(n.toLong());TextButton(onClick={date=day.toString()}) { Text(day.format(DateTimeFormatter.ofPattern("EEE\nd")),color=if(day==selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant) } } } }
        item { DateField("Choose a date",date,{date=it});Row { TextButton(onClick={date=selected.minusWeeks(1).toString()}) {Text("Previous week")};TextButton(onClick={date=LocalDate.now().toString()}) {Text("Today")};TextButton(onClick={date=selected.plusWeeks(1).toString()}) {Text("Next week")} } }
        item { PrimaryButton("Write something",onClick={go("create/JOURNAL?date=$selected")}) }
        if(history.isEmpty()) item { QuietEmpty("A quiet day here.","Nothing has been recorded for this date yet.","Capture a memory",{go("create/MEMORY?date=$selected")},p.companion) }
        items(history,key={it.id}) { r -> RecordRow(r,open) }
        item { SectionTitle("Keep what matters");Row { TextButton(onClick={go("list/MEMORY")}) {Text("Memories")};TextButton(onClick={go("list/CHAPTER")}) {Text("Chapters")};TextButton(onClick={go("list/IDEA")}) {Text("Ideas")} } }
        item { CalmCard(tone=2,onClick={go("review")}) { Text("Your week, truthfully.",style=MaterialTheme.typography.titleLarge);Text("See what moved and choose what to carry forward.") } }
        item { TextButton(onClick={go("timeline")}) {Text("Life timeline & archive")};TextButton(onClick={go("search")}) {Text("Search your history")} }
    }
}
@Composable fun MeScreen(p: PersonalPreferences,records: List<Record>,open: (EntityType,String)->Unit,create: (EntityType)->Unit,go: (String)->Unit) {
    LazyColumn(contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        item { PageTitle(p.name.ifBlank { "Your personal space" },"Becoming, at your pace",true);if(p.description.isNotBlank()) Text(p.description);TextButton(onClick={go("settings")}) {Text("Settings & profile")} }
        item { SectionTitle("Your world") }
        val areas=records.filter { it.type==EntityType.LIFE_AREA && it.status=="ACTIVE" }
        items(areas,key={it.id}) { RecordRow(it,open) }
        item { TextButton(onClick={create(EntityType.LIFE_AREA)}) {Text("Add a life area")} }
        item { SectionTitle("Room to grow") }
        item { listOf(EntityType.HOBBY,EntityType.SKILL,EntityType.GOAL,EntityType.PROJECT).forEach { t -> CalmCard(onClick={go("list/${t.name}")}) { Text(t.label()+"s",style=MaterialTheme.typography.titleMedium);Text("${records.count { it.type==t && it.status=="ACTIVE" }} active") };Spacer(Modifier.height(12.dp)) } }
        val sessions=records.filter { it.type==EntityType.SESSION }
        if(sessions.isNotEmpty()) item { CalmCard(tone=1,onClick={go("list/SESSION")}) { Text("${sessions.size} sessions recorded");Text("${sessions.sumOf { it.duration ?: 0 }} minutes logged") } }
        item { TextButton(onClick={go("timeline")}) {Text("Visit your history")};TextButton(onClick={go("search")}) {Text("Find anything")} }
    }
}
@Composable fun RecordList(type: EntityType,records: List<Record>,open: (EntityType,String)->Unit,create: ()->Unit,back: ()->Unit) {
    var archived by rememberSaveable { mutableStateOf(false) }
    LazyColumn(contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        item { HeaderBack(type.label()+"s",back);PrimaryButton("Create ${type.label().lowercase()}",onClick=create);Toggle("Include archived",archived,{archived=it}) }
        val shown=records.filter { archived || it.status!="ARCHIVED" }
        if(shown.isEmpty()) item { Text("Nothing here yet. Add something when it matters to you.") }
        items(shown,key={it.id}) { RecordRow(it,open) }
    }
}

