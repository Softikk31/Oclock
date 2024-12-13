package com.example.oclock.navigation.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.oclock.navigation.NavGraphFun
import com.example.oclock.navigation.Routes
import com.example.oclock.ui.theme.*

@Preview(showSystemUi = true)
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val currentRoute =
        remember { mutableStateOf(navController.currentBackStackEntry?.destination?.route) }

    OclockProgerTimeThemeScreenBottomBarScreens {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar() },
            bottomBar = {
                if (currentRoute.value != Routes.RemoveTimer.route) {
                    BottomBar(navController = navController)
                }
            },
            containerColor = WhiteBottomBarColor
        ) {

            LaunchedEffect(key1 = navController) {
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    currentRoute.value = destination.route
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                NavGraphFun(navController = navController)
            }
        }
    }

}

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .background(WhiteColorScreen)
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarRoutes.Time,
        BottomBarRoutes.Alarm,
        BottomBarRoutes.Timer,
        BottomBarRoutes.Stopwatch
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    Row(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 40.dp)
            .background(Color.Transparent)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}

@Composable
fun AddItem(
    screen: BottomBarRoutes,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val colorIcon = if (selected) Green else LightGray

    val interactionSource = remember { MutableInteractionSource() }

    val colorBox = if (selected) LightGreen else Color.Transparent

    Box(
        modifier = Modifier
            .height(55.dp)
            .width(75.dp)
            .clickable(
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                indication = null,
                interactionSource = interactionSource
            ),
        contentAlignment = Alignment.TopCenter
    ) {

        Box(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .padding(top = 3.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                Modifier
                    .clip(CircleShape)
                    .background(color = colorBox)
                    .width(50.dp)
                    .height(30.dp),
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    imageVector = ImageVector.vectorResource(id = screen.icon),
                    contentDescription = null,
                    tint = colorIcon
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 25.dp),
                text = stringResource(screen.title),
                fontSize = 9.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = colorIcon
            )

        }
    }
}