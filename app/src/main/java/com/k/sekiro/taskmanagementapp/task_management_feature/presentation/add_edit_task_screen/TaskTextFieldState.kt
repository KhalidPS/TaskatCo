package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen

data class TaskTextFieldState(
    val text:String = "",
    val hint:String = "",
    val isHintVisible:Boolean = true,
    val enabled:Boolean = true,
    val isFocused:Boolean = false
)