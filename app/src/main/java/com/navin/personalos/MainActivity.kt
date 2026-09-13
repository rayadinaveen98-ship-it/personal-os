package com.navin.personalos

import android.os.Bundle
import android.os.SystemClock
import android.content.Intent
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.navin.personalos.core.database.EntityType
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.navin.personalos.feature.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private val model: PersonalViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen();super.onCreate(savedInstanceState);enableEdgeToEdge()
        handleIntent(intent)
        lifecycleScope.launch { model.preferences.flow.collect { p ->
            if(p.locked) window.addFlags(WindowManager.LayoutParams.FLAG_SECURE) else window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        } }
        setContent { PersonalApp(model) }
    }
    override fun onStart() {
        val p=model.prefs.value
        if(p?.locked==true && model.backgroundAt?.let { SystemClock.elapsedRealtime()-it>=p.timeoutMinutes*60000L }==true) model.unlocked.value=false
        model.backgroundAt=null
        super.onStart()
        model.reminders.enqueue()
    }
    override fun onStop() { model.backgroundAt=SystemClock.elapsedRealtime();super.onStop() }
    override fun onNewIntent(intent: Intent) { super.onNewIntent(intent);setIntent(intent);handleIntent(intent) }
    private fun handleIntent(intent: Intent) {
        val type=intent.getStringExtra("entityType")?.let { runCatching { EntityType.valueOf(it) }.getOrNull() }
        val id=intent.getStringExtra("entityId")
        if(type!=null && !id.isNullOrBlank()) model.pendingDestination.value=type to id
    }
}


