package com.k.sekiro.taskmanagementapp.task_management_feature.domain.model

import java.time.LocalTime

data class AlarmItem(
    val time:LocalTime,
    val title:String,
    val id:Int,
)