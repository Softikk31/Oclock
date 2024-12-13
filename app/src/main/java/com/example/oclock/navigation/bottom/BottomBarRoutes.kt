package com.example.oclock.navigation.bottom

import com.example.oclock.R

sealed class BottomBarRoutes(
    val route: String,
    val title: Int,
    val icon: Int
) {

    object Time : BottomBarRoutes(
        route = "time",
        title = R.string.titleTime,
        icon = R.drawable.time_icon
    )

    object Alarm : BottomBarRoutes(
        route = "alarm",
        title = R.string.titleAlarm,
        icon = R.drawable.alarm_icon
    )

    object Timer : BottomBarRoutes(
        route = "timer",
        title = R.string.titleTimer,
        icon = R.drawable.timer_icon
    )

    object Stopwatch : BottomBarRoutes(
        route = "stopwatch",
        title = R.string.titleStopwatch,
        icon = R.drawable.stopwatch_icon
    )

}