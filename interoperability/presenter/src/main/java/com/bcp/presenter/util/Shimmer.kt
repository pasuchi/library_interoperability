package com.bcp.presenter.util

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ShimmerList(
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp)) {
        Spacer(
            modifier = Modifier
                .height(24.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(5.dp))
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Spacer(
            modifier = Modifier
                .height(24.dp)
                .width(200.dp)
                .clip(RoundedCornerShape(5.dp))
                .shimmerEffect()
        )
    }
}


fun Modifier.shimmerEffect(): Modifier = composed {
    var size = remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.value.width.toFloat(),
        targetValue = 2 * size.value.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                1000,
                easing = FastOutLinearInEasing
            )
        )
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5)
            ), start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.value.width.toFloat(), size.value.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size.value = it.size
        }
}
