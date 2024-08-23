package com.k.sekiro.taskmanagementapp.task_management_feature.domain.use_case

import android.content.Context
import android.util.*
import androidx.core.content.ContextCompat
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.converters.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.Task
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.repository.Repository
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.utils.InvalidTaskException
import java.time.*
import java.time.format.*
import java.util.Calendar


class AddTask(private val repository: Repository,private val context:Context) {

    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(task: Task){

        val dateString = task.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        val currentDateString = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        val date = LocalDateConverter.toCalender(task.date)
        val currentDate = Calendar.getInstance()
        val startTime = LocalTimeConverter.toCalender(task.startTime)
        val endTime = LocalTimeConverter.toCalender(task.endTime)
        val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"))

        Log.e("ks","the current date: $currentDateString")
        Log.e("ks","the date : $dateString ")

        if (!task.isRestored){// the check here cuz if we want to restore deleted task we don't need to
            //validate it anymore cuz this validation when add new task or edit existing one
            if (task.title.isBlank()){
                throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.empty_title_msg))
            }

            if (task.description.isBlank()){
                throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.empty_decription_msg))
            }

            if (dateString  != currentDateString &&  date.before(currentDate)){
                throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.current_time_msg))
            }

            if (task.startTime.format(DateTimeFormatter.ofPattern("hh:mm a")) == currentTime && dateString == currentDateString){
                throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.current_time_start_time_msg))
            }

            if (startTime.before(Calendar.getInstance()) && dateString == currentDateString){
                throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.start_time_for_today_out_msg))
            }

            if (task.endTime.format(DateTimeFormatter.ofPattern("hh:mm a")) == currentTime && dateString == currentDateString){
                throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.current_time_end_time_msg))
            }

            if (endTime.before(startTime)){
                throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.end_time_before_start_time_msg))
            }

            if (startTime.after(endTime)){
                throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.start_time_after_end_time_msg))
            }

            if (task.endTime.format(DateTimeFormatter.ofPattern("hh:mm a")) ==
                task.startTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
            ){
                throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.end_time_as_start_time_msg))
            }


            if (task.endTime.format(DateTimeFormatter.ofPattern("hh:mm a")) == currentTime && dateString == currentDateString){
                throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.current_time_end_time_msg))
            }

            /*        repository.getAllTasks().map { list ->
                        list.filter {
                            it.getFormattedStartTime() == task.getFormattedStartTime() && it.getFormattedDate() == task.getFormattedDate()
                                    && it.id != task.id
                        }
                    }.collectLatest { tasks ->
                        Log.e("ks",tasks.isNotEmpty().toString() + " here")
                        if (tasks.isNotEmpty()){
                            throw InvalidTaskException("There's a task with the same start time for this date, " +
                                    "please change start time for this task")
                        }
                    }*/

            if (repository.hasSameStartTime(task.date.toString(),task.startTime.toString(),task.id) != null){
                throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.task_same_start_time_for_date_msg))
            }


            if (repository.hasSameEndTime(task.date.toString(),task.endTime.toString(),task.id) != null){
                throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.task_same_end_time_for_date_msg))
            }


            if (task.priority == 1){
                if (repository.hasHighPriority(task.date.toString(),task.id) != null){
                    throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.high_priority_for_same_date_msg))
                }
            }

            Log.e("ks","add task " +task.startTime.toString())
            Log.e("ks","add task " +task.endTime.toString())

            /*        repository.getAllTasks().map { tasks ->
                        tasks.filter { task.date.toString() == it.date.toString() }
                            .ifEmpty { emptyList() }

                    }.collectLatest { tasks ->
                        if (tasks.isNotEmpty()){
                            tasks.forEach {item ->
                                if (startTime.after(LocalTimeConverter.toCalender(item.startTime)) && startTime.before(LocalTimeConverter.toCalender(item.endTime))){
                                    throw InvalidTaskException("Start time can't be between start time and end time for other task")
                                }

                                if (endTime.after(LocalTimeConverter.toCalender(item.startTime)) && endTime.before(LocalTimeConverter.toCalender(item.endTime))){
                                    throw InvalidTaskException("End time can't be between start time and end time for other task")
                                }
                            }
                        }

                    }*/

            if (repository.ifTimeBetweenOtherTasksTime(task.date.toString(),task.startTime.toString(),task.id) != null){
                throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.start_time_between_other_time_msg))
            }

            if (repository.ifTimeBetweenOtherTasksTime(task.date.toString(),task.endTime.toString(),task.id) != null){
                throw InvalidTaskException(ContextCompat.getContextForLanguage(context).getString(R.string.end_time_between_other_time_msg))

            }
        }



        repository.insertTask(task)
    }

}