package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.Task
import com.k.sekiro.taskmanagementapp.ui.theme.dimens

@Composable
fun TopSection(todayTasks: List<Task>) {

    val numberOfTodayTasks = remember(todayTasks) {todayTasks.size}
    val numberOfCompletedTasks = remember (todayTasks){ todayTasks.filter { it.isCompleted }.size }
    val unCompletedTasks = remember(todayTasks) {todayTasks.filter { !it.isCompleted }}

    LazyRow(
        contentPadding = PaddingValues(MaterialTheme.dimens.homeScreenDimens.topSectionContentPadding)
    ) {
        item {
            CompletedTaskCountCard(numberOfTodayTasks,numberOfCompletedTasks)
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.homeScreenDimens.spacerBetweenLazyRowItems))
        }

        if (unCompletedTasks.size >= 1){
            item {
                SomeUnCompletedTaskCard(unCompletedTasks[0])
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.homeScreenDimens.spacerBetweenLazyRowItems))
            }
        }

        if (unCompletedTasks.size >=2){
            item {
                SomeUnCompletedTaskCard(unCompletedTasks[1])
            }
        }



    }

}