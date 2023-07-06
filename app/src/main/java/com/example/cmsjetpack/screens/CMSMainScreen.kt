package com.example.cmsjetpack.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Composable
fun CMSMainScreen() {
    CMSMainScreenSkeleton()
}

@Preview
@Composable
fun CMSMainScreenSkeletonPreview() {
    CMSMainScreenSkeleton()
}

@Composable
fun CMSMainScreenSkeleton() {
    val navController = rememberNavController()

    CMSNavHost(
        navController = navController
    )
}
