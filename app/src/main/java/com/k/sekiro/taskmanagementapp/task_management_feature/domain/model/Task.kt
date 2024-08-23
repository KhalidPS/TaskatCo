package com.k.sekiro.taskmanagementapp.task_management_feature.domain.model

import android.content.Context
import android.os.*
import androidx.core.content.ContextCompat
import androidx.room.*
import com.k.sekiro.taskmanagementapp.di.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.converters.LocalTimeConverter
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.converters.LocalDateConverter
import kotlinx.parcelize.*
import java.text.*
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import com.k.sekiro.taskmanagementapp.R

@Entity
@TypeConverters(
    LocalTimeConverter::class,
    LocalDateConverter::class
)
@Parcelize
data class Task(
    val title: String = "task title",
    val description: String = "task description",
    val startTime: LocalTime = LocalTime.now(),
    val endTime: LocalTime = LocalTime.now(),
    val date: LocalDate = LocalDate.now(),
    val priority: Int = Priority.NORMAL.value,
    val isEnteredTime: Boolean = true,
    val isCompleted: Boolean = false,
    val isRestored:Boolean = false,
    val isTimerFinished:Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
):Parcelable{



    fun getFormattedTime2(): String {
        val dtf = DateTimeFormatter.ofPattern("hh:mm a")
        val startTimeFormat = startTime.format(dtf)
        val endTimeFormat = endTime.format(dtf)
        return "$startTimeFormat - $endTimeFormat"
    }

    fun getFormattedDate2():String{
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }



    fun getFormattedTime():String{

       val st =  SimpleDateFormat("hh:mm a",Locale.getDefault())
           .format(Calendar.getInstance().apply {
               set(Calendar.HOUR_OF_DAY,startTime.hour)
               set(Calendar.MINUTE,startTime.minute)
           }.time)


        val et = SimpleDateFormat("hh:mm a",Locale.getDefault())
            .format(Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY,endTime.hour)
                set(Calendar.MINUTE,endTime.minute)
            }.time)
        return "$st - $et"
    }

    fun getFormattedStartTime():String{
        return SimpleDateFormat("hh:mm a",Locale.getDefault())
            .format(Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY,startTime.hour)
                set(Calendar.MINUTE,startTime.minute)
            }.time)
    }

    fun getFormattedEndTime():String{
        return SimpleDateFormat("hh:mm a",Locale.getDefault())
            .format(Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY,endTime.hour)
                set(Calendar.MINUTE,endTime.minute)
            }.time)
    }



    fun getFormattedDate():String{
        return SimpleDateFormat("dd-MM-yyyy",Locale.getDefault())
            .format(LocalDateConverter.toCalender(date).time)
    }



    fun getFormattedDuration(context: Context): String {
        val taskDuration = getStartEndTimeDifferenceInSeconds()

        val hours = taskDuration / 3600
        val minutes = (taskDuration % 3600) / 60

        val minTxt = ContextCompat.getContextForLanguage(context).getString(R.string.min)
        val minsTxt = ContextCompat.getContextForLanguage(context).getString(R.string.mins)
        val hourTxt = ContextCompat.getContextForLanguage(context).getString(R.string.hour)
        val hoursTxt = ContextCompat.getContextForLanguage(context).getString(R.string.hours)
        val hrsTxt = ContextCompat.getContextForLanguage(context).getString(R.string.hrs)
        val resNum_1 = String.format(Locale.getDefault(),"%d",1)


        if (hours > 0) {
            //show in hours
            if (minutes > 0) {
                val timeDuration = hours + (minutes / 60f)
                return String.format(Locale.getDefault(),"%.1f $hrsTxt", timeDuration)
            } else {
                if (hours == 1L) return "$resNum_1 $hourTxt"
                return "${String.format(Locale.getDefault(),"%d",hours)} $hoursTxt"
            }
        } else {
            if (minutes in 2..10) return "${String.format(Locale.getDefault(),"%d",minutes)} $minsTxt"

            //show in minutes
            return "${String.format(Locale.getDefault(),"%d",minutes)} $minTxt"
        }

    }

    fun getStartEndTimeDifferenceInSeconds():Long{
        return Duration.between(startTime,endTime).seconds
    }


/*    fun getFormattedTime(): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val startTimeFormat = sdf.format(startTime)
        val endTimeFormat = sdf.format(endTime)
        return "$startTimeFormat - $endTimeFormat"
    }

    fun getFormattedDate():String{
        return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)
    }*/



    companion object {



        val DEFAULT_TASK = Task(
            "Programming self-taught",
            "Work hard to get what you want, today is dream but tomorrow gonna be real",
            LocalTime.now(),
            LocalTime.now(),
            LocalDate.now(),
            Priority.NORMAL.value,
            true,
            false
        )

        val DEFAULT_TASKS = listOf(
            DEFAULT_TASK,
            Task("Testing"),
            Task(title = "Testing 2", isEnteredTime = false), DEFAULT_TASK,
            Task("Testing"),
            Task(title = "Testing 2", isEnteredTime = false)
        )

    }

}

//       val time = SimpleDateFormat("MMMM dd yyyy - hh:mm a", Locale.getDefault()).format(messages[position].time)

/* LocalTime
val dtf = DateTimeFormatter.ofPattern("hh:mm a")
val startTimeFormat = startTime.format(dtf)
val endTimeFormat = endTime.format(dtf)
return "$startTimeFormat - $endTimeFormat"
*/


