package com.example.pixelpeppers

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.pixelpeppers.coordinators.dataCoordinator.DataCoordinator
import com.example.pixelpeppers.coordinators.dataCoordinator.updateAccessToken
import com.example.pixelpeppers.coordinators.dataCoordinator.updateTokenExpires
import com.example.pixelpeppers.coordinators.dataCoordinator.updateUsername
import com.example.pixelpeppers.repositories.UserRepository
import com.example.pixelpeppers.ui.theme.PixelPeppersTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity(
) {
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
//        val currentUser = auth.currentUser
        val accessToken = DataCoordinator.instance.accessToken
        val currentUser = auth.currentUser
        var route: Route = Route.Login
        if (currentUser != null) {
            currentUser.let {
                DataCoordinator.instance.updateUsername(currentUser.displayName!!)
            }
            route = Route.OnboardingIntro
        } else {
            val uri: Uri? = intent.data
            if (uri != null && uri.path.equals("/auth")) {
                val code = uri.getQueryParameter("code")
                if (code != null) {
                    runBlocking {
                        val customAuthToken = UserRepository.instance.getCustomAuthToken(code)
                        println(customAuthToken)
                        auth.signInWithCustomToken(customAuthToken).await()
                        UserRepository.instance.refreshTwitchAccessToken()
                        route = Route.OnboardingIntro
                    }
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
        runBlocking {
            DataCoordinator.instance.initializeAsync(baseContext)
        }
    }

}

