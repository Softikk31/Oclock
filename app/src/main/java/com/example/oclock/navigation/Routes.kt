package com.example.oclock.navigation

import androidx.compose.ui.res.stringResource
import com.example.oclock.R
import com.example.oclock.navigation.ui.BottomBarRoutes

sealed class Routes(val route: String) {
    data object RemoveTimer : Routes(route = "removeTimer")
}