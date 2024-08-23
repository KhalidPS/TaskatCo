package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.analysis_screen.component

import android.content.Context
import android.provider.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*
import com.k.sekiro.taskmanagementapp.ui.theme.*

@Composable
fun TimeAnalysisPage(context: Context,todayTasks:List<Task>) {
    val totalColors = pieChartColors.size

    val sortedTasks = todayTasks.sortedByDescending { it.getStartEndTimeDifferenceInSeconds() }

    Column {

        val pieChartBgGradient = MaterialTheme.colorScheme.onBackground.copy(alpha = .1f)/*Brush.verticalGradient(
            listOf(
                Color.White.copy(alpha = if (AppThemeSettings.isDarkTheme) .5f else .3f),
                Color.Black.copy(alpha = .3f)
                ),
        )*/
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    4.dp,
                    RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                    spotColor = if (AppThemeSettings.isDarkTheme) Color.White.copy(alpha = .5f) else Color.Black.copy(.3f)

                )
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            val data = sortedTasks.map { it.getStartEndTimeDifferenceInSeconds() }
            CustomPieChart(
                data = data,
                pieChartSize = MaterialTheme.dimens.analysisDimens.timePieChartSize)

        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.analysisDimens.spacerBetweenPieChartAndItems))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            itemsIndexed(items = sortedTasks) { index, item ->
                PieChartItemComponent(
                    task = item,
                    itemColor = pieChartColors[index % totalColors],
                    animDelay = index * 100,
                    context = context
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.analysisDimens.spacerBetweenLazyItems))
            }
        }
    }
}