package com.navin.personalos.feature

import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.navin.personalos.core.database.*
import com.navin.personalos.core.designsystem.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable fun AttachmentSection(vm: PersonalViewModel,type: EntityType,id: String) {
    val context=LocalContext.current
    val all by vm.repository.dao.observeAttachment().collectAsStateWithLifecycle(emptyList())
    val attachments=all.filter {it.ownerType==type && it.ownerId==id}
    var remove by remember {mutableStateOf<Attachment?>(null)};var unavailable by remember {mutableStateOf<Set<String>>(emptySet())}
    LaunchedEffect(attachments) { unavailable=withContext(Dispatchers.IO) {attachments.filter { a -> runCatching {context.contentResolver.openAssetFileDescriptor(Uri.parse(a.uri),"r")?.use {true} ?: false}.getOrDefault(false).not() }.map {it.id}.toSet()} }
    val picker=rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri -> uri?.let {
        vm.act("Attachment linked") { withContext(Dispatchers.IO) {
            require(uri.scheme=="content") {"Choose a document through Android's file picker."}
            try {context.contentResolver.takePersistableUriPermission(uri,Intent.FLAG_GRANT_READ_URI_PERMISSION)} catch(e: SecurityException) {throw IllegalArgumentException("This provider cannot keep access after restart. Choose a file from a document provider that supports persistent access.")}
            var name:String?=null;var size:Long?=null
            context.contentResolver.query(uri,arrayOf(OpenableColumns.DISPLAY_NAME,OpenableColumns.SIZE),null,null,null)?.use { c -> if(c.moveToFirst()) {name=c.getString(0);if(!c.isNull(1)) size=c.getLong(1)} }
            vm.repository.dao.put(Attachment(ownerType=type,ownerId=id,uri=uri.toString(),mimeType=context.contentResolver.getType(uri) ?: "application/octet-stream",displayName=name,sizeBytes=size))
        } }
    } }
    SectionTitle("Attachments");Text("Links to original files. Removing a link keeps your original file.")
    attachments.forEach { a -> CalmCard {
        Text(a.displayName ?: "Attached file",style=MaterialTheme.typography.titleMedium)
        Text(if(a.id in unavailable) "Original file unavailable. Add it again from this device." else "${a.mimeType}${a.sizeBytes?.let { " · $it bytes" }.orEmpty()}")
        TextButton(enabled=a.id !in unavailable,onClick={vm.act("") {try { context.startActivity(Intent(Intent.ACTION_VIEW).setDataAndType(Uri.parse(a.uri),a.mimeType).addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)) } catch(e: android.content.ActivityNotFoundException) {throw IllegalArgumentException("No installed app can open this file type.")} catch(e: SecurityException) {throw IllegalArgumentException("Access has expired. Add this attachment again.")} }}) {Text("Open file")}
        TextButton(onClick={remove=a}) {Text("Remove link")}
    } }
    TextButton(onClick={picker.launch(arrayOf("image/*"))}) {Text("Add image")};TextButton(onClick={picker.launch(arrayOf("*/*"))}) {Text("Add file")}
    remove?.let { a -> AlertDialog(onDismissRequest={remove=null},title={Text("Remove attachment link?")},text={Text("The original file stays in its current location.")},confirmButton={TextButton(onClick={vm.act("Attachment link removed") {vm.repository.dao.deleteAttachment(a.id)
            if(vm.repository.dao.allAttachment().none {it.uri==a.uri}) runCatching {context.contentResolver.releasePersistableUriPermission(Uri.parse(a.uri),Intent.FLAG_GRANT_READ_URI_PERMISSION)}
            remove=null}}) {Text("Remove link")}},dismissButton={TextButton(onClick={remove=null}) {Text("Keep")}}) }
}
