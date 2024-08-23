package com.k.sekiro.taskmanagementapp.task_management_feature.domain.alarm

import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*

interface AlarmScheduler {
    fun scheduler(task:Task)

    fun cancel(task: Task)
}