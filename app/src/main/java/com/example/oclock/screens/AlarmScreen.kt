package com.example.oclock.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.oclock.ui.theme.OclockProgerTimeThemeScreenBottomBarScreens
import com.example.oclock.ui.theme.WhiteColorScreen

@Composable
fun AlarmScreenFun() {
    OclockProgerTimeThemeScreenBottomBarScreens {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteColorScreen),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "This is ALARM"
            )

        }
    }
}