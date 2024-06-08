package com.example.pixelpeppers.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.R
import com.example.pixelpeppers.ui.components.PageIndicator
import com.example.pixelpeppers.ui.components.PixelPeppersButton

@Composable
fun OnboardingIntro(modifier: Modifier = Modifier, currentPage: Int = 0) {
    Box(contentAlignment = Alignment.TopCenter,
        modifier = modifier
    ) {
        PixelPeppersButton(text = "Next") {}
        PageIndicator(totalPages = 2,currentPage = currentPage)
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
            painter = painterResource(id = R.drawable.notify_panel_notification_icon_bg),
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
            painter = painterResource(id = R.drawable.notify_panel_notification_icon_bg),
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
            painter = painterResource(id = R.drawable.notify_panel_notification_icon_bg),
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
            painter = painterResource(id = R.drawable.notify_panel_notification_icon_bg),
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
            painter = painterResource(id = R.drawable.notify_panel_notification_icon_bg),
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
            painter = painterResource(id = R.drawable.notify_panel_notification_icon_bg),
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