package com.navin.personalos.feature

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.*
import com.navin.personalos.core.data.*
import com.navin.personalos.core.database.*
import com.navin.personalos.core.designsystem.*
import com.navin.personalos.core.domain.*
import kotlinx.coroutines.launch
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.UUID

fun EntityType.label(): String = name.lowercase().replace('_',' ').replaceFirstChar { it.titlecase() }.let { if(it=="Journal") "Journal" else it }
fun route(type: EntityType,id: String)="detail/${type.name}/$id"

@Composable fun PersonalApp(vm: PersonalViewModel) {
    val prefs by vm.prefs.collectAsStateWithLifecycle();val records by vm.records.collectAsStateWithLifecycle()
    PersonalTheme(prefs?.theme ?: "SYSTEM") {
        val snackbar=remember { SnackbarHostState() }
        LaunchedEffect(Unit) { for(message in vm.events) snackbar.showSnackbar(message) }
        val p=prefs
        if(p==null) { Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),contentAlignment=Alignment.Center) { CircularProgressIndicator() };return@PersonalTheme }
        val unlocked by vm.unlocked.collectAsStateWithLifecycle()
        if(p.locked && !unlocked) { LockScreen(vm);return@PersonalTheme }
        if(!p.ready) { Scaffold(snackbarHost={SnackbarHost(snackbar)}) { padding -> Onboarding(vm,Modifier.padding(padding)) };return@PersonalTheme }
        val nav=rememberNavController();val entry by nav.currentBackStackEntryAsState();val current=entry?.destination?.route
        val destination by vm.pendingDestination.collectAsStateWithLifecycle()
        LaunchedEffect(destination) { destination?.let { (type,id) -> nav.navigate(route(type,id)) { launchSingleTop=true };vm.pendingDestination.value=null } }
        val primary=listOf("today","plan","capture","journey","me")
        Scaffold(snackbarHost={SnackbarHost(snackbar)},bottomBar={
            if(current in primary) Surface(color=MaterialTheme.colorScheme.surface,shadowElevation=2.dp) {
                Row(Modifier.fillMaxWidth().navigationBarsPadding().padding(horizontal=4.dp,vertical=8.dp),horizontalArrangement=Arrangement.SpaceEvenly) {
                    primary.forEach { tab ->
                        Column(Modifier.weight(1f).heightIn(min=56.dp).clickable { nav.navigate(tab) { launchSingleTop=true;restoreState=true;popUpTo("today") { saveState=true } } }.padding(vertical=6.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(4.dp)) {
                            Text(if(tab=="capture") "+" else when(tab) { "today" -> "◉";"plan" -> "▤";"journey" -> "▥";else -> "○" },style=MaterialTheme.typography.titleLarge,color=if(current==tab) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(tab.replaceFirstChar { it.titlecase() },style=MaterialTheme.typography.labelSmall,color=if(current==tab) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }) { padding ->
            val page=Modifier.padding(padding).fillMaxSize()
            val open:(EntityType,String)->Unit={type,id -> nav.navigate(route(type,id))}
            val create:(EntityType)->Unit={type -> nav.navigate("create/${type.name}")}
            NavHost(nav,startDestination="today",modifier=page) {
                composable("today") { TodayScreen(vm,p,records,open,create,{nav.navigate(it)}) }
                composable("plan") { PlanScreen(vm,records,open,create,{nav.navigate(it)}) }
                composable("capture") { CaptureScreen(vm,records,null) { nav.popBackStack() } }
                composable("journey") { JourneyScreen(vm,p,records,open,create,{nav.navigate(it)}) }
                composable("me") { MeScreen(p,records,open,create,{nav.navigate(it)}) }
                composable("search") { SearchScreen(vm,open) { nav.popBackStack() } }
                composable("settings") { SettingsScreen(vm,p) { nav.popBackStack() } }
                composable("timeline") { TimelineScreen(vm,records,open) { nav.popBackStack() } }
                composable("review") { ReviewScreen(vm,records) { nav.popBackStack() } }
                composable("list/{type}") { e ->
                    val type=EntityType.valueOf(requireNotNull(e.arguments?.getString("type")))
                    RecordList(type,records.filter { it.type==type },open,{create(type)}) { nav.popBackStack() }
                }
                composable("create/{type}") { e ->
                    val type=EntityType.valueOf(requireNotNull(e.arguments?.getString("type")))
                    Editor(vm,records,remember(type) { Draft(UUID.randomUUID().toString(),type,date=if(type in listOf(EntityType.JOURNAL,EntityType.SESSION,EntityType.MEMORY,EntityType.CHAPTER)) LocalDate.now().toString() else "",frequency=if(type==EntityType.HABIT) Frequency.DAILY else null) },false) { nav.popBackStack() }
                }
                composable("edit/{type}/{id}") { e ->
                    val type=EntityType.valueOf(requireNotNull(e.arguments?.getString("type")));val id=requireNotNull(e.arguments?.getString("id"))
                    var draft by remember(id) { mutableStateOf<Draft?>(null) }
                    LaunchedEffect(id) { draft=vm.repository.edit(type,id) }
                    draft?.let { Editor(vm,records,it,true) { nav.popBackStack() } }
                }
                composable("detail/{type}/{id}") { e ->
                    val type=EntityType.valueOf(requireNotNull(e.arguments?.getString("type")));val id=requireNotNull(e.arguments?.getString("id"))
                    DetailScreen(vm,records,type,id,open,{nav.navigate("edit/${type.name}/$id")},create) { nav.popBackStack() }
                }
            }
        }
    }
}

@Composable fun Onboarding(vm: PersonalViewModel,modifier: Modifier) {
    var step by rememberSaveable { mutableIntStateOf(0) };var name by rememberSaveable { mutableStateOf("") }
    var intents by rememberSaveable { mutableStateOf(arrayListOf<String>()) };var areas by rememberSaveable { mutableStateOf(arrayListOf<String>()) }
    var custom by rememberSaveable { mutableStateOf("") };var firstTitle by rememberSaveable { mutableStateOf("") };var firstType by rememberSaveable { mutableStateOf(EntityType.TASK) }
    val setupId=rememberSaveable { UUID.randomUUID().toString() };var morning by rememberSaveable { mutableStateOf(false) };var evening by rememberSaveable { mutableStateOf(false) }
    val busy by vm.busy.collectAsStateWithLifecycle()
    LazyColumn(modifier.fillMaxSize().imePadding(),contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(20.dp)) {
        item { if(step>0) TextButton(onClick={step--}) { Text("Back · $step of 5") } else Eyebrow("Personal OS") }
        item {
            when(step) {
                0 -> { PageTitle("A calmer place for the life you're building.","Your private life space",true);Spacer(Modifier.height(16.dp));Text("Plan what matters. Capture what crosses your mind. Remember where you left off.");Friend(Modifier.fillMaxWidth());Text("Private by default. Your core data stays on this device.",color=MaterialTheme.colorScheme.onSurfaceVariant) }
                1 -> { PageTitle("What matters to you?","Shape your space");Spacer(Modifier.height(16.dp));Field("What should we call you? (optional)",name,{name=it});Spacer(Modifier.height(16.dp));Text("Choose what you'd like help with. Nothing is selected for you.") }
                2 -> { PageTitle("What's already in your world?","Life areas");Text("Choose your own contexts. Each selected area becomes a real record.") }
                3 -> { PageTitle("Add something real.","A first step");Text("An intention you're moving forward. You can also leave this blank.");Choice("Type",listOf(EntityType.TASK,EntityType.PROJECT,EntityType.GOAL),firstType,{firstType=it}) { it.label() };Field("Title (optional)",firstTitle,{firstTitle=it}) }
                4 -> { PageTitle("Meet the day your way.","Your rhythm");Toggle("Morning Brief",morning,{morning=it});Toggle("Evening Reflection",evening,{evening=it});Text("These are optional. Notification delivery will need permission when enabled.") }
                5 -> { Friend(Modifier.fillMaxWidth(),context="completion");PageTitle("Your space is ready.",reflective=true);Text("${areas.size} chosen life areas${if(firstTitle.isNotBlank()) " and your first ${firstType.label().lowercase()}" else ""}. Everything here begins with you.") }
            }
        }
        if(step==1) items(listOf("Build projects","Stay organized","Learn & grow","Build habits","Remember my life","Explore ideas")) { text ->
            SelectRow(text,text in intents) { intents=ArrayList(intents.toMutableList().apply { if(text in this) remove(text) else add(text) }) }
        }
        if(step==2) {
            items(listOf("Work / Career","Creative Work","Health","Reading","Learning","Family","Personal")) { text -> SelectRow(text,text in areas) { areas=ArrayList(areas.toMutableList().apply { if(text in this) remove(text) else add(text) }) } }
            item { Field("Add your own life area",custom,{custom=it});TextButton(onClick={if(custom.isNotBlank() && custom.trim() !in areas) {areas=ArrayList(areas+custom.trim());custom=""}}) { Text("Add life area") };Text(areas.joinToString(" · ")) }
        }
        item { PrimaryButton(if(step==0) "Make this space mine" else if(step==5) "Enter Personal OS" else "Continue",!busy) {
            if(step<5) step++ else vm.act("Your space is ready") {
                areas.forEach { area -> vm.repository.save(Draft(UUID.nameUUIDFromBytes("$setupId/area/$area".toByteArray()).toString(),EntityType.LIFE_AREA,title=area)) }
                if(firstTitle.isNotBlank()) vm.repository.save(Draft(UUID.nameUUIDFromBytes("$setupId/first".toByteArray()).toString(),firstType,title=firstTitle))
                vm.preferences.finishSetup(name,intents.toSet(),morning,evening)
            }
        } }
        if(step in 1..4) item { TextButton(onClick={when(step) {1 -> {name="";intents=arrayListOf()};2 -> areas=arrayListOf();3 -> firstTitle="";4 -> {morning=false;evening=false} };step++}) { Text("Skip for now") } }
    }
}
@Composable fun SelectRow(text: String,selected: Boolean,onClick: ()->Unit) { CalmCard(tone=if(selected) 1 else 0,onClick=onClick) { Row(verticalAlignment=Alignment.CenterVertically) { Checkbox(selected,{onClick()});Text(text,style=MaterialTheme.typography.titleMedium) } } }
@Composable fun Toggle(text: String,value: Boolean,onChange: (Boolean)->Unit) { Row(Modifier.fillMaxWidth().heightIn(min=56.dp),verticalAlignment=Alignment.CenterVertically) { Text(text,Modifier.weight(1f));Switch(value,onChange) } }
@Composable fun <T> Choice(label: String,options: List<T>,selected: T,onChange: (T)->Unit,text: (T)->String={it.toString()}) {
    var expanded by remember { mutableStateOf(false) }
    Column { Text(label,style=MaterialTheme.typography.labelMedium);Box { OutlinedButton(onClick={expanded=true},modifier=Modifier.fillMaxWidth().heightIn(min=48.dp),shape=RoundedCornerShape(18.dp)) { Text(text(selected)) };DropdownMenu(expanded,{expanded=false}) { options.forEach { option -> DropdownMenuItem(text={Text(text(option))},onClick={onChange(option);expanded=false}) } } } }
}
@Composable fun HeaderBack(title: String,back: ()->Unit) { TextButton(onClick=back) { Text("Back") };PageTitle(title) }
@Composable fun RecordRow(r: Record,open: (EntityType,String)->Unit,complete: (() -> Unit)? = null) {
    CalmCard(onClick={open(r.type,r.id)}) { Row(verticalAlignment=Alignment.CenterVertically) {
        if(complete!=null) Checkbox(r.status=="COMPLETED",{complete()})
        Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(4.dp)) { Eyebrow(r.type.label());Text(r.title,style=MaterialTheme.typography.titleMedium);Text(listOfNotNull(r.status.takeIf { it.isNotEmpty() }?.lowercase()?.replace('_',' '),r.date).joinToString(" · "),style=MaterialTheme.typography.labelMedium,color=MaterialTheme.colorScheme.onSurfaceVariant) }
    } }
}

