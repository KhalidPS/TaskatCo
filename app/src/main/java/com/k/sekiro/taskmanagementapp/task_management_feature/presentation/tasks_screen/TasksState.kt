package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen

import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.Task
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.utils.OrderType
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.utils.TaskOrder

data class TasksState(
    val tasks:List<Task> = emptyList(),
    val taskOrder:TaskOrder = TaskOrder.Default(OrderType.Ascending),
    val isOrderSectionVisible:Boolean = false,
    val isStopTimerButtonEnable:Boolean = false,
    val todayTasks:List<Task> = emptyList()
)