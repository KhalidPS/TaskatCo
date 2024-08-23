package com.k.sekiro.taskmanagementapp.task_management_feature.domain.repository


import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getAllTasks(): Flow<List<Task>>


    suspend fun getTaskById(id:Int): Task?


    suspend fun insertTask(task: Task)


    suspend fun insertTasks(tasks: List<Task>)


    suspend fun deleteTask(task: Task)


    suspend fun deleteTasks(tasksIds:List<Int>)


    suspend fun hasSameStartTime(date:String,time:String,id: Int?):Task?


    suspend fun hasSameEndTime(date:String,time:String,id: Int?):Task?

    suspend fun hasHighPriority(date:String,id:Int):Task?

    suspend fun getTaskId(startTime:String,date:String):Int

    suspend fun ifTimeBetweenOtherTasksTime(date: String,time: String,id: Int):Task?

}