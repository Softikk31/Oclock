package com.example.oclock.navigation

sealed class Routes(val route: String) {
    data object ReplaceTimer : Routes(route = "replaceTimer")
    data object TimerSignal : Routes(route = "timerSignal")
}