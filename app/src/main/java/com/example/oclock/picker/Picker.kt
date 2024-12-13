package com.example.oclock.picker


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oclock.fontTime
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


private const val visibleItemsMiddle = 3 / 2
private val fadingEdgeGradient: Brush = createFadingEdgeGradient()


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Picker(
    state: PickerState = rememberPickerState(),
    items: List<String>,
    text: String,
    listState: LazyListState,
    modifier: Modifier
) {

    fun getItem(index: Int) = items[index % items.size]
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val itemHeightPixels = remember { mutableIntStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.intValue)

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> getItem(index + visibleItemsMiddle) }
            .distinctUntilChanged()
            .collect { item -> state.selectedItem = item }
    }


    Box(modifier = modifier) {

        Row(
            modifier = Modifier
                .wrapContentSize()
        ) {
            LazyColumn(
                state = listState,
                flingBehavior = flingBehavior,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .height(itemHeightDp * 3)
                    .fadingEdge(fadingEdgeGradient)
            ) {
                items(Integer.MAX_VALUE) { index ->
                    Text(
                        text = getItem(index),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(fontSize = 40.sp),
                        modifier = Modifier
                            .onSizeChanged { size -> itemHeightPixels.intValue = size.height }
                            .then(
                                Modifier.padding(vertical = 14.dp)
                            ),
                        fontFamily = fontTime
                    )
                }
            }

            Text(
                modifier = Modifier
                    .padding(top = 95.dp),
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight(500)
            )

        }

    }
}

fun createFadingEdgeGradient(): Brush {
    return Brush.verticalGradient(
        0f to Color.Transparent.copy(alpha = 0.001f),
        0.1f to Color.White.copy(alpha = 0.3f),
        0.2f to Color.White.copy(alpha = 0.3f),
        0.3f to Color.White.copy(alpha = 0.5f),
        0.4f to Color.Black,
        0.5f to Color.Black,
        0.6f to Color.Black,
        0.7f to Color.White.copy(alpha = 0.5f),
        0.8f to Color.White.copy(alpha = 0.3f),
        0.9f to Color.White.copy(alpha = 0.3f),
        1f to Color.Transparent.copy(alpha = 0.001f)
    )
}

private fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

@Composable
private fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }

