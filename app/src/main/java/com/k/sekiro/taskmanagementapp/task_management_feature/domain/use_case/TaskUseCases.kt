package com.k.sekiro.taskmanagementapp.task_management_feature.domain.use_case

import com.k.sekiro.taskmanagementapp.task_management_feature.domain.repository.Repository

data class TaskUseCases(
    val addTask: AddTask,
    val addTasks: AddTasks,
    val deleteTask: DeleteTask,
    val deleteTasks: DeleteTasks,
    val getTask: GetTask,
    val getTasks: GetTasks,
    val validateTime: ValidateTime,
    val validateDate: ValidateDate,
    val getTaskId:GetTaskId,
    val scheduleReminder: ScheduleReminder
)