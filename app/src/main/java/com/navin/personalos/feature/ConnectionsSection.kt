package com.navin.personalos.feature

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.navin.personalos.core.data.*
import com.navin.personalos.core.database.*
import com.navin.personalos.core.designsystem.*

@Composable fun ConnectionsSection(vm: PersonalViewModel,type: EntityType,id: String,records: List<Record>,open: (EntityType,String)->Unit) {
    val tags by vm.repository.dao.observeTag().collectAsStateWithLifecycle(emptyList())
    val refs by vm.repository.dao.observeTagLinks().collectAsStateWithLifecycle(emptyList())
    val links by vm.repository.dao.observeEntityLink().collectAsStateWithLifecycle(emptyList())
    var name by rememberSaveable {mutableStateOf("")};var targetType by rememberSaveable {mutableStateOf(EntityType.PROJECT)};var targetId by rememberSaveable {mutableStateOf<String?>(null)}
    SectionTitle("Tags & connections")
    tags.filter {tag -> refs.any {it.tagId==tag.id && it.entityType==type && it.entityId==id}}.forEach {tag -> TextButton(onClick={vm.act("Tag removed") {vm.repository.tag(type,id,tag.name,false)}}) {Text("${tag.name} · Remove tag")} }
    Field("Add a tag",name,{name=it})
    TextButton(enabled=name.isNotBlank(),onClick={vm.act("Tag added") {vm.repository.tag(type,id,name,true);name=""}}) {Text("Add tag")}
    links.filter {it.fromType==type && it.fromId==id || it.toType==type && it.toId==id}.forEach {link ->
        val target=if(link.fromType==type && link.fromId==id) link.toType to link.toId else link.fromType to link.fromId
        val r=records.firstOrNull {it.type==target.first && it.id==target.second}
        CalmCard { if(r!=null) TextButton(onClick={open(r.type,r.id)}) {Text(r.title)} else Text("Linked record unavailable");Text(link.relationType.lowercase().replace('_',' '));TextButton(onClick={vm.act("Link removed") {vm.repository.dao.deleteEntityLink(link.id)}}) {Text("Remove connection")} }
    }
    Choice("Connect a record",EntityType.entries.filter {it!=EntityType.DAILY_REVIEW},targetType,{targetType=it;targetId=null}) {it.label()}
    ContextPicker("Record",targetType,records.filter {it.type!=type || it.id!=id},targetId,{targetId=it})
    TextButton(enabled=targetId!=null,onClick={val target=targetId ?: return@TextButton;vm.act("Records connected") {vm.repository.link(type,id,targetType,target);targetId=null}}) {Text("Add connection")}
}
