package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen.component

import android.content.Context
import android.widget.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen.*
import java.sql.*
import java.text.*
import java.time.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDatePicker(
    onEvent:(AddEditTaskEvent) -> Unit,
    onDismissRequest:() -> Unit,
    onConfirmClicked:() -> Unit,
    onDismissClicked:() -> Unit,
    context:Context,
    initialDate:Long
) {
    /////////   solution 2
//        val calendar = Calendar.getInstance()
//        calendar.time.time
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate
    )

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Row {


                Button(onClick = onDismissClicked) {
                    Text(
                        text = stringResource(R.string.cancel_btn),
                        fontSize = 24.sp
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Button(onClick = {
                    onConfirmClicked()
                    val timeStamp = Timestamp.from(datePickerState.selectedDateMillis?.let {
                        Instant.ofEpochMilli(it) })
                    val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(timeStamp)


//    or ->             val date = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
//                         .atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))


//                val selectedDate = Calendar.getInstance().apply {
//                    timeInMillis = datePickerState.selectedDateMillis!!
//                }

                    onEvent(AddEditTaskEvent.EnteredDate(date,datePickerState.selectedDateMillis!!))



                }) {
                    Text(
                        text = stringResource(R.string.done_btn),
                        fontSize = 24.sp
                    )

                }  
                
            }

        },

        properties = DialogProperties(
            usePlatformDefaultWidth = true
        )

    ) {

        DatePicker(
            state = datePickerState,
            dateFormatter = remember {
                DatePickerDefaults.dateFormatter( selectedDateSkeleton = "dd-MM-yyyy")
            }
        )
        


    }
}