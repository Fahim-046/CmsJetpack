package com.example.cmsjetpack.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    gotoHomeIndex: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1000)

        gotoHomeIndex()
    }
    SplashScreenSkeleton()
}

@Composable
fun SplashScreenSkeleton() {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CMS",
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenSkeletonPreview() {
    SplashScreenSkeleton()
}
