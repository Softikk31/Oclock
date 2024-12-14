package com.example.oclock.navigation.ui

import com.example.oclock.R

sealed class BottomBarRoutes(
    val route: String,
    val title: Int,
    val icon: Int
) {

    data object Time : BottomBarRoutes(
        route = "time",
        title = R.string.titleTime,
        icon = R.drawable.time_icon
    )

    data object Alarm : BottomBarRoutes(
        route = "alarm",
        title = R.string.titleAlarm,
        icon = R.drawable.alarm_icon
    )

    data object Timer : BottomBarRoutes(
        route = "timer",
        title = R.string.titleTimer,
        icon = R.drawable.timer_icon
    )

    data object Stopwatch : BottomBarRoutes(
        route = "stopwatch",
        title = R.string.titleStopwatch,
        icon = R.drawable.stopwatch_icon
    )

}