package com.navin.personalos.feature

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.navin.personalos.core.data.*
import com.navin.personalos.core.database.*
import com.navin.personalos.core.designsystem.*
import java.time.*
import java.util.UUID

@Composable fun DetailScreen(vm: PersonalViewModel,records: List<Record>,type: EntityType,id: String,open: (EntityType,String)->Unit,edit: ()->Unit,create: (EntityType)->Unit,back: ()->Unit) {
    val r=records.firstOrNull {it.type==type && it.id==id}
    var confirm by remember {mutableStateOf<String?>(null)};var child by rememberSaveable {mutableStateOf<Draft?>(null)}
    var draft by remember {mutableStateOf<Draft?>(null)};var events by remember {mutableStateOf<List<TimelineEvent>>(emptyList())};var habitEvents by remember {mutableStateOf<List<HabitEvent>>(emptyList())};var members by remember {mutableStateOf<List<ChapterItem>>(emptyList())}
    var sessions by remember {mutableStateOf<List<Session>>(emptyList())};var children by remember {mutableStateOf<List<Task>>(emptyList())}
    var revision by remember {mutableIntStateOf(0)}
    LaunchedEffect(records,id,revision) {sessions=vm.repository.dao.allSession().filter {it.hobbyId==id || it.skillId==id};children=vm.repository.dao.allTask().filter {it.parentTaskId==id};draft=vm.repository.edit(type,id);events=vm.repository.dao.allTimelineEvent().filter {it.sourceType==type && it.sourceId==id};habitEvents=vm.repository.dao.allHabitEvent().filter {it.habitId==id};members=vm.repository.dao.allChapterItem().filter {it.chapterId==id}.sortedBy {it.orderIndex}}
    val c=child
    if(c!=null) {Editor(vm,records,c,false) {child=null};return}
    fun childCreate(t: EntityType) {child=Draft(UUID.randomUUID().toString(),t,projectId=if(type==EntityType.PROJECT) id else r?.projectId,goalId=if(type==EntityType.GOAL) id else r?.goalId,lifeAreaId=if(type==EntityType.LIFE_AREA) id else r?.lifeAreaId,parentTaskId=if(type==EntityType.TASK && t==EntityType.TASK) id else null,hobbyId=if(type==EntityType.HOBBY) id else null,skillId=if(type==EntityType.SKILL) id else null,date=if(t==EntityType.SESSION) LocalDate.now().toString() else "",frequency=if(t==EntityType.HABIT) Frequency.DAILY else null)}
    if(r==null) {Column(Modifier.padding(20.dp)) {HeaderBack("This item is no longer available.",back)};return}
    LazyColumn(contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        item {TextButton(onClick=back) {Text("Back")};PageTitle(r.title,type.label(),type in listOf(EntityType.JOURNAL,EntityType.MEMORY,EntityType.CHAPTER));Text(listOfNotNull(r.status.takeIf {it.isNotBlank()}?.lowercase(),r.date).joinToString(" · "),color=MaterialTheme.colorScheme.onSurfaceVariant)}
        item {OutlinedButton(onClick=edit) {Text("Edit")}}
        if(r.body.isNotBlank()) item {CalmCard(tone=if(type in listOf(EntityType.JOURNAL,EntityType.MEMORY)) 2 else 0) {Text(r.body,style=MaterialTheme.typography.bodyLarge,fontFamily=if(type==EntityType.JOURNAL) ReflectiveFont else null)}}
        if(type==EntityType.TASK) {
            item {PrimaryButton(if(r.status=="COMPLETED") "Reopen task" else "Mark complete",r.status!="CANCELLED") {vm.act(if(r.status=="COMPLETED") "Reopened" else "Completed") {if(r.status=="COMPLETED") vm.repository.reopenTask(id) else vm.repository.completeTask(id,r.date)}}}
            if(r.status=="OPEN") item {TextButton(onClick={vm.act(if(r.pinned) "Focus cleared" else "Focus pinned") {vm.repository.pin(id,!r.pinned)}}) {Text(if(r.pinned) "Clear Today focus" else "Make this Today's focus")}}
            item {SectionTitle("Details");Text("Priority: ${draft?.priority?.name?.lowercase()}");Text("Due: ${draft?.date?.ifBlank {"No date"}} ${draft?.time.orEmpty()}");Text("Reminder: ${draft?.reminderDate?.ifBlank {"None"}} ${draft?.reminderTime.orEmpty()}");draft?.frequency?.let {Text("Repeats ${it.name.lowercase()}")}}
            items(children,key={"child-${it.id}"}) { task -> records.firstOrNull {it.type==EntityType.TASK && it.id==task.id}?.let {RecordRow(it,open)} }
            item {TextButton(onClick={childCreate(EntityType.TASK)}) {Text("Add subtask")}}
        }
        if(type==EntityType.REMINDER) item {CalmCard(tone=1) {Text("Delivery: ${r.status.lowercase().replace('_',' ')}");Text("Saved intent and Android delivery are tracked separately.");TextButton(onClick=edit) {Text("Change reminder time")}}}
        if(type==EntityType.GOAL) item {
            draft?.let { d ->
                if(d.success.isNotBlank()) {SectionTitle("What success looks like");Text(d.success)}
                if(d.measurement!=MeasurementType.NONE) CalmCard(tone=1) {Text("${d.current} / ${d.target} ${d.unit}",style=MaterialTheme.typography.titleLarge);Text("Your chosen measurement")}
            }
        }
        if(type in listOf(EntityType.PROJECT,EntityType.GOAL)) {
            val linked=records.filter {if(type==EntityType.PROJECT) it.projectId==id else it.goalId==id}
            val next=linked.firstOrNull {it.type==EntityType.TASK && it.status=="OPEN" && it.pinned} ?: linked.filter {it.type==EntityType.TASK && it.status=="OPEN"}.minByOrNull {it.date ?: "9999"}
            item {SectionTitle("Next action");if(next!=null) RecordRow(next,open) else Text("Choose a real next action when you're ready.")}
            item {Row {TextButton(onClick={childCreate(EntityType.TASK)}) {Text("Add task")};TextButton(onClick={childCreate(EntityType.MILESTONE)}) {Text("Add milestone")}}}
            items(linked,key={it.id}) {RecordRow(it,open)}
        }
        if(type==EntityType.MILESTONE) item {PrimaryButton(if(r.status=="COMPLETED") "Reopen milestone" else "Complete milestone") {vm.act {vm.repository.status(type,id,if(r.status=="COMPLETED") "OPEN" else "COMPLETED")}}}
        if(type==EntityType.HABIT) {
            item {Text("Repeats ${draft?.frequency?.name?.lowercase() ?: "on its saved schedule"}");PrimaryButton("Log today",r.status=="ACTIVE") {vm.act("Logged") {vm.repository.logHabit(id,LocalDate.now(),HabitEventState.COMPLETED);revision++}};TextButton(onClick={vm.act("Skipped today") {vm.repository.logHabit(id,LocalDate.now(),HabitEventState.SKIPPED);revision++}}) {Text("Skip today")}}
            item {SectionTitle("Recorded days");Text("${habitEvents.count {it.state==HabitEventState.COMPLETED}} completed days. Missed days don't create debt.")}
            items(habitEvents.sortedByDescending {it.localDate},key={it.id}) {Text("${it.localDate} · ${it.state.name.lowercase()}")}
        }
        if(type in listOf(EntityType.HOBBY,EntityType.SKILL)) {
            item {draft?.currentFocus?.takeIf {it.isNotBlank()}?.let {CalmCard(tone=1) {Eyebrow("Current focus");Text(it)}};PrimaryButton("Log a session",onClick={childCreate(EntityType.SESSION)})}
            if(sessions.isNotEmpty()) item {CalmCard {Text("${sessions.size} sessions · ${sessions.sumOf {it.durationMinutes ?: 0}} minutes logged")}}
            items(sessions,key={"session-${it.id}"}) {s -> records.firstOrNull {it.type==EntityType.SESSION && it.id==s.id}?.let {RecordRow(it,open)}}
        }
        if(type==EntityType.SESSION) item {Text("${r.duration?.let {"$it minutes logged"} ?: "No duration recorded"}")}
        if(type in listOf(EntityType.JOURNAL,EntityType.SESSION)) item {PrimaryButton("Save as memory") {vm.act("Memory saved") {val target=vm.repository.saveMemory(type,id);open(EntityType.MEMORY,target)}}}
        if(type==EntityType.IDEA) item {SectionTitle("Turn this into something");Text("The original idea stays here.");TextButton(onClick={vm.act {open(EntityType.TASK,vm.repository.convertIdea(id,EntityType.TASK))}}) {Text("Create a task")};TextButton(onClick={vm.act {open(EntityType.PROJECT,vm.repository.convertIdea(id,EntityType.PROJECT))}}) {Text("Create a project")}}
        if(type==EntityType.LIFE_AREA) {
            item {SectionTitle("Active in this area");Row {listOf(EntityType.PROJECT,EntityType.GOAL,EntityType.TASK).forEach {t -> TextButton(onClick={childCreate(t)}) {Text("Add ${t.label().lowercase()}")}}} }
            items(records.filter {it.lifeAreaId==id},key={it.id}) {RecordRow(it,open)}
        }
        if(type==EntityType.CHAPTER) {
            items(members,key={"member-${it.id}"}) { member ->
                val record=records.firstOrNull {it.type==member.entityType && it.id==member.entityId}
                CalmCard {
                    if(record!=null) TextButton(onClick={open(record.type,record.id)}) {Text(record.title)} else Text("Original item unavailable")
                    Row {
                        val position=members.indexOf(member)
                        TextButton(enabled=position>0,onClick={vm.act("Order updated") {val order=members.map {it.id}.toMutableList();java.util.Collections.swap(order,position,position-1);vm.repository.reorderChapter(id,order);revision++}}) {Text("Move up")}
                        TextButton(enabled=position<members.lastIndex,onClick={vm.act("Order updated") {val order=members.map {it.id}.toMutableList();java.util.Collections.swap(order,position,position+1);vm.repository.reorderChapter(id,order);revision++}}) {Text("Move down")}
                    }
                }
            }
            item {PrimaryButton(if(r.status=="COMPLETED") "Reopen chapter" else "Complete chapter") {vm.act {vm.repository.status(type,id,if(r.status=="COMPLETED") "ACTIVE" else "COMPLETED")}}}
            item {SectionTitle("Chosen moments");Text("Select the records you want this chapter to hold.")}
            items(records.filter {it.type in listOf(EntityType.MEMORY,EntityType.JOURNAL,EntityType.SESSION,EntityType.PROJECT,EntityType.GOAL,EntityType.MILESTONE)},key={it.id}) {candidate ->
                val selected=members.any {it.entityType==candidate.type && it.entityId==candidate.id}
                SelectRow(candidate.title,selected) {vm.act("Chapter updated") {vm.repository.chapterMember(id,candidate.type,candidate.id,!selected);revision++}}
            }
        }
        if(type in listOf(EntityType.JOURNAL,EntityType.MEMORY,EntityType.IDEA,EntityType.PROJECT)) item {AttachmentSection(vm,type,id)}
        item {ConnectionsSection(vm,type,id,records,open)}
        val contextIds=listOfNotNull(r.projectId?.let {EntityType.PROJECT to it},r.goalId?.let {EntityType.GOAL to it},r.lifeAreaId?.let {EntityType.LIFE_AREA to it})
        if(contextIds.isNotEmpty()) item {SectionTitle("Connected to");contextIds.forEach {(t,i) -> records.firstOrNull {it.type==t && it.id==i}?.let {record -> TextButton(onClick={open(t,i)}) {Text(record.title)}}}
        if(events.isNotEmpty()) {item {SectionTitle("History")};items(events.sortedByDescending {it.occurredAt},key={it.id}) {Text("${it.localDate} · ${it.eventType.lowercase().replace('_',' ')}")}}
        if(type in listOf(EntityType.PROJECT,EntityType.GOAL,EntityType.HABIT,EntityType.HOBBY,EntityType.SKILL)) item {
            if(r.status in listOf("ACTIVE","PAUSED")) TextButton(onClick={vm.act {vm.repository.status(type,id,if(r.status=="PAUSED") "ACTIVE" else "PAUSED")}}) {Text(if(r.status=="PAUSED") "Resume" else "Pause")}
            if(type==EntityType.PROJECT || type==EntityType.GOAL) TextButton(onClick={confirm=if(type==EntityType.PROJECT) "COMPLETED" else "ACHIEVED"}) {Text(if(type==EntityType.PROJECT) "Complete project" else "Mark goal achieved")}
        }
        if(type in listOf(EntityType.PROJECT,EntityType.GOAL,EntityType.HABIT,EntityType.HOBBY,EntityType.SKILL,EntityType.CHAPTER,EntityType.LIFE_AREA,EntityType.IDEA)) item {
            TextButton(onClick={confirm=if(r.status=="ARCHIVED") "ACTIVE" else "ARCHIVED"}) {Text(if(r.status=="ARCHIVED") "Restore from archive" else "Archive")}
        }
        if(type in listOf(EntityType.TASK,EntityType.JOURNAL,EntityType.IDEA,EntityType.MEMORY,EntityType.SESSION)) item {TextButton(onClick={confirm="DELETE"}) {Text("Delete",color=MaterialTheme.colorScheme.error)}}
        if(type==EntityType.REMINDER || type==EntityType.TASK && r.status=="OPEN") item {TextButton(onClick={confirm="CANCELLED"}) {Text("Cancel ${type.label().lowercase()}")}}
    }
    confirm?.let {action -> AlertDialog(onDismissRequest={confirm=null},title={Text(if(action=="DELETE") "Delete this record?" else "${action.lowercase().replaceFirstChar(Char::titlecase)} this record?")},text={Text(if(action=="DELETE") "This removes the record and its links. Your original external files are not deleted." else "Related records and history are preserved.")},confirmButton={TextButton(onClick={vm.act {if(action=="DELETE") {vm.repository.delete(type,id);back()} else vm.repository.status(type,id,action);confirm=null}}) {Text("Confirm")}},dismissButton={TextButton(onClick={confirm=null}) {Text("Keep as it is")}})}
}

