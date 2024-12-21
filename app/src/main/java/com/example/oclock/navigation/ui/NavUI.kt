package com.example.oclock.navigation.ui

import NavGraphFun
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.oclock.navigation.Routes
import com.example.oclock.ui.theme.*

@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val targetColor = when (currentRoute) {
        in listOf(
            BottomBarRoutes.Time.route,
            BottomBarRoutes.Alarm.route,
            BottomBarRoutes.Timer.route,
            BottomBarRoutes.Stopwatch.route
        ) -> Color.Transparent
        else -> WhiteColorScreen
    }

    val animatedColor: Color by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(200)
    )

    OclockProgerTimeThemeScreenBottomBarScreens {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                val routesWithBottomBar = listOf(
                    BottomBarRoutes.Time.route,
                    BottomBarRoutes.Alarm.route,
                    BottomBarRoutes.Timer.route,
                    BottomBarRoutes.Stopwatch.route
                )

                AnimatedVisibility(
                    visible = currentRoute in routesWithBottomBar,
                    enter = fadeIn(animationSpec = tween(300)),
                    exit = fadeOut(animationSpec = tween(300))
                ) {
                    BottomBar(navController = navController, animatedColor)
                }
            },
            containerColor = WhiteBottomBarColor
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                NavGraphFun(navController = navController)
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController, color: Color) {
    val screens = listOf(
        BottomBarRoutes.Time,
        BottomBarRoutes.Alarm,
        BottomBarRoutes.Timer,
        BottomBarRoutes.Stopwatch
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .height(105.dp)
            .background(color),
        containerColor = WhiteBottomBarColor
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                modifier = Modifier
                    .padding(top = 10.dp),
                icon = {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = ImageVector.vectorResource(screen.icon),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(screen.title),
                        fontSize = 9.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                },
                selected = currentRoute == screen.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Green,
                    selectedTextColor = Green,
                    unselectedIconColor = LightGray,
                    unselectedTextColor = LightGray,
                    indicatorColor = LightGreen
                ),
                onClick = {
                    navController.navigate(screen.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}