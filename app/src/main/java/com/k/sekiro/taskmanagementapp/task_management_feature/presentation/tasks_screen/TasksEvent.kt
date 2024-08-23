package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen

import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.Task
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.utils.TaskOrder

sealed class TasksEvent {
    data class Filter(val taskOrder: TaskOrder):TasksEvent()
    data class DeleteTask(val task:Task):TasksEvent()
    data class Checked(val task:Task):TasksEvent()
    object RestoreTask:TasksEvent()
    object ToggleFilterSection:TasksEvent()

}