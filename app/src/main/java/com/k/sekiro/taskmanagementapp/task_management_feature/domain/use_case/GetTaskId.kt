package com.k.sekiro.taskmanagementapp.task_management_feature.domain.use_case

import com.k.sekiro.taskmanagementapp.task_management_feature.domain.repository.*

class GetTaskId (private val repository: Repository){
    suspend operator fun invoke(startTime:String, date:String):Int{
        return repository.getTaskId(startTime, date)
    }
}