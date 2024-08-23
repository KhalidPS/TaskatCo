package com.k.sekiro.taskmanagementapp.task_management_feature.domain.use_case

import com.k.sekiro.taskmanagementapp.task_management_feature.data.alarm.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.alarm.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*

class ScheduleReminder(private val alarmScheduler: AlarmScheduler) {

    fun schedule(task: Task){
        alarmScheduler.scheduler(task)
    }

    fun cancel(task: Task){
        alarmScheduler.cancel(task)
    }
}