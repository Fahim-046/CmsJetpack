package com.example.cmsjetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cmsjetpack.screens.CMSMainScreen
import com.example.cmsjetpack.ui.theme.CMSJetpackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CMSJetpackTheme {
                // A surface container using the 'background' color from the theme
                CMSMainScreen()
            }
        }
    }
}
