package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.analysis_screen.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.k.sekiro.taskmanagementapp.ui.theme.*
import kotlinx.coroutines.*
import kotlin.math.*

@Composable
fun AnalyticsCompleting(
    doneProgressStateProvider: () -> Float = { 0.4f },
) {
    val sweepAngle1 = remember { Animatable(initialValue = 0f) }
    val sweepAngle2 = remember { Animatable(initialValue = 0f) }
    val donePercentage = remember { Animatable(initialValue = 0f) }
    val todoPercentage = remember { Animatable(initialValue = 0f) }


    val dimens = MaterialTheme.dimens


    LaunchedEffect(key1 = null) {

        launch {
            sweepAngle1.animateTo(
                targetValue = (doneProgressStateProvider() * 360),
                animationSpec = tween(durationMillis = 1000)
            )
        }

        launch {
            sweepAngle2.animateTo(
                targetValue = (1 - doneProgressStateProvider()) * 360,
                animationSpec = tween(durationMillis = 1000)
            )
        }


        launch {
            donePercentage.animateTo(
                targetValue = (doneProgressStateProvider() * 100),
                animationSpec = tween(1000)
            )

        }

        launch {
            todoPercentage.animateTo(
                targetValue = ((1 - doneProgressStateProvider()) * 100),
                animationSpec = tween(1000)
            )
        }

    }


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Canvas(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(MaterialTheme.dimens.analysisDimens.completedChartCanvasSize)
                    .graphicsLayer {
                        compositingStrategy = CompositingStrategy.Offscreen
                    }
                    .drawWithContent {
                        drawContent()
                        if (doneProgressStateProvider() > 0f && doneProgressStateProvider() < 1.0f) {
                            drawLine(
                                color = Color.Black,
                                start = Offset(x = dimens.analysisDimens.completedChartTopLineOffset.toPx(), y = 0.dp.toPx()),
                                end = Offset(x = dimens.analysisDimens.completedChartTopLineOffset.toPx(), y = dimens.analysisDimens.completedChartTopLineOffset.toPx()),
                                strokeWidth = dimens.analysisDimens.lineBetweenArcsStroke,
                                blendMode = BlendMode.Clear
                            )
                        }
                        drawCircle(
                            color = Color.Black,
                            radius = dimens.analysisDimens.completedChartRadius,
                            blendMode = BlendMode.Clear
                        )
                    }
            ) {
                val center = Offset(size.width / 2f, size.height / 2f)
                val radius = 320f
                val startAngle1 = -90f
                val startAngle2 = (doneProgressStateProvider() * 360) - 90f

                drawArc(
                    color = CoolGreen,
                    startAngle = startAngle1,
                    sweepAngle = sweepAngle1.value,
                    useCenter = true,
                )

                drawArc(
                    color = CoolRed2,
                    startAngle = startAngle2,
                    sweepAngle = sweepAngle2.value,
                    useCenter = true
                )

                val offsetAngle = startAngle1 + sweepAngle1.value
                val offsetRadians = Math.toRadians(offsetAngle.toDouble())
                val xOffset = center.x + (radius * cos(offsetRadians)).toFloat() * dimens.analysisDimens.completedChartBottomLineMultiply
                val yOffset = center.y + (radius * sin(offsetRadians)).toFloat() * dimens.analysisDimens.completedChartBottomLineMultiply

                if (doneProgressStateProvider() > 0f && doneProgressStateProvider() < 1.0f) {
                    drawLine(
                        color = Color.Red,
                        start = center,
                        end = Offset(xOffset, yOffset),
                        strokeWidth = dimens.analysisDimens.lineBetweenArcsStroke,
                        blendMode = BlendMode.Clear
                    )
                }

            }
            Column {
                Text(
                    text = "${donePercentage.value.toInt()}%",
                    modifier = Modifier
                        .width(dimens.analysisDimens.completedChartPercentageBoxWidth)
                        .background(color = CoolGreen, shape = RoundedCornerShape(4.dp))
                        .padding(dimens.analysisDimens.completedChartPercentageBoxPadding),
                    color = Color.White,
                    //fontFamily = SfDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "${todoPercentage.value.toInt()}%",
                    modifier = Modifier
                        .width(dimens.analysisDimens.completedChartPercentageBoxWidth)
                        .background(
                            color = CoolRed2,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(dimens.analysisDimens.completedChartPercentageBoxPadding),
                    color = Color.White,
                    //fontFamily = SfDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    textAlign = TextAlign.Center
                )
            }

        }

        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Canvas(modifier = Modifier.size(dimens.analysisDimens.completedChartColorCircleSize)) {
                    drawCircle(color = CoolGreen)
                }
                Text(
                    text = stringResource(id = com.k.sekiro.taskmanagementapp.R.string.done),
                    //fontFamily = SfDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Canvas(modifier = Modifier.size(dimens.analysisDimens.completedChartColorCircleSize)) {
                    drawCircle(color = CoolRed2)
                }
                Text(
                    text = stringResource(id = com.k.sekiro.taskmanagementapp.R.string.todo),
                    //fontFamily = SfDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0x66DFDFDF))
        )
    }
}