package com.navin.personalos.feature

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.speech.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.navin.personalos.core.domain.*
import java.time.*
import java.util.UUID

@Composable fun DateField(label: String,value: String,onChange: (String)->Unit) {
    val context=LocalContext.current
    Column { Text(label,style=MaterialTheme.typography.labelMedium);Row {
        OutlinedButton(onClick={val d=runCatching { LocalDate.parse(value) }.getOrDefault(LocalDate.now());DatePickerDialog(context,{_,y,m,day -> onChange(LocalDate.of(y,m+1,day).toString())},d.year,d.monthValue-1,d.dayOfMonth).show()},modifier=Modifier.weight(1f).heightIn(min=48.dp)) { Text(value.ifBlank { "Choose date" }) }
        if(value.isNotBlank()) TextButton(onClick={onChange("")}) {Text("Clear")}
    } }
}
@Composable fun TimeField(label: String,value: String,onChange: (String)->Unit) {
    val context=LocalContext.current
    Column {Text(label,style=MaterialTheme.typography.labelMedium);Row {
        OutlinedButton(onClick={val t=runCatching { LocalTime.parse(value) }.getOrDefault(LocalTime.of(9,0));TimePickerDialog(context,{_,h,m -> onChange(LocalTime.of(h,m).toString())},t.hour,t.minute,true).show()},modifier=Modifier.weight(1f).heightIn(min=48.dp)) {Text(value.ifBlank {"Choose time"})}
        if(value.isNotBlank()) TextButton(onClick={onChange("")}) {Text("Clear")}
    }}
}
@Composable fun ContextPicker(label: String,type: EntityType,records: List<Record>,id: String?,onChange: (String?)->Unit) {
    val choices=listOf<String?>(null)+records.filter { it.type==type && (it.status!="ARCHIVED" || it.id==id) }.map {it.id}
    Choice(label,choices,id,onChange) { selected -> if(selected==null) "None" else records.firstOrNull {it.id==selected}?.title ?: "Unavailable context" }
}
@Composable fun Editor(vm: PersonalViewModel,records: List<Record>,initial: Draft,editing: Boolean,close: ()->Unit) {
    var d by rememberSaveable(initial.id) { mutableStateOf(initial) };val busy by vm.busy.collectAsStateWithLifecycle();var saved by rememberSaveable { mutableStateOf(false) }
    val type=d.type
    LazyColumn(Modifier.imePadding(),contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
        item { HeaderBack(if(editing) "Edit ${type.label().lowercase()}" else "New ${type.label().lowercase()}",close) }
        item { Field(if(type==EntityType.JOURNAL) "Title (optional)" else "Title",d.title,{d=d.copy(title=it)}) }
        if(type !in listOf(EntityType.REMINDER,EntityType.MILESTONE)) item {Field(if(type in listOf(EntityType.JOURNAL,EntityType.IDEA,EntityType.MEMORY,EntityType.WEEKLY_REVIEW)) "Your words" else "Notes / description",d.body,{d=d.copy(body=it)},true)}
        if(type in listOf(EntityType.TASK,EntityType.REMINDER,EntityType.PROJECT,EntityType.GOAL,EntityType.MILESTONE,EntityType.JOURNAL,EntityType.SESSION,EntityType.MEMORY,EntityType.CHAPTER,EntityType.HABIT)) item {
            DateField(if(type==EntityType.TASK) "Due date" else if(type==EntityType.CHAPTER) "Start date" else "Date",d.date,{d=d.copy(date=it)})
            if(type in listOf(EntityType.TASK,EntityType.REMINDER,EntityType.SESSION,EntityType.HABIT)) TimeField(when(type) {EntityType.TASK -> "Due time (optional)";EntityType.HABIT -> "Reminder time (optional)";else -> "Time"},d.time,{d=d.copy(time=it)})
        }
        if(type==EntityType.TASK) {
            item { Choice("Priority",Priority.entries,d.priority,{d=d.copy(priority=it)}) {it.name.lowercase().replaceFirstChar(Char::titlecase)} }
            item { DateField("Reminder date (separate from due date)",d.reminderDate,{d=d.copy(reminderDate=it)});if(d.reminderDate.isNotBlank()) TimeField("Reminder time",d.reminderTime,{d=d.copy(reminderTime=it)}) }
            item {ContextPicker("Parent task",EntityType.TASK,records.filter {it.id!=d.id},d.parentTaskId,{d=d.copy(parentTaskId=it)})}
        }
        if(type in listOf(EntityType.TASK,EntityType.REMINDER,EntityType.HABIT)) item {
            Choice("Repeats",listOf<Frequency?>(null)+Frequency.entries,d.frequency,{d=d.copy(frequency=it)}) {it?.name?.lowercase()?.replaceFirstChar(Char::titlecase) ?: "Does not repeat"}
            if(d.frequency!=null) {
                Field("Every N ${d.frequency!!.name.lowercase().removeSuffix("ly")} periods",d.interval.toString(),{it.toIntOrNull()?.let {n -> d=d.copy(interval=n)}})
                if(d.frequency==Frequency.WEEKLY) DayOfWeek.entries.forEach { day ->
                    val selected=(d.weekdaysMask ?: (1 shl ((runCatching { LocalDate.parse(d.date) }.getOrDefault(LocalDate.now())).dayOfWeek.value-1))) and (1 shl (day.value-1))!=0
                    Toggle(day.name.lowercase().replaceFirstChar(Char::titlecase),selected,{checked -> val mask=d.weekdaysMask ?: (1 shl ((runCatching {LocalDate.parse(d.date)}.getOrDefault(LocalDate.now())).dayOfWeek.value-1));d=d.copy(weekdaysMask=if(checked) mask or (1 shl(day.value-1)) else mask and (1 shl(day.value-1)).inv())})
                }
                DateField("Ends on (optional)",d.endDate,{d=d.copy(endDate=it)});Field("Occurrence limit (optional)",d.countLimit,{d=d.copy(countLimit=it)})
                if(d.frequency==Frequency.MONTHLY) Text("Months without your chosen day use their last valid day.",style=MaterialTheme.typography.labelMedium)
            }
        }
        if(type==EntityType.HABIT) item {
            Choice("Daily target",TargetType.entries,d.habitTarget,{d=d.copy(habitTarget=it)}) {it.name.lowercase()}
            if(d.habitTarget!=TargetType.CHECK) {Field("Target amount",d.target,{d=d.copy(target=it)});Field("Unit",d.unit,{d=d.copy(unit=it)})}
        }
        if(type==EntityType.GOAL) item {
            Field("Why it matters",d.why,{d=d.copy(why=it)},true);Field("What success looks like",d.success,{d=d.copy(success=it)},true)
            Choice("Progress measurement",MeasurementType.entries,d.measurement,{d=d.copy(measurement=it)}) {it.name.lowercase().replace('_',' ')}
            if(d.measurement!=MeasurementType.NONE) {Field("Current value",d.current,{d=d.copy(current=it)});Field("Target value",d.target,{d=d.copy(target=it)});Field("Unit",d.unit,{d=d.copy(unit=it)})}
        }
        if(type==EntityType.SESSION) item {Field("Duration in minutes",d.duration,{d=d.copy(duration=it)});ContextPicker("Hobby",EntityType.HOBBY,records,d.hobbyId,{d=d.copy(hobbyId=it)});ContextPicker("Skill",EntityType.SKILL,records,d.skillId,{d=d.copy(skillId=it)})}
        if(type==EntityType.SKILL) item {Field("Current focus",d.currentFocus,{d=d.copy(currentFocus=it)});ContextPicker("Hobby",EntityType.HOBBY,records,d.hobbyId,{d=d.copy(hobbyId=it)})}
        if(type==EntityType.CHAPTER) item {DateField("End date (optional)",d.endDate,{d=d.copy(endDate=it)})}
        if(type !in listOf(EntityType.LIFE_AREA,EntityType.REMINDER,EntityType.WEEKLY_REVIEW,EntityType.MILESTONE)) item {ContextPicker("Life area",EntityType.LIFE_AREA,records,d.lifeAreaId,{d=d.copy(lifeAreaId=it)})}
        if(type in listOf(EntityType.TASK,EntityType.MILESTONE,EntityType.JOURNAL,EntityType.IDEA,EntityType.MEMORY,EntityType.SESSION)) item {ContextPicker("Project",EntityType.PROJECT,records,d.projectId,{d=d.copy(projectId=it)})}
        if(type in listOf(EntityType.TASK,EntityType.PROJECT,EntityType.MILESTONE,EntityType.HABIT,EntityType.JOURNAL)) item {ContextPicker("Goal",EntityType.GOAL,records,d.goalId,{d=d.copy(goalId=it)})}
        item {PrimaryButton(if(busy) "Saving…" else "Save ${type.label().lowercase()}",!busy && !saved) {vm.act(if(type==EntityType.REMINDER || d.reminderDate.isNotBlank()) "Saved. Delivery depends on Android permissions; check the reminder status." else "Saved") {vm.repository.save(d);saved=true;close()}}}
    }
}
@Composable fun CaptureScreen(vm: PersonalViewModel,records: List<Record>,context: EntityType?,close: ()->Unit) {
    var text by rememberSaveable {mutableStateOf("")};var draft by rememberSaveable {mutableStateOf<Draft?>(null)};var ambiguity by rememberSaveable {mutableStateOf<String?>(null)}
    val id=rememberSaveable {UUID.randomUUID().toString()};val androidContext=LocalContext.current
    var speechState by remember {mutableStateOf("")}
    val recognizer=remember { if(SpeechRecognizer.isRecognitionAvailable(androidContext)) SpeechRecognizer.createSpeechRecognizer(androidContext) else null }
    DisposableEffect(recognizer) {onDispose {recognizer?.destroy()}}
    fun listen() {
        val r=recognizer
        if(r==null) {speechState="Voice recognition isn't available on this device. You can type instead.";return}
        r.setRecognitionListener(object: RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {speechState="Listening…"}
            override fun onBeginningOfSpeech() {} override fun onRmsChanged(rmsdB: Float) {} override fun onBufferReceived(buffer: ByteArray?) {} override fun onEndOfSpeech() {speechState="Processing voice…"}
            override fun onError(error: Int) {speechState="Couldn't recognize that. Try again or type your thought."}
            override fun onResults(results: Bundle?) {results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull()?.let {text=it};speechState="Check the text before saving."}
            override fun onPartialResults(partialResults: Bundle?) {partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull()?.let {text=it}}
            override fun onEvent(eventType: Int,params: Bundle?) {}
        })
        r.startListening(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM).putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true).putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE,true))
    }
    val permission=rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {if(it) listen() else speechState="Microphone access was denied. Typing works without it."}
    val d=draft
    if(d!=null) {
        Column { if(ambiguity!=null) Text(ambiguity!!,Modifier.padding(20.dp));Row(Modifier.padding(horizontal=20.dp)) { TextButton(onClick={draft=null}) {Text("Edit original thought")} }
            Choice("What should this be?",listOf(EntityType.TASK,EntityType.REMINDER,EntityType.JOURNAL,EntityType.IDEA,EntityType.SESSION,EntityType.MEMORY),d.type,{draft=d.copy(type=it)}) {it.label()}
            key(d.type) {Editor(vm,records,d,false,close)}
        }
    } else LazyColumn(Modifier.imePadding(),contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(20.dp)) {
        item {HeaderBack("What's on your mind?",close);Text("A thought first. Details can follow.",color=MaterialTheme.colorScheme.onSurfaceVariant)}
        item {Field("Capture a thought or action",text,{text=it},true)}
        item {OutlinedButton(onClick={permission.launch(Manifest.permission.RECORD_AUDIO)},modifier=Modifier.heightIn(min=48.dp)) {Text("Use voice")};if(speechState.isNotBlank()) Text(speechState)}
        item {PrimaryButton("Understand",text.isNotBlank()) {
            vm.act("") {
                val p=CaptureParser().parse(text,ZonedDateTime.now(),vm.repository.dao.allProject(),vm.repository.dao.allLifeArea());ambiguity=p.ambiguity
                draft=Draft(id,context ?: p.type,p.title,p.body,p.date?.toString().orEmpty(),p.time?.toString().orEmpty(),p.priority,p.lifeAreaId,p.projectId,duration=p.durationMinutes?.toString().orEmpty(),frequency=p.frequency,weekdaysMask=p.weekdaysMask)
            }
        }}
        item {Text("You can correct every detail before saving. Nothing is saved automatically.",style=MaterialTheme.typography.labelMedium)}
    }
}

