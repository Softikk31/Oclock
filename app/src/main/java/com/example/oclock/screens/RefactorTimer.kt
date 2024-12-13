@file:Suppress("DEPRECATION", "SYNTHETIC_PROPERTY_WITHOUT_JAVA_ORIGIN")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.oclock.screens

import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.navigation.NavHostController
import com.example.oclock.R
import com.example.oclock.data.DataStickers
import com.example.oclock.data.addItem
import com.example.oclock.data.funcs.chunkedWithNulls
import com.example.oclock.data.getStickersTimer
import com.example.oclock.navigation.bottom.BottomBarRoutes
import com.example.oclock.picker.Picker
import com.example.oclock.picker.rememberPickerState
import com.example.oclock.ui.theme.DarkGray
import com.example.oclock.ui.theme.Green
import com.example.oclock.ui.theme.LightGray
import com.example.oclock.ui.theme.LightGreen
import com.example.oclock.ui.theme.OclockProgerTimeThemeScreenScreens
import com.example.oclock.ui.theme.WhiteColorScreen

fun extractTexts(): List<String> {
    return getStickersTimer().map { it.text }
}

private val seconds = (0..59).map { if (it < 10) "0$it" else it.toString() }
private val minute = (0..59).map { if (it < 10) "0$it" else it.toString() }
private val hour = (0..23).map { if (it < 10) "0$it" else it.toString() }
private const val VISIBLE_ITEMS_MIDDLE = 3 / 2
private const val LIST_SCROLL_COUNT = Integer.MAX_VALUE
private const val LIST_SCROLL_MIDDLE = LIST_SCROLL_COUNT / 2
private val listStickers = getStickersTimer()
private val namesStickersList = extractTexts()

//@Preview(showSystemUi = true)
@Composable
fun RemoveTimer(navController: NavHostController) {

    val hourPickerState = rememberPickerState()
    val minutePickerState = rememberPickerState()
    val secondsPickerState = rememberPickerState()

    val timeIndexH by remember { mutableIntStateOf(0) }
    val timeIndexM by remember { mutableIntStateOf(0) }
    val timeIndexS by remember { mutableIntStateOf(0) }

    val listStartIndexHour = LIST_SCROLL_MIDDLE - LIST_SCROLL_MIDDLE % hour.size - VISIBLE_ITEMS_MIDDLE + timeIndexH

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

    var textTimer by remember { mutableStateOf("Таймер") }
    var textSticker by remember { mutableStateOf("Таймер") }

    var openDialogName by remember { mutableStateOf(false) }
    var openDialogSticker by remember { mutableStateOf(false) }

    var stateActive by remember { mutableIntStateOf(1) }

    OclockProgerTimeThemeScreenScreens {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteColorScreen)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(top = 50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(BottomBarRoutes.Timer.route)
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(25.dp),
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp),
                        text = stringResource(R.string.titleRemoveTimer),
                        fontSize = 20.sp,
                        fontWeight = FontWeight(500)
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.CenterEnd),
                            onClick = {
                                addItem(
                                    text = textTimer,
                                    h = hourPickerState.selectedItem,
                                    m = minutePickerState.selectedItem,
                                    s = secondsPickerState.selectedItem,
                                    icon = if ((listStickers.firstOrNull { it.text == textSticker }?.icon
                                            ?: -1) == -1
                                    ) {
                                        R.drawable.timer_icon
                                    } else {
                                        listStickers.firstOrNull { it.text == textSticker }?.icon
                                            ?: -1
                                    }
                                )
                                navController.navigate(BottomBarRoutes.Timer.route)
                            }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(25.dp),
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    }
                }
            }

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

                Row(
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .fillMaxWidth()
                        .height(70.dp)
                        .clickable(
                            onClick = {
                                openDialogName = true
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Text(
                        modifier = Modifier
                            .padding(start = 25.dp),
                        text = stringResource(R.string.removeTimerRemoveTimerButtonTimerName),
                        fontSize = 15.sp,
                        fontWeight = FontWeight(475)
                    )


                    Box(
                        Modifier
                            .weight(1f)
                            .width(150.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(end = 5.dp),
                            text = textTimer,
                            fontSize = 12.5.sp,
                            color = DarkGray,
                            maxLines = 1
                        )
                    }

                    Icon(
                        modifier = Modifier
                            .padding(end = 20.dp),
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = DarkGray
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .clickable(
                            onClick = {
                                openDialogSticker = true
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        modifier = Modifier
                            .padding(start = 25.dp),
                        text = stringResource(R.string.removeTimerRemoveTimerButtonTimerSticker),
                        fontSize = 15.sp,
                        fontWeight = FontWeight(475)
                    )

                    Box(
                        Modifier
                            .weight(1f)
                            .width(150.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(end = 5.dp),
                            text = textSticker,
                            fontSize = 12.5.sp,
                            color = DarkGray,
                            maxLines = 1
                        )
                    }

                    Icon(
                        modifier = Modifier
                            .padding(end = 20.dp),
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = DarkGray
                    )

                }

            }
        }

        if (openDialogName) {
            DialogNameTimer(
                textTimer = textTimer
            ) {
                openDialogName = false

                it?.let { nameTimer ->
                    textTimer = nameTimer
                }
            }

        }

        if (openDialogSticker) {
            DialogStickerTimer(
                stateActive = stateActive,
                textTimer = textTimer,
            ) { nameSticker, nameTimer, id ->
                openDialogSticker = false

                nameSticker?.let { name ->
                    textSticker = name
                }

                nameTimer?.let { name ->
                    textTimer = name
                }

                id?.let {
                    stateActive = id
                }

            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogNameTimer(textTimer: String, onDialogCompleted: (String?) -> Unit) {

    var messageFlag by remember { mutableStateOf(textTimer) }

    val selectionColors = TextSelectionColors(
        handleColor = Green,
        backgroundColor = LightGreen
    )

    var buttonActive by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = { onDialogCompleted(null) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        (LocalView.current.parent as? DialogWindowProvider)?.window?.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)

        Card(
            modifier = Modifier
                .width(400.dp)
                .padding(horizontal = 18.dp)
                .padding(bottom = 12.5.dp)
                .height(intrinsicSize = IntrinsicSize.Min)
                .width(intrinsicSize = IntrinsicSize.Min),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = WhiteColorScreen)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, bottom = 5.dp)
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.removeTimerRemoveTimerButtonTimerName),
                    fontWeight = FontWeight(500),
                    fontSize = 18.5.sp
                )

                Row(
                    modifier = Modifier
                        .padding(top = 15.dp)
                ) {

                    CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {
                        BasicTextField(
                            modifier = Modifier
                                .weight(1f),
                            value = messageFlag,
                            onValueChange = {
                                if (it.length <= 20) {
                                    messageFlag = it
                                }

                                buttonActive = messageFlag.isNotEmpty()
                            },
                            textStyle = TextStyle(
                                fontSize = 16.5.sp
                            ),
                            maxLines = 1,
                            cursorBrush = SolidColor(Green),
                            decorationBox = { innerTextField ->
                                TextFieldDefaults.DecorationBox(
                                    value = messageFlag,
                                    innerTextField = innerTextField,
                                    enabled = true,
                                    singleLine = true,
                                    colors = colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                    visualTransformation = VisualTransformation.None,
                                    interactionSource = remember { MutableInteractionSource() },
                                    placeholder = {
                                        Text(
                                            text = stringResource(R.string.removeTimerRemoveTimerButtonTimerName)
                                        )
                                    },
                                    contentPadding = PaddingValues(0.dp)
                                )
                            }
                        )
                    }

                    IconButton(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(15.dp)
                            .clip(shape = CircleShape)
                            .align(alignment = Alignment.CenterVertically),
                        onClick = {
                            messageFlag = ""
                            buttonActive = false
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.LightGray,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(2.dp),
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }

                }


                HorizontalDivider(
                    modifier = Modifier
                        .padding(top = 10.dp),
                    thickness = 1.25.dp,
                    color = LightGray
                )

                Row(
                    Modifier.padding(top = 15.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f),
                        onClick = { onDialogCompleted(null) },
                        colors = ButtonDefaults.buttonColors(containerColor = WhiteColorScreen)
                    ) {
                        Text(
                            text = stringResource(R.string.buttonDialogCancel),
                            fontSize = 18.sp,
                            color = DarkGray
                        )
                    }

                    Button(
                        modifier = Modifier
                            .weight(1f),
                        onClick = { onDialogCompleted(messageFlag) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WhiteColorScreen,
                            disabledContainerColor = WhiteColorScreen
                        ),
                        enabled = buttonActive
                    ) {
                        Text(
                            text = stringResource(R.string.buttonDialogOK),
                            fontSize = 18.sp,
                            color = if (buttonActive) Green else LightGreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DialogStickerTimer(
    stateActive: Int,
    textTimer: String,
    onDialogCompleted: (String?, String?, Int?) -> Unit
) {

    Dialog(
        onDismissRequest = { onDialogCompleted(null, null, null) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        (LocalView.current.parent as? DialogWindowProvider)?.window?.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)

        Card(
            modifier = Modifier
                .width(400.dp)
                .padding(horizontal = 18.dp)
                .padding(bottom = 12.5.dp)
                .height(intrinsicSize = IntrinsicSize.Min)
                .width(intrinsicSize = IntrinsicSize.Min),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = WhiteColorScreen)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, bottom = 5.dp)
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.removeTimerRemoveTimerButtonTimerSticker),
                    fontWeight = FontWeight(500),
                    fontSize = 18.5.sp
                )

                LazyColumn(
                    modifier = Modifier
                        .width(400.dp)
                        .height(140.dp)
                ) {
                    items(listStickers.chunkedWithNulls(4)) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            item.forEach {
                                when (it) {
                                    is DataStickers -> {
                                        Box(
                                            modifier = Modifier
                                                .width(60.dp)
                                                .padding(top = 15.dp)
                                                .padding(horizontal = 5.dp)
                                                .clickable(
                                                    onClick = {
                                                        if (textTimer in namesStickersList) {
                                                            onDialogCompleted(
                                                                it.text,
                                                                it.text,
                                                                it.id
                                                            )
                                                        } else {
                                                            onDialogCompleted(
                                                                it.text,
                                                                null,
                                                                it.id
                                                            )
                                                        }
                                                    }
                                                )
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Icon(
                                                    modifier = Modifier
                                                        .size(20.dp),
                                                    imageVector = ImageVector.vectorResource(it.icon),
                                                    contentDescription = null,
                                                    tint = if (it.id == stateActive) Green else LightGray
                                                )

                                                Box(
                                                    modifier = Modifier
                                                        .width(50.dp),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = it.text,
                                                        fontSize = 10.sp,
                                                        color = if (it.id == stateActive) Green else LightGray,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    null -> {
                                        Box(
                                            modifier = Modifier
                                                .width(60.dp)
                                                .padding(top = 15.dp)
                                                .padding(horizontal = 5.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


