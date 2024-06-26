package com.example.pixelpeppers

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.pixelpeppers.coordinators.dataCoordinator.DataCoordinator
import com.example.pixelpeppers.ui.theme.PixelPeppersTheme
import com.example.pixelpeppers.viewModels.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : ComponentActivity(
) {
    private val userViewModel: UserViewModel by viewModels()
    override fun onStart() {
        super.onStart()
        var route: Route = Route.Login
        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            userViewModel.refreshUser(Firebase.auth.currentUser!!.uid)
            route = Route.Menu
        } else {
            val uri: Uri? = intent.data
            if (uri != null && uri.path.equals("/auth")) {
                val code = uri.getQueryParameter("code")
                if (code != null) {
                    runBlocking {
                        userViewModel.loginWithCode(code)
                    }
                    route = Route.OnboardingIntro
                }
            }
        }
        setContent {
            PixelPeppersTheme {
                NavGraph(
                    startDestination = route.route,
                )
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        DataCoordinator.instance.initialize(baseContext)
    }
}

