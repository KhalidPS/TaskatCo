package com.k.sekiro.taskmanagementapp.task_management_feature.domain.converters

import androidx.room.TypeConverter
import java.text.*
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

object LocalDateConverter {

	@TypeConverter
	@JvmStatic
	fun fromString(value: String?): LocalDate? {
		return value?.let { LocalDate.parse(it) }
	}

	@TypeConverter
	@JvmStatic
	fun toString(value: LocalDate?): String? {
		val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
		return value?.format(formatter)
	}


	fun fromCalender(calender:Calendar):LocalDate{
		return LocalDate.of(
			calender.get(Calendar.YEAR),
			calender.get(Calendar.MONTH) + 1,// +1 here cuz when we convert calender to LocaleDate
			// the Calender.MONTH give u the month before current month and the opposite
			// if u want to convert from LocaleDate to Calender u should -1 as in
			// getFormattedDate in task data class
			calender.get(Calendar.DAY_OF_MONTH)
		)
	}

	fun toCalender(date:LocalDate):Calendar{
		return Calendar.getInstance().apply {
			set(Calendar.DAY_OF_MONTH,date.dayOfMonth)
			set(Calendar.MONTH,date.monthValue -1)
			set(Calendar.YEAR,date.year)
		}
	}

	fun millisToCalender(timeMillis:Long):Calendar{
		return Calendar.getInstance().apply {
			timeInMillis = timeMillis
		}
	}


/*	@TypeConverter
	@JvmStatic
	fun fromString(value: String?): Date? {
		return value?.let { Date.from(Instant.parse(it)) }
	}

	@TypeConverter
	@JvmStatic
	fun toString(date: Date?): String? {

		return date?.let {
			SimpleDateFormat("dd-mm-yyyy", Locale.getDefault()).format(it)
		}*/


}



