package com.musica.phone.getstarted

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.musica.common.compose.MusicaComposeActivity
import com.musica.dashboard.user.register.RegisterUserActivity
import com.musica.dashboard.user.signin.SignInActivity

class LandingActivity: MusicaComposeActivity() {
    @Composable
    override fun ActivityContent() {
        LandingScreen(
            onSignInClick = { startActivity(Intent(this@LandingActivity, SignInActivity::class.java)) },
            onRegisterClick = { startActivity(Intent(this@LandingActivity, RegisterUserActivity::class.java)) }
        )

        BackHandler {
            finishAffinity()
        }
    }
}