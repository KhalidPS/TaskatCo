package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen.component.*
import com.k.sekiro.taskmanagementapp.ui.theme.*
import kotlinx.coroutines.flow.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    navController: NavController = rememberNavController(),
    snackbarHostState: SnackbarHostState,
    paddingValues:PaddingValues = PaddingValues(),
    onEvent:(AddEditTaskEvent) -> Unit = {},
    taskTitle:TaskTextFieldState = TaskTextFieldState(),
    taskDescription:TaskTextFieldState = TaskTextFieldState(),
    taskDate:TaskTextState = TaskTextState(),
    taskStartTime:TaskTextState = TaskTextState(),
    taskEndTime:TaskTextState = TaskTextState(),
    taskPriority:PriorityMenuState = PriorityMenuState(),
    uiEvent:Flow<AddEditTaskViewModel.UiEvent>,
    calenderStartTime:Calendar,
    calenderEndTime: Calendar,
    isRestEnabled:Boolean = false,
    pickerInitialDate:Long = 0,
    onChangeSnackActionState:() -> Unit
) {




    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }

    var showTimePickerDialog by remember {
        mutableStateOf(false)
    }

    var timeType by remember {
        mutableIntStateOf(1)
    }


    val context = LocalContext.current


    val coroutineScope = rememberCoroutineScope()



    LaunchedEffect(key1 = true) {
        uiEvent.collectLatest { event ->
            when(event){
               is  AddEditTaskViewModel.UiEvent.ShowSnackBar ->{
                   onChangeSnackActionState()
                    snackbarHostState.showSnackbar(event.message)
                }

                is AddEditTaskViewModel.UiEvent.SaveButton -> {
                    navController.navigateUp()
                }

                is AddEditTaskViewModel.UiEvent.BackButton -> {
                    navController.navigateUp()
                }

            }
        }
    }



        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues),
        ){
            AddEditTaskTopSection(
                onBackClicked = { navController.navigateUp() },
                onRestClicked = { onEvent(AddEditTaskEvent.ResetField) },
                isRestEnabled = isRestEnabled && taskTitle.enabled
            )


            TaskTextField(
                text = taskTitle.text,
                hint = taskTitle.hint,
                onValueChange = {
                    onEvent(AddEditTaskEvent.EnteredTitle(it))
                },
                onFocusChanged = {
                    onEvent(AddEditTaskEvent.ChangeTitleFocus(it))
                },
                isHintVisible = taskTitle.isHintVisible,
                roundedCornerSize = 16.dp,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                enabled = taskTitle.enabled

                )

            Spacer(modifier = Modifier.height(16.dp))

            TaskTextField(
                text = taskDescription.text,
                onValueChange = {
                    onEvent(AddEditTaskEvent.EnteredDescription(it))
                },
                onFocusChanged = {
                    onEvent(AddEditTaskEvent.ChangeDescriptionFocus(it))
                },
                isHintVisible = taskDescription.isHintVisible,
                hint = taskDescription.hint,
                height = MaterialTheme.dimens.addEditScreenDimens.taskTextFieldDescriptionHeight,
                maxLines = 5,
                singleLine = false,
                contentAlignment = Alignment.Top,
                paddingValues = PaddingValues(16.dp),
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                enabled = taskDescription.enabled
            )

            Spacer(modifier = Modifier.height(16.dp))


            TaskText(
                text = taskDate.text,
                icon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "date icon",
                        modifier = Modifier.size(MaterialTheme.dimens.addEditScreenDimens.iconsSize)
                    )
                },
                onClick = {
                    showDatePickerDialog = true
                },
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                enabled = taskDate.enabled,
                clickable = taskDate.clickable
            )


            Spacer(modifier = Modifier.height(16.dp))


            TaskText(
                text = taskStartTime.text,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "date icon",
                        modifier = Modifier.size(MaterialTheme.dimens.addEditScreenDimens.iconsSize)
                    )
                },
                helperMessage = stringResource(id = R.string.start_time_helper_msg),
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                onClick = {
                    timeType = 1
                    showTimePickerDialog = true
                },
                enabled = taskStartTime.enabled,
                clickable = taskStartTime.clickable
            )


            Spacer(modifier = Modifier.height(16.dp))


            TaskText(
                text = taskEndTime.text,
                icon = {
                    Icon(
                        imageVector = Icons.Default.TimerOff,
                        contentDescription = "date icon",
                        modifier = Modifier.size(MaterialTheme.dimens.addEditScreenDimens.iconsSize)
                        )
                },
                helperMessage = stringResource(id = R.string.end_time_helper_msg),
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                onClick = {
                    timeType = 2
                    showTimePickerDialog = true
                },
                enabled = taskEndTime.enabled,
                clickable = taskEndTime.clickable
            )

            Spacer(modifier = Modifier.height(20.dp))


            TaskPriorityMenu(
                onEvent = onEvent,
                taskPriority = taskPriority.priority,
                enabled = taskPriority.enabled
            )


            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onEvent(AddEditTaskEvent.SaveTask) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth(.9f),
                enabled = taskTitle.enabled // any one title or description enabled cuz at the end they depend on task isEnteredTime value same thing
                // there's no need to make state for button just for enabled
            ) {
                Text(
                    text = stringResource(R.string.save_btn),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(MaterialTheme.dimens.addEditScreenDimens.saveButtonPadding)
                )
            }

            Spacer(modifier = Modifier.weight(1f))


        }



    /////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    if (showDatePickerDialog){
        TaskDatePicker(
            onEvent = onEvent,
            onDismissRequest = { showDatePickerDialog = false },
            onConfirmClicked = { showDatePickerDialog = false },
            onDismissClicked = { showDatePickerDialog = false },
            context = context,
            initialDate = pickerInitialDate
        )
    }



    if(showTimePickerDialog){
        TaskTimePicker(
            onEvent = onEvent,
            onDismissRequest = { showTimePickerDialog = false },
            onConfirmClicked = { showTimePickerDialog = false },
            onDismissClicked = { showTimePickerDialog = false },
            timeType = timeType,
            taskStartTime = calenderStartTime,
            taskEndTime = calenderEndTime
        )

    }



}
