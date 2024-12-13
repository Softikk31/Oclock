package com.example.oclock.navigation

sealed class Routes(val route: String) {
    object RemoveTimer : Routes(route = "removeTimer")
}