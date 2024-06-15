package com.example.pixelpeppers.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pixelpeppers.R
import com.example.pixelpeppers.Route
import com.example.pixelpeppers.coordinators.dataCoordinator.DataCoordinator
import com.example.pixelpeppers.services.UserService


@Composable
fun Login(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    if (DataCoordinator.instance.accessToken != null) {
        // @@ TODO: Remove navcontroller
        navController.navigate(Route.OnboardingIntro.route)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val context = LocalContext.current
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Log in to your account",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(100.dp))
            Button(
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF633ea6)),
                onClick = {
                    // @@ TODO: Use ViewModel
                    UserService.instance.startTwitchAuthActivity(context)
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.twitch),
                    contentDescription = "Log in with Twitch",
                    tint = Color.White,
                    modifier = Modifier
                        .width(160.dp)
                        .height(100.dp)
                        .align(Alignment.CenterVertically)
                )

            }
        }
    }
}