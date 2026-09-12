package com.navin.personalos

import android.os.Bundle
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
        setContent { PersonalApp(model) }
    }
}
