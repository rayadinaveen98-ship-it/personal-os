package com.navin.personalos.feature

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.navin.personalos.core.database.*
import com.navin.personalos.core.designsystem.*

@Composable fun ReminderDeliverySection(vm: PersonalViewModel,type: EntityType,id: String) {
    val context=LocalContext.current
    val reminders by vm.repository.dao.observeReminder().collectAsStateWithLifecycle(emptyList())
    val selected=reminders.filter {if(type==EntityType.REMINDER) it.id==id else it.ownerType==(if(type==EntityType.HABIT) OwnerType.HABIT else OwnerType.TASK) && it.ownerId==id}
    val permission=rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {vm.reminders.enqueue()}
    selected.forEach {r -> CalmCard {
        Eyebrow("Reminder delivery")
        Text(if(r.state==ReminderState.SCHEDULED) when(r.deliveryState) {
            DeliveryState.PENDING -> "Saved. Android scheduling is being checked."
            DeliveryState.SCHEDULED -> "Scheduled with Android."
            DeliveryState.NOTIFICATIONS_BLOCKED -> "Notifications are blocked."
            DeliveryState.EXACT_ACCESS_BLOCKED -> "Precise reminder access is required."
            DeliveryState.CHANNEL_BLOCKED -> "The reminder notification channel is disabled."
            DeliveryState.EXPIRED -> "This reminder time has passed. Choose a new time if needed."
            DeliveryState.ERROR -> "Android couldn't schedule this reminder. Check delivery again."
        } else r.state.name.lowercase().replaceFirstChar {it.titlecase()})
        if(r.deliveryState==DeliveryState.NOTIFICATIONS_BLOCKED || r.deliveryState==DeliveryState.CHANNEL_BLOCKED) {
            if(Build.VERSION.SDK_INT>=33) TextButton(onClick={permission.launch(Manifest.permission.POST_NOTIFICATIONS)}) {Text("Allow notifications")}
            TextButton(onClick={context.startActivity(Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).putExtra(Settings.EXTRA_APP_PACKAGE,context.packageName))}) {Text("Open notification settings")}
        }
        if(r.deliveryState==DeliveryState.EXACT_ACCESS_BLOCKED && Build.VERSION.SDK_INT>=31) TextButton(onClick={context.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,Uri.parse("package:${context.packageName}")))}) {Text("Allow precise reminders")}
        if(r.state==ReminderState.SCHEDULED) TextButton(onClick={vm.reminders.enqueue()}) {Text("Check delivery again")}
    } }
}
