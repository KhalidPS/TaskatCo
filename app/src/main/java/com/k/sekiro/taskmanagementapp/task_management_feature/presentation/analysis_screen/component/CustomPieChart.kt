package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.analysis_screen.component

import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.*
import com.k.sekiro.taskmanagementapp.ui.theme.*

@Composable
fun CustomPieChart(
    data: List<Long>,
    arcWidth: Dp = MaterialTheme.dimens.analysisDimens.timePieChartArcWidth,
    startAngle: Float = -180f,
    pieChartSize: Dp = MaterialTheme.dimens.analysisDimens.timePieChartSize,
    animDuration: Int = 1000,
) {
    // calculate each arc value
    val totalSum = data.sum()
    val arcValues = mutableListOf<Float>()
    data.forEachIndexed { index, value ->
        val arc = value.toFloat() / totalSum.toFloat() * 360f
        arcValues.add(index, arc)
    }

    var newStartAngle = startAngle

    // animations
    val animationProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        animationProgress.animateTo(1f, animationSpec = tween(animDuration))
    }

    // draw pie chart
    val totalColors = pieChartColors.size

    Column(
        modifier = Modifier.size(pieChartSize * 1.5f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(pieChartSize)
                .rotate(90f * animationProgress.value)
        ) {
            arcValues.forEachIndexed { index, arcValue ->
                drawArc(
                    color = pieChartColors[index % totalColors],
                    startAngle = newStartAngle,
                    useCenter = false,
                    sweepAngle = arcValue * animationProgress.value,
                    style = Stroke(width = arcWidth.toPx())
                )
                newStartAngle += arcValue
            }
        }
    }

}