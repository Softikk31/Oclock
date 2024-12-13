package com.example.oclock.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oclock.navigation.bottom.BottomBarRoutes
import com.example.oclock.screens.AlarmScreenFun
import com.example.oclock.screens.RemoveTimer
import com.example.oclock.screens.StopwatchScreenFun
import com.example.oclock.screens.TimeScreenFun
import com.example.oclock.screens.TimerScreenFun

@Composable
fun NavGraphFun(navController: NavHostController) {

    NavHost(navController = navController, startDestination = BottomBarRoutes.Time.route) {
        composable(route = BottomBarRoutes.Time.route) {
            TimeScreenFun()
        }

        composable(route = BottomBarRoutes.Alarm.route) {
            AlarmScreenFun()
        }

        composable(route = BottomBarRoutes.Timer.route) {
            TimerScreenFun(navController = navController)
        }

        composable(route = BottomBarRoutes.Stopwatch.route) {
            StopwatchScreenFun()
        }

        composable(route = Routes.RemoveTimer.route) {
            RemoveTimer(navController = navController)
        }
    }

}