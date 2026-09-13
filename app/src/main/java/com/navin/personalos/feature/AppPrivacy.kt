package com.navin.personalos.feature

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.activity.compose.LocalActivity
import androidx.compose.ui.unit.dp
import com.navin.personalos.core.designsystem.*

private const val AUTH = BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL
fun authenticate(activity: FragmentActivity,onSuccess: ()->Unit,onError: (String)->Unit) {
    if(BiometricManager.from(activity).canAuthenticate(AUTH)!=BiometricManager.BIOMETRIC_SUCCESS) { onError("Set up a secure screen lock or biometrics in Android Settings first.");return }
    val prompt=BiometricPrompt(activity,ContextCompat.getMainExecutor(activity),object: BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) { onSuccess() }
        override fun onAuthenticationError(errorCode: Int,errString: CharSequence) { onError(errString.toString()) }
    })
    prompt.authenticate(BiometricPrompt.PromptInfo.Builder().setTitle("Unlock Personal OS").setSubtitle("Use your device's secure authentication").setAllowedAuthenticators(AUTH).build())
}
@Composable fun LockScreen(vm: PersonalViewModel) {
    val activity=LocalActivity.current as FragmentActivity
    var error by remember { mutableStateOf<String?>(null) }
    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).safeDrawingPadding().padding(24.dp),contentAlignment=Alignment.Center) {
        Column(verticalArrangement=Arrangement.spacedBy(20.dp)) {
            PageTitle("Your space is private.","Personal OS",true);Text("Unlock to return to your records.")
            error?.let { Text(it,color=MaterialTheme.colorScheme.onSurfaceVariant) }
            PrimaryButton("Unlock") { authenticate(activity,{vm.unlocked.value=true;error=null},{error=it}) }
        }
    }
}
