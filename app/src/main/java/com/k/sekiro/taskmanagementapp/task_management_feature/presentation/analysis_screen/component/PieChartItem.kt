package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.analysis_screen.component

import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*
import com.k.sekiro.taskmanagementapp.ui.theme.*
import kotlinx.coroutines.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PieChartItemComponent(task: Task, itemColor: Color, animDelay: Int = 100,context: Context) {

    val alphaAnimation = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(animDelay) {
        delay(animDelay.toLong())
        alphaAnimation.animateTo(targetValue = 1f, animationSpec = tween(1000))
    }

    Box(
        modifier = Modifier
            .graphicsLayer {
                alpha = alphaAnimation.value
            }
            .fillMaxWidth()
            .background(if(AppThemeSettings.isDarkTheme) Color.Black.copy(alpha = .5f) else Color.LightGray.copy(alpha = .1f), RoundedCornerShape(8.dp))
            .padding(
                vertical = MaterialTheme.dimens.analysisDimens.itemContainerVerticalPadding,
                horizontal = MaterialTheme.dimens.analysisDimens.itemContainerHorizontalPadding
            )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(MaterialTheme.dimens.analysisDimens.colorBoxSize)
                    .background(itemColor, CircleShape),
            )

            Spacer(modifier = Modifier.width(0.dp))

            Text(
                text = task.title,
                modifier = Modifier
                    .basicMarquee(delayMillis = 1000)
                    .weight(1f),
                //style = taskTextStyle,
            )

            Text(
                text = task.getFormattedDuration(context),
                //style = taskTextStyle,
            )
        }
    }
}