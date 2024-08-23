package com.k.sekiro.taskmanagementapp.task_management_feature.data.database.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAllTasks():Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskById(id:Int):Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks:List<Task>)

    @Delete
    suspend fun deleteTask(task:Task)

    @Query("DELETE FROM task WHERE id in (:tasksIds)")
    suspend fun deleteTasks(tasksIds:List<Int>)

    @Query("SELECT * FROM task WHERE startTime = :time AND date = :date And id NOT in (:id) LIMIT 1")
    suspend fun hasSameStartTime(date:String,time:String,id: Int?):Task?

    @Query("SELECT * FROM task WHERE endTime = :time AND date = :date And id NOT in (:id) LIMIT 1")
    suspend fun hasSameEndTime(date:String,time:String,id: Int?):Task?

    @Query("SELECT * FROM task WHERE date = :date AND priority = 1 AND id != :id LIMIT 1")
    suspend fun hasHighPriority(date:String,id:Int):Task?

    @Query("SELECT id FROM task WHERE date = :date AND startTime = :startTime")
    suspend fun getTaskId(startTime:String,date:String):Int

    @Query("SELECT * FROM task WHERE date = :date AND :time > startTime AND :time < endTime AND id != :id LIMIT 1")
    suspend fun ifTimeBetweenOtherTasksTime(date: String,time: String,id: Int):Task?


}