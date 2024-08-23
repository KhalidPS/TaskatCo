package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen.component


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen.*
import java.text.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTimePicker(
    onEvent: (AddEditTaskEvent) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmClicked: () -> Unit,
    onDismissClicked: () -> Unit,
    timeType: Int,
    taskStartTime: Calendar, // to compare with end time
    taskEndTime: Calendar    // compare with start time
) {
    val pickerState = rememberTimePickerState(
        initialHour = when (timeType) {
            1 -> taskStartTime.get(Calendar.HOUR_OF_DAY)
            2 -> taskEndTime.get(Calendar.HOUR_OF_DAY)
            else -> 12
        },
        initialMinute = when (timeType) {
            1 -> taskStartTime.get(Calendar.MINUTE)
            2 -> taskEndTime.get(Calendar.MINUTE)
            else -> 12
        }

    )

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,

        ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            TimePicker(
                state = pickerState,
                modifier = Modifier.padding(16.dp),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            ) {

                Button(onClick = onDismissClicked) {
                    Text(
                        text = stringResource(id = R.string.cancel_btn),
                        fontSize = 24.sp
                    )
                }


                Button(onClick = {
                    onConfirmClicked()

                    val calender = Calendar.getInstance()
                    calender.set(Calendar.HOUR_OF_DAY, pickerState.hour)
                    calender.set(Calendar.MINUTE, pickerState.minute)
                    calender.isLenient = false

                    val time = SimpleDateFormat("hh:mm a", Locale.getDefault())
                        .format(calender.time)





                    when (timeType) {

                        1 -> {
                            onEvent(AddEditTaskEvent.EnteredStartTime(time, calender))
                        }


                        2 -> {
                            onEvent(AddEditTaskEvent.EnteredEndTime(time, calender))
                        }
                    }


                }) {
                    Text(
                        text = stringResource(id = R.string.done_btn),
                        fontSize = 24.sp
                    )
                }

            }
        }
    }
}