package com.sample.photogalleryapplication.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.photogalleryapplication.R
import com.sample.photogalleryapplication.view.util.getStringResource
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigateToMain: () -> Unit) {
    val alphaAnimation = rememberInfiniteTransition(label = "Alpha Animation")
    val alpha by alphaAnimation.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Alpha Value"
    )

    LaunchedEffect(Unit) {
        delay(3000)
        navigateToMain()
    }

    Surface(color = MaterialTheme.colorScheme.background) {
        SplashContent(alpha = alpha)
    }
}

@Composable
private fun SplashContent(alpha: Float) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedLogo(alpha = alpha)
            Spacer(modifier = Modifier.height(24.dp))
            AppName()
        }
    }
}

@Composable
private fun AnimatedLogo(alpha: Float) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        Image(
            painter = painterResource(id = R.drawable.reddit_logo),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .alpha(alpha),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun AppName() {
    Text(
        text = getStringResource(R.string.app_name),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(navigateToMain = {})
}