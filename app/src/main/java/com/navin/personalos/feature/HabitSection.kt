package com.navin.personalos.feature

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.navin.personalos.core.database.*
import com.navin.personalos.core.designsystem.*
import com.navin.personalos.core.domain.RecurrenceEngine
import java.time.LocalDate

@Composable fun DueHabits(vm: PersonalViewModel,date: LocalDate,open: (EntityType,String)->Unit) {
    val habits by vm.repository.dao.observeHabit().collectAsStateWithLifecycle(emptyList())
    val rules by vm.repository.dao.observeRecurrenceRule().collectAsStateWithLifecycle(emptyList())
    val events by vm.repository.dao.observeHabitEvent().collectAsStateWithLifecycle(emptyList())
    val due=habits.filter {it.status==ActiveStatus.ACTIVE && rules.firstOrNull {r -> r.id==it.scheduleRuleId}?.let {r -> RecurrenceEngine().dates(r,date,date).isNotEmpty()}==true}
    if(due.isEmpty()) return
    SectionTitle("Your rhythms")
    due.forEach {habit -> key(habit.id) {
        val event=events.firstOrNull {it.habitId==habit.id && it.localDate==date.toString()}
        CalmCard {
            TextButton(onClick={open(EntityType.HABIT,habit.id)}) {Text(habit.title)}
            Text(event?.let {"${it.state.name.lowercase()}${it.value?.let {v -> " · $v ${habit.unit.orEmpty()}"}.orEmpty()}"} ?: "Scheduled for this day")
            if(habit.targetType==TargetType.CHECK && date<=LocalDate.now()) TextButton(onClick={vm.act("Habit updated") {if(event?.state==HabitEventState.COMPLETED) vm.repository.dao.deleteHabitEvent(event.id) else vm.repository.logHabit(habit.id,date,HabitEventState.COMPLETED)}}) {Text(if(event?.state==HabitEventState.COMPLETED) "Undo completion" else "Mark done")}
        }
    } }
}
@Composable fun HabitLogControls(vm: PersonalViewModel,id: String,active: Boolean,onChanged: ()->Unit) {
    var date by rememberSaveable {mutableStateOf(LocalDate.now().toString())};var amount by rememberSaveable {mutableStateOf("")}
    var habit by remember {mutableStateOf<Habit?>(null)}
    LaunchedEffect(id) {habit=vm.repository.dao.getHabit(id)}
    DateField("Record a day",date,{if(it.isNotBlank()) date=it})
    if(habit?.targetType!=TargetType.CHECK) Field("Recorded amount (${habit?.unit.orEmpty()})",amount,{amount=it})
    PrimaryButton("Log day",active) {vm.act("Day recorded") {vm.repository.logHabit(id,LocalDate.parse(date),HabitEventState.COMPLETED,amount.takeIf {it.isNotBlank()}?.toDoubleOrNull());onChanged()}}
    TextButton(enabled=active,onClick={vm.act("Day skipped") {vm.repository.logHabit(id,LocalDate.parse(date),HabitEventState.SKIPPED);onChanged()}}) {Text("Skip day")}
    TextButton(onClick={vm.act("Day entry removed") {vm.repository.dao.allHabitEvent().firstOrNull {it.habitId==id && it.localDate==date}?.let {vm.repository.dao.deleteHabitEvent(it.id)};onChanged()}}) {Text("Clear this day's entry")}
}
