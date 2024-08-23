package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen.component

import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*

data class PriorityMenuState(
    val priority: Priority = Priority.NORMAL,
    val enabled:Boolean = true
)