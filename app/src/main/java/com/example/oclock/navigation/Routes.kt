package com.example.oclock.navigation

import com.example.oclock.R

sealed class Routes(val route: String, val title: Int) {

    data object RemoveTimer : Routes(route = "removeTimer", title = R.string.titleRefactorTimer)

    data object Time : Routes(route = "time", title = R.string.titleTime)

    data object Alarm : Routes(route = "alarm", title = R.string.titleAlarm)

    data object Timer : Routes(route = "timer", title = R.string.titleTimer)

    data object Stopwatch : Routes(route = "stopwatch", title = R.string.titleStopwatch)
}