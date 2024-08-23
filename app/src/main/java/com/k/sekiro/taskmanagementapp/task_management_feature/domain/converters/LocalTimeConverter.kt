package com.k.sekiro.taskmanagementapp.task_management_feature.domain.converters

import androidx.room.TypeConverter
import java.text.*
import java.time.*
import java.util.*

object LocalTimeConverter {
	@TypeConverter
	@JvmStatic
	fun fromString(value: String?): LocalTime? {
		return value?.let { LocalTime.parse(it) }
	}

	@TypeConverter
	@JvmStatic
	fun toString(localTime: LocalTime?): String? {
		return localTime?.toString()
	}


	fun fromCalender(calendar: Calendar):LocalTime{
		return LocalTime.of(
			calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE),
			0
		)
	}

	fun toCalender(localTime: LocalTime):Calendar{
		return Calendar.getInstance().apply {
			set(Calendar.HOUR_OF_DAY,localTime.hour)
			set(Calendar.MINUTE,localTime.minute)
			set(Calendar.SECOND,0)
		}
	}

/*	@TypeConverter
	@JvmStatic
	fun fromString(value: String?): Date? {
		return value?.let { Date.from(Instant.parse(it)) }
	}

	@TypeConverter
	@JvmStatic
	fun toString(time: Date?): String? {
		return time?.let {
			SimpleDateFormat("hh:mm a", Locale.getDefault()).format(it)
		}
	}*/
}