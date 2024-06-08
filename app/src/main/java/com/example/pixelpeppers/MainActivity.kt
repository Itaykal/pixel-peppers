package com.example.pixelpeppers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pixelpeppers.ui.theme.PixelPeppersTheme
import com.example.pixelpeppers.ui.screens.OnboardingIntro

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PixelPeppersTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                        .padding(0.dp)
                ) { paddingValues ->
                    OnboardingIntro(
                        currentPage = 0,
                        modifier = Modifier.padding(paddingValues)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}
