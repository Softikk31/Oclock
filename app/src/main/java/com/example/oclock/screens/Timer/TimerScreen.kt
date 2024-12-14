package com.example.oclock.screens.Timer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oclock.R
import com.example.oclock.data.TimerItems
import com.example.oclock.data.funcs.chunkedWithNulls
import com.example.oclock.data.getTimeTimer
import com.example.oclock.navigation.Routes
import com.example.oclock.picker.Picker
import com.example.oclock.picker.rememberPickerState
import com.example.oclock.ui.theme.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

private val seconds = (0..59).map { if (it < 10) "0$it" else it.toString() }
private val minute = (0..59).map { if (it < 10) "0$it" else it.toString() }
private val hour = (0..23).map { if (it < 10) "0$it" else it.toString() }
private const val VISIBLE_ITEMS_MIDDLE = 3 / 2
private const val LIST_SCROLL_COUNT = Integer.MAX_VALUE
private const val LIST_SCROLL_MIDDLE = LIST_SCROLL_COUNT / 2

@OptIn(ExperimentalComposeUiApi::class)
//@Preview
@Composable
fun TimerScreenFun(
    navController: NavHostController
) {
    val listTimer = getTimeTimer()

    val hourPickerState = rememberPickerState()
    val minutePickerState = rememberPickerState()
    val secondsPickerState = rememberPickerState()

    val coroutineScope = rememberCoroutineScope()

    var timeIndexH by remember { mutableIntStateOf(0) }
    var timeIndexM by remember { mutableIntStateOf(0) }
    var timeIndexS by remember { mutableIntStateOf(0) }

    val listStartIndexHour =
        LIST_SCROLL_MIDDLE - LIST_SCROLL_MIDDLE % hour.size - VISIBLE_ITEMS_MIDDLE + timeIndexH

    val listStartIndexMinute =
        LIST_SCROLL_MIDDLE - LIST_SCROLL_MIDDLE % minute.size - VISIBLE_ITEMS_MIDDLE + timeIndexM

    val listStartIndexSeconds =
        LIST_SCROLL_MIDDLE - LIST_SCROLL_MIDDLE % seconds.size - VISIBLE_ITEMS_MIDDLE + timeIndexS

    val listStateH =
        rememberLazyListState(initialFirstVisibleItemIndex = listStartIndexHour)
    val listStateM =
        rememberLazyListState(initialFirstVisibleItemIndex = listStartIndexMinute)
    val listStateS =
        rememberLazyListState(initialFirstVisibleItemIndex = listStartIndexSeconds)

    var stateActive by remember { mutableIntStateOf(0) }
    var jobEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(
        hourPickerState.selectedItem,
        minutePickerState.selectedItem,
        secondsPickerState.selectedItem
    ) {
        if ((listStateH.isScrollInProgress || listStateM.isScrollInProgress || listStateS.isScrollInProgress) && (jobEnabled)) {
            stateActive = 0

        }
    }

    OclockProgerTimeThemeScreenBottomBarScreens {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteColorScreen)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 35.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Picker(
                        listState = listStateH,
                        state = hourPickerState,
                        items = hour,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentSize(),
                        text = stringResource(R.string.h)
                    )

                    Picker(
                        listState = listStateM,
                        state = minutePickerState,
                        items = minute,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentSize(),
                        text = stringResource(R.string.m)
                    )

                    Picker(
                        listState = listStateS,
                        state = secondsPickerState,
                        items = seconds,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentSize(),
                        text = stringResource(R.string.s)
                    )
                }


                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .height(250.dp)
                            .fillMaxWidth()
                    ) {

                        items(items = listTimer.chunkedWithNulls(3)) { itemRows ->
                            Row {
                                itemRows.forEach {
                                    when (it) {
                                        is TimerItems.DataTimerItem -> {

                                            LaunchedEffect(key1 = stateActive) {
                                                if (it.id == stateActive) {

                                                    listStateH.scrollToItem(
                                                        listStartIndexHour
                                                    )
                                                    listStateM.scrollToItem(
                                                        listStartIndexMinute
                                                    )
                                                    listStateS.scrollToItem(
                                                        listStartIndexSeconds
                                                    )
                                                    jobEnabled = true
                                                }
                                            }

                                            ButtonTimerItemPreview(
                                                it = it,
                                                stateActive = { id ->
                                                    stateActive = id
                                                },
                                                jobEnabled = {
                                                    jobEnabled = false
                                                },
                                                timeRefactor = { h, m, s ->
                                                    timeIndexH = h
                                                    timeIndexM = m
                                                    timeIndexS = s
                                                },
                                                stateActiveItem = stateActive
                                            )
                                        }

                                        is TimerItems.AddItemsItem -> {

                                            ButtonTimerItemPreviewAddItemButton(
                                                navController = navController
                                            )

                                        }

                                        null -> {
                                            NullItem()
                                        }
                                    }
                                }
                            }
                        }
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        IconButton(
                            modifier = Modifier
                                .size(35.dp),
                            onClick = {

                                coroutineScope.launch {
                                    val scrollJobs = mutableListOf<Deferred<Unit>>()

                                    scrollJobs.add(async { listStateH.animateScrollToItem(listStateH.firstVisibleItemIndex) })
                                    scrollJobs.add(async { listStateM.animateScrollToItem(listStateM.firstVisibleItemIndex) })
                                    scrollJobs.add(async { listStateS.animateScrollToItem(listStateS.firstVisibleItemIndex) })

                                    awaitAll(*scrollJobs.toTypedArray())
                                }

                            }

                        ) {

                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.stopwatch_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp),
                                tint = DarkGray
                            )
                        }

                        IconButton(
                            modifier = Modifier
                                .padding(horizontal = 50.dp)
                                .size(70.dp)
                                .clip(shape = AbsoluteCutCornerShape(1.dp)),
                            colors = IconButtonDefaults.iconButtonColors(WhiteButtonColor),

                            onClick = {

                                coroutineScope.launch {
                                    val scrollJobs = mutableListOf<Deferred<Unit>>()

                                    scrollJobs.add(async { listStateH.animateScrollToItem(listStateH.firstVisibleItemIndex) })
                                    scrollJobs.add(async { listStateM.animateScrollToItem(listStateM.firstVisibleItemIndex) })
                                    scrollJobs.add(async { listStateS.animateScrollToItem(listStateS.firstVisibleItemIndex) })

                                    awaitAll(*scrollJobs.toTypedArray())
                                }

                            }

                        ) {

                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.start_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(start = 5.dp),
                                tint = DarkGray
                            )
                        }

                        IconButton(
                            modifier = Modifier
                                .size(35.dp),
                            onClick = {

                                coroutineScope.launch {
                                    val scrollJobs = mutableListOf<Deferred<Unit>>()

                                    scrollJobs.add(async { listStateH.animateScrollToItem(listStateH.firstVisibleItemIndex) })
                                    scrollJobs.add(async { listStateM.animateScrollToItem(listStateM.firstVisibleItemIndex) })
                                    scrollJobs.add(async { listStateS.animateScrollToItem(listStateS.firstVisibleItemIndex) })

                                    awaitAll(*scrollJobs.toTypedArray())
                                }

                            }

                        ) {

                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.bell_ring_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp),
                                tint = DarkGray
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ButtonTimerItemPreview(
    stateActive: (Int) -> Unit,
    jobEnabled: () -> Unit,
    timeRefactor: (Int, Int, Int) -> Unit,
    stateActiveItem: Int,
    it: TimerItems.DataTimerItem
) {

    Column(
        modifier = Modifier
            .padding(10.dp)
            .height(100.dp)
            .width(100.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(
                if (stateActiveItem == it.id) {
                    LightGreen
                } else {
                    WhitePickerItemColor
                }
            )
            .clickable(

                onClick = {

                    jobEnabled()

                    if (stateActiveItem == it.id) {
                        stateActive(0)
                    } else {
                        stateActive(it.id)
                    }

                    timeRefactor(it.h.toInt(), it.m.toInt(), it.s.toInt())
                }

            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = Modifier
                .padding(top = 15.dp, bottom = 7.5.dp)
                .size(20.dp),
            imageVector = ImageVector.vectorResource(it.icon),
            contentDescription = null,
            tint = if (stateActiveItem == it.id) Green else DarkGray
        )
        Box(
            modifier = Modifier
                .width(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = it.text,
                fontSize = 13.sp,
                color = if (stateActiveItem == it.id) Green else Color.Black,
                fontWeight = FontWeight(400),
                maxLines = 1
            )
        }
        Text(
            text = "${it.h}:${it.m}:${it.s}",
            fontSize = 12.sp,
            color = if (stateActiveItem == it.id) Green else DarkGray
        )
    }
}

@Composable
fun ButtonTimerItemPreviewAddItemButton(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .height(100.dp)
            .width(100.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(WhitePickerItemColor)
            .clickable(
                onClick = {
                    navController.navigate(Routes.RemoveTimer.route)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp),
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = DarkGray
        )
    }
}


@Composable
fun NullItem() {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .height(100.dp)
            .width(100.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(WhiteColorScreen)
    )
}

