package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen

import android.content.*
import android.util.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import androidx.datastore.core.*
import androidx.datastore.preferences.core.*
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.k.sekiro.taskmanagementapp.*
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.utils.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen.component.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.*
import com.k.sekiro.taskmanagementapp.ui.theme.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@Composable
fun TasksScreen(
    navController: NavHostController = rememberNavController(),
    drawerState:DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    paddingValues: PaddingValues = PaddingValues(),
    state : TasksState,
    onEvent: (TasksEvent) -> Unit,
    uiEvent: Flow<TaskScreenUiEvent>,
    context:Context,
    alarmService: AlarmService,
    dataStore: DataStore<Preferences>,
    onChangeSnackActionState:() -> Unit
) {




    LaunchedEffect(key1 = true) {
        uiEvent.collectLatest {
            when(it){
                is TaskScreenUiEvent.ShowSnackBar -> {
                    onChangeSnackActionState()
                    val result = snackbarHostState.showSnackbar(
                        it.msg,
                        "Undo",
                        duration = SnackbarDuration.Short
                    )

                    if (result == SnackbarResult.ActionPerformed){
                        onEvent(TasksEvent.RestoreTask)
                    }
                }
            }
        }
    }


   // if (ScreenOrientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitTasksScreen(
            navController = navController,
            scope = scope,
            paddingValues = paddingValues,
            state = state,
            onEvent = onEvent,
            context = context,
            alarmService = alarmService,
            dataStore = dataStore
        )
    //}


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PortraitTasksScreen(
    navController:NavHostController = rememberNavController(),
    scope:CoroutineScope = rememberCoroutineScope(),
    paddingValues: PaddingValues = PaddingValues(),
    state: TasksState,
    onEvent: (TasksEvent) -> Unit,
    context: Context,
    alarmService: AlarmService,
    dataStore: DataStore<Preferences>
) {

    Column (Modifier.padding(paddingValues)){



        TopSection(state.todayTasks)

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.homeScreenDimens.spacerBetweenTopSectionAndTasksHeader))


        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.al_tasks),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(MaterialTheme.dimens.homeScreenDimens.tasksHeaderPadding)
                    .weight(1f)
            )

            IconButton(onClick = { onEvent(TasksEvent.ToggleFilterSection)  }) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "filter icon",
                    modifier = Modifier.size(MaterialTheme.dimens.homeScreenDimens.filterIconSize)
                )
            }
        }

        AnimatedVisibility(
            visible = state.isOrderSectionVisible,
            enter =
                fadeIn() + expandVertically(
                    animationSpec = tween(
                        durationMillis = 500
                    )
                ) ,
            exit = fadeOut() + shrinkVertically(
                animationSpec = tween(
                  durationMillis = 500,
                )
            )
        ) {

            FilterSection(
                order = state.taskOrder
            ){ order ->
                onEvent(TasksEvent.Filter(order))
            }

        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.homeScreenDimens.spacerBetweenFilterSectionAndTasksCards))

        if (state.tasks.isEmpty()){
            Image(
                painter = painterResource(id = R.drawable.no_tasks),
                contentDescription = "no task image",
                modifier = Modifier.fillMaxHeight()
            )
        }else{
            if ((state.taskOrder is TaskOrder.Today && state.taskOrder.pendedHighPriority) || state.taskOrder is TaskOrder.Default){
                if (state.tasks.isNotEmpty() && state.tasks[0].priority == 1){
                    SwipeToDeleteContainer(
                        item = state.tasks[0],
                        onDelete = { task ->
                            onEvent(TasksEvent.DeleteTask(task))


                        },
                        animationDuration = 500,
                        modifier = Modifier.padding(horizontal = MaterialTheme.dimens.homeScreenDimens.highPriorityTaskCardPadding)
                    ) {
                        TaskCard(
                            task =  state.tasks[0] ,
                            elevation = 16.dp,
                            borderStroke = null,
                            showPinnedIcon = true,
                            modifier = Modifier.zIndex(1f),
                            alarmService = alarmService,
                            dataStore = dataStore,
                            onCheckedChange = {onEvent(TasksEvent.Checked(it))},
                            scope = scope
                        ){
                            navController.navigate(Screens.AddEdit.route + "?taskId=${if (state.tasks.isNotEmpty()) state.tasks[0].id else return@TaskCard}"){
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                        }
                    }
                }

                LazyColumn(
                    contentPadding = PaddingValues(
                        start = MaterialTheme.dimens.homeScreenDimens.tasksLazyColumnPaddingValues,
                        end = MaterialTheme.dimens.homeScreenDimens.tasksLazyColumnPaddingValues,
                        top = MaterialTheme.dimens.homeScreenDimens.tasksLazyColumnPaddingValues
                    ),
                ) {
                    itemsIndexed(
                        items = state.tasks,
                        key = {_, item ->
                            item.id
                        }
                    ) { index , item ->
                        if (item.priority != 1){

                            SwipeToDeleteContainer(
                                item = item,
                                onDelete = { task ->
                                    onEvent(TasksEvent.DeleteTask(item))


                                },
                                animationDuration = 500,
                                modifier = Modifier.animateItemPlacement(
                                    spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = 50f,
                                    )
                                )
                            ) {

                                TaskCard(
                                    task = item,
                                    alarmService = alarmService,
                                    dataStore = dataStore,
                                    scope = scope,
                                    onCheckedChange = {onEvent(TasksEvent.Checked(it))},
                                ){
                                    navController.navigate(Screens.AddEdit.route + "?taskId=${item.id}"){
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    Log.e("ks","the id is :${item.id}")

                                }

                            }
                            Spacer(modifier = Modifier.height(MaterialTheme.dimens.homeScreenDimens.spacerBetweenListItems))
                        }

                    }
                }



            }else{
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = MaterialTheme.dimens.homeScreenDimens.tasksLazyColumnPaddingValues,
                        end = MaterialTheme.dimens.homeScreenDimens.tasksLazyColumnPaddingValues
                    )
                ) {
                    items(
                        items = state.tasks,
                        key = {
                            it.id
                        }
                    ) { item ->
                        Log.e("ks","id delted " +item.id.toString())
                        SwipeToDeleteContainer(
                            item = item,
                            onDelete = { task ->
                                onEvent(TasksEvent.DeleteTask(task))


                            },
                            animationDuration = 500,
                            modifier = Modifier.animateItemPlacement(
                                spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = 50f,
                                )
                            )
                        ) {


                            TaskCard(
                                task = item,
                                alarmService = alarmService,
                                dataStore = dataStore,
                                onCheckedChange = {onEvent(TasksEvent.Checked(it))},
                                scope = scope
                            ){
                                navController.navigate(Screens.AddEdit.route + "?taskId=${item.id}"){
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                Log.e("ks","the id is :${item.id}")

                            }

                        }

                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.homeScreenDimens.spacerBetweenListItems))


                    }
                }
            }
        }






    }
}







/*
@Preview
@Composable
fun Task2() {
    Card(
        modifier = Modifier.fillMaxWidth().background(
            Color.Black,
            RoundedCornerShape(
                bottomStart = 10.dp,
                topStart = 8.dp,
                bottomEnd = 8.dp,
                topEnd = 20.dp
            )
        ).padding(start = 8.dp),
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    LightPrimaryColor,
                    RoundedCornerShape(
                        topEnd = 8.dp,
                        bottomEnd = 8.dp
                    )
                )
                .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
        ) {

            Box(
                modifier = Modifier
                    .width(10.dp)
                    .clip(CircleShape)
                    .background(Color.Black)


            )

            RadioButton(
                selected = false,
                onClick = { }
            )

            Column {
                Text(
                    text = "Task Title",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    )
                )



                Text(
                    text = "Task description",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f),
                        fontSize = 10.sp,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = DarkPrimaryColor
                    )

                    Text(
                        text = "10:00 PM - 11:10 PM",
                        modifier = Modifier.padding(start = 4.dp),
                        style = TextStyle(
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = .8f)
                        ),
                    )
                }
            }

        }
    }
}*/


