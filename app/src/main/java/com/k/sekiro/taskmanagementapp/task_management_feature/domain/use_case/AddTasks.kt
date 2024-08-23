package com.k.sekiro.taskmanagementapp.task_management_feature.domain.use_case

import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.Task
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.repository.Repository

class AddTasks (private val repository: Repository){
    suspend operator fun invoke(tasks:List<Task>){
        repository.insertTasks(tasks)
    }
}