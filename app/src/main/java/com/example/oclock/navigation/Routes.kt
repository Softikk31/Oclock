package com.example.oclock.navigation

import com.example.oclock.R

sealed class Routes(val route: String) {
    data object RemoveTimer : Routes(route = "removeTimer")
    data object TimerSignal : Routes(route = "timerSignal")
}