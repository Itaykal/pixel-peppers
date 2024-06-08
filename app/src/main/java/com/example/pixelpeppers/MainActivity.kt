package com.example.pixelpeppers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.pixelpeppers.ui.theme.PixelPeppersTheme
import com.example.pixelpeppers.ui.components.PixelPeppersButton
import com.example.pixelpeppers.ui.components.PageIndicator


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PixelPeppersTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Onboarding1(
                            modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Onboarding1(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .requiredWidth(width = 393.dp)
            .requiredHeight(height = 852.dp)
            .background(color = Color(0xff121011))
    ) {
        PixelPeppersButton(text = "Next") {}
        PageIndicator(totalPages = 2, currentPage = 0)
        Text(
            text = "Tell us about your\nfavorite games",
            color = Color.White,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium),
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .offset(
                    x = 0.dp,
                    y = 597.dp
                ))
        Image(
            painter = painterResource(id = androidx.core.R.drawable.notify_panel_notification_icon_bg),
            contentDescription = "hero_capsule 1",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 312.dp,
                    y = 317.dp
                )
                .requiredWidth(width = 133.dp)
                .requiredHeight(height = 182.dp)
                .clip(shape = RoundedCornerShape(8.dp)))
        Image(
            painter = painterResource(id = androidx.core.R.drawable.notify_panel_notification_icon_bg),
            contentDescription = "Logo_of_Stardew_Valley",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = (-52).dp,
                    y = 124.dp
                )
                .requiredWidth(width = 133.dp)
                .requiredHeight(height = 182.dp)
                .clip(shape = RoundedCornerShape(8.dp)))
        Image(
            painter = painterResource(id = androidx.core.R.drawable.notify_panel_notification_icon_bg),
            contentDescription = "60eca3ac155247e21850c7d075d01ebf0f3f5dbf19ccd2a1",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 100.dp,
                    y = 124.dp
                )
                .requiredWidth(width = 133.dp)
                .requiredHeight(height = 182.dp)
                .clip(shape = RoundedCornerShape(8.dp)))
        Image(
            painter = painterResource(id = androidx.core.R.drawable.notify_panel_notification_icon_bg),
            contentDescription = "World_of_Warcraft",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 251.dp,
                    y = 124.dp
                )
                .requiredWidth(width = 133.dp)
                .requiredHeight(height = 182.dp)
                .clip(shape = RoundedCornerShape(8.dp)))
        Image(
            painter = painterResource(id = androidx.core.R.drawable.notify_panel_notification_icon_bg),
            contentDescription = "due3Vp0T2VSGfBtGsWjVnrL4o882iYVk",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 10.dp,
                    y = 317.dp
                )
                .requiredWidth(width = 133.dp)
                .requiredHeight(height = 182.dp)
                .clip(shape = RoundedCornerShape(8.dp)))
        Image(
            painter = painterResource(id = androidx.core.R.drawable.notify_panel_notification_icon_bg),
            contentDescription = "MV5BMzNkMmE5MjktMzRhYS00MzZhLWEzYzMtMWFkYmE4MDk0NDZkXkEyXkFqcGdeQXVyMTk2OTAzNTI@",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 161.dp,
                    y = 317.dp
                )
                .requiredWidth(width = 133.dp)
                .requiredHeight(height = 182.dp)
                .clip(shape = RoundedCornerShape(8.dp)))
        Text(
            text = "Skip",
            color = Color(0xffd4d4d4),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 16.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(
                    x = 342.dp,
                    y = 59.dp
                ))
    }
}

@Preview(widthDp = 393, heightDp = 852)
@Composable
private fun Onboarding1Preview() {
    Onboarding1(Modifier)
}