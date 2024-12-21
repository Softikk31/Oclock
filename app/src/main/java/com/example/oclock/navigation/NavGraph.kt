import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.oclock.navigation.Routes
import com.example.oclock.navigation.ui.BottomBarRoutes
import com.example.oclock.screens.AlarmScreenFun
import com.example.oclock.screens.StopwatchScreenFun
import com.example.oclock.screens.TimeScreenFun
import com.example.oclock.screens.Timer.ReplaceTimerScreenFun
import com.example.oclock.screens.Timer.TimerScreenFun
import com.example.oclock.screens.Timer.TimerSignalScreenFun


@Composable
fun NavGraphFun(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarRoutes.Time.route
    ) {

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

        composable(
            route = Routes.ReplaceTimer.route,
            enterTransition = {
                fadeIn(
                    tween(300, easing = LinearEasing)
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    tween(300, easing = LinearEasing)
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            ReplaceTimerScreenFun(navController = navController) { navController.popBackStack() }
        }

        composable(route = Routes.TimerSignal.route) {
            TimerSignalScreenFun()
        }
    }
}