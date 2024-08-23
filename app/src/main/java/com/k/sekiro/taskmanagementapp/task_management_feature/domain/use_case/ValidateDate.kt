package com.k.sekiro.taskmanagementapp.task_management_feature.domain.use_case

import android.content.Context
import android.widget.*
import androidx.core.content.ContextCompat
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.converters.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen.*
import com.k.sekiro.taskmanagementapp.R
import java.sql.*
import java.text.*
import java.time.*
import java.util.*

class ValidateDate(private val context: Context){
    operator fun invoke(
        date:Long
    ):String{

        val calendarDate = LocalDateConverter.millisToCalender(date)

        val dateString = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            .format(calendarDate.time)

        val currentDate = Calendar.getInstance()
        val currentDateString = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            .format(currentDate.time)

        return if (dateString == currentDateString){ //selectedDate.before(Calendar.getInstance())
            ""
        }else if (calendarDate.after(currentDate)){
            ""
        }else{
            ContextCompat.getContextForLanguage(context).getString(R.string.invalid_date_msg)
        }
    }
}