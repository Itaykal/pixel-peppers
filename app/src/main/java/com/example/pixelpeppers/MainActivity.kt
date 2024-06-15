package com.example.pixelpeppers

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.pixelpeppers.coordinators.dataCoordinator.DataCoordinator
import com.example.pixelpeppers.repositories.TwitchAuthRepository
import com.example.pixelpeppers.ui.theme.PixelPeppersTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity(
) {
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        var route: Route = Route.Login
        if (currentUser != null) {
            route = Route.Menu
        } else {
            val uri: Uri? = intent.data
            if (uri != null && uri.path.equals("/auth")) {
                val code = uri.getQueryParameter("code")
                if (code != null) {
                    authenticateWithCode(code)
                    route = Route.OnboardingIntro
                }
            }
        }
        setContent {
            PixelPeppersTheme {
                NavGraph(startDestination = route.route)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        auth = Firebase.auth
        DataCoordinator.instance.initialize(baseContext)
    }

    private fun authenticateWithCode(code: String) {
        CoroutineScope(Dispatchers.Main).launch {
            TwitchAuthRepository.instance.authenticateWithTwitch(code) {
                auth.signInWithCustomToken(it)
            }
        }
    }
}

