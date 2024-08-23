package com.k.sekiro.taskmanagementapp.task_management_feature.domain.use_case

import com.k.sekiro.taskmanagementapp.task_management_feature.domain.repository.Repository

class DeleteTasks(private val repository: Repository) {
    suspend operator fun invoke(tasksIds:List<Int>){
        repository.deleteTasks(tasksIds)
    }
}