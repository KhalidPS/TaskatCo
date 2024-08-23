package com.k.sekiro.taskmanagementapp.task_management_feature.data.repository

import com.k.sekiro.taskmanagementapp.task_management_feature.data.database.local.TaskDao
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.Task
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(val taskDao: TaskDao):Repository {
    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    override suspend fun getTaskById(id: Int): Task? {
        return taskDao.getTaskById(id)
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    override suspend fun insertTasks(tasks: List<Task>) {

            taskDao.insertTasks(tasks)

    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override suspend fun deleteTasks(tasksIds: List<Int>) {
        taskDao.deleteTasks(tasksIds)
    }

    override suspend fun hasSameStartTime(date: String, time: String, id: Int?): Task? {
        return taskDao.hasSameStartTime(date, time, id)
    }

    override suspend fun hasSameEndTime(date: String, time: String, id: Int?): Task? {
        return taskDao.hasSameEndTime(date, time, id)
    }

    override suspend fun hasHighPriority(date: String,id: Int): Task? {
        return taskDao.hasHighPriority(date,id)
    }

    override suspend fun getTaskId(startTime: String, date: String): Int {
        return taskDao.getTaskId(startTime, date)
    }

    override suspend fun ifTimeBetweenOtherTasksTime(
        date: String,
        time: String,
        id: Int
    ) :Task?{
        return taskDao.ifTimeBetweenOtherTasksTime(date,time,id)
    }


}