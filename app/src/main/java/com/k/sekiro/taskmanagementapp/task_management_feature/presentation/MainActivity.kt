package com.k.sekiro.taskmanagementapp.task_management_feature.presentation

import android.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Configuration
import android.os.*
import android.util.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.*
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.core.app.*
import androidx.core.os.LocaleListCompat
import androidx.datastore.core.*
import androidx.datastore.preferences.core.*
import androidx.hilt.navigation.compose.*
import androidx.lifecycle.*
import androidx.media3.exoplayer.*
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.k.sekiro.taskmanagementapp.AlarmService
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.analysis_screen.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.request_permission_screen.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.component.*
import com.k.sekiro.taskmanagementapp.ui.theme.*
import dagger.hilt.android.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.Locale
import javax.inject.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //val Context.dataStore:DataStore<Preferences> by preferencesDataStore("theme")

   @Inject lateinit var dataStore: DataStore<Preferences>

    lateinit var alarmService:AlarmService
    private var isBound by mutableStateOf(false)



    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            val binder = service as AlarmService.AlarmBinder
            alarmService =  binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }
    }



    override fun onStart() {
        super.onStart()
        Intent(this,AlarmService::class.java).also {intent ->
            bindService(intent,connection,Context.BIND_AUTO_CREATE)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*        LocaleManagerCompat.getSystemLocales(this).also { list ->
            if (!list.isEmpty){
                Log.e("ks","the language : ${list[0]?.language?:"null"}")
            }
        }*/


///////  Set locale manually

        /*        val locale = Locale.Builder().setLanguage("ar").build()
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(
            config, resources.displayMetrics
        )*/


        /*val loc = Locale.getDefault().language
        Log.e("ks","the language : $loc")*/

        lifecycleScope.launch {

            dataStore.data.collectLatest { preferences ->
                val key = booleanPreferencesKey("isDarkTheme")
                Log.e("ks", "Theme: ${preferences[key]}")
                AppThemeSettings.isDarkTheme = preferences[key] ?: false
            }

        }



        setContent {
            TaskManagementAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    val navController = rememberNavController()

                    val coroutineScope = rememberCoroutineScope()

                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                    val snackbarHostState = remember {
                        SnackbarHostState()
                    }


                    val currentBackStackEntry by navController.currentBackStackEntryAsState()


                    val isBottomBarVisible by remember {
                        derivedStateOf { currentBackStackEntry?.destination?.route == Screens.Tasks.route || currentBackStackEntry?.destination?.route == Screens.Analysis.route}
                    }

                    val isTopBarVisible by remember {
                        derivedStateOf { currentBackStackEntry?.destination?.route == Screens.Tasks.route}
                    }

                    val context = LocalContext.current

                    var isSnackBarActionVisible by remember { /** this cuz I'm using only one scaffold for the whole app
                     this means only one snackbarHostState common between all screens so here I want the action to be
                     visible on TaskScreen and be unVisible on AnalysisScreen for example so I'm changing the state using
                     lambda function and call it within the screen when we need to show snackbar*/
                        mutableStateOf(false)
                    }


                    RequestPermissionScreen(context = this) {
                        ModalNavigationDrawer(
                            drawerContent = {
                                ModalDrawerSheet {
                                    AppDrawer(
                                        dataStore = dataStore,
                                        coroutineScope = coroutineScope,
                                        navController = navController,
                                        drawerState = drawerState
                                    )
                                }
                            },
                            drawerState = drawerState,
                        ) {
                            Scaffold(
                                bottomBar = {
                                    AnimatedVisibility(
                                        visible = isBottomBarVisible,
                                        enter = slideInVertically(
                                            initialOffsetY = {
                                                it
                                            }
                                        ),
                                        exit = slideOutVertically(
                                            targetOffsetY = {it},
                                            animationSpec = tween(
                                                durationMillis = 100
                                            )
                                        )
                                    ) {
                                        AppBottomBar(
                                            onNavIconClicked = {
                                                //selectedScreen = it.route
                                                navController.navigate(it.route) {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            },

                                            onAddClicked = {
                                                navController.navigate(Screens.AddEdit.route) /*{
                                                popUpTo(navController.currentBackStackEntry?.destination?.route!!) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }*/
                                            },
                                            // selectedScreen = selectedScreen
                                            navController = navController
                                        )
                                    }



                                },
                                topBar = {
                                    AnimatedVisibility(
                                        visible = isTopBarVisible,
                                        enter = slideInVertically(
                                            initialOffsetY = {-it},
                                        ),
                                        exit = slideOutVertically(
                                            targetOffsetY = {-it},
                                            animationSpec = tween(
                                                100
                                            )
                                        )
                                    ) {
                                        AppTopBar { coroutineScope.launch { drawerState.open() } }
                                    }




                                },
                                snackbarHost = {
                                    SnackbarHost(
                                        hostState = snackbarHostState,
                                    ) {

                                        Snackbar(
                                            action = {
                                                if (isSnackBarActionVisible /*getCurrentDestination(navController) == Screens.Tasks.route*/){
                                                    TextButton(
                                                        onClick = { it.performAction() }
                                                    ) {
                                                        Text(
                                                            text = stringResource(id = R.string.undo),
                                                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                                                        )
                                                    }
                                                }

                                            }
                                        ) {
                                            Text(text = it.visuals.message)
                                        }

                                    }


                                }

                            ) { paddingValues ->

                                NavHost(
                                    navController = navController,
                                    startDestination = Screens.Tasks.route,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    composable(Screens.Tasks.route) {

                                        val tasksViewModel = hiltViewModel<TasksViewModel>()

                                        if (isBound) {
                                            TasksScreen(
                                                navController = navController,
                                                drawerState = drawerState,
                                                scope = coroutineScope,
                                                snackbarHostState = snackbarHostState,
                                                paddingValues = paddingValues,
                                                state = tasksViewModel.state.value,
                                                onEvent = tasksViewModel::onEvent,
                                                uiEvent = tasksViewModel.uiEvent,
                                                context = context,
                                                alarmService = alarmService,
                                                dataStore = dataStore,
                                                onChangeSnackActionState = {isSnackBarActionVisible = true}
                                            )
                                        }





                                    }

                                    composable(Screens.Analysis.route) {
                                        val analysisViewModel = hiltViewModel<AnalysisViewModel>()

                                        AnalysisScreen(
                                            navController = navController,
                                            coroutineScope = coroutineScope,
                                            drawerState = drawerState,
                                            paddingValues = paddingValues,
                                            todayTask = analysisViewModel.todayTasks.value
                                        )
                                    }

                                    composable(
                                        route = Screens.AddEdit.route + "?taskId={taskId}",
                                        arguments = listOf(
                                            navArgument("taskId") {
                                                type = NavType.IntType
                                                defaultValue = -1
                                            }
                                        ),
                                        enterTransition = {
                                            slideInHorizontally(
                                                animationSpec = tween(
                                                    durationMillis = 500,
                                                    easing = EaseIn
                                                ),
                                                initialOffsetX = { -it }
                                            )
                                        },
                                        exitTransition = {
                                            slideOutVertically(
                                                animationSpec = tween(
                                                    durationMillis = 500,
                                                    easing = EaseInOut
                                                ),
                                                targetOffsetY = { it }
                                            )
                                        },
                                    ) {

                                        val addEditViewModel = hiltViewModel<AddEditTaskViewModel>()


                                        AddEditTaskScreen(
                                            onEvent = addEditViewModel::onEvent,
                                            taskDate = addEditViewModel.taskDate.value,
                                            isRestEnabled = addEditViewModel.restButtonEnabled.value,
                                            navController = navController,
                                            snackbarHostState = snackbarHostState,
                                            paddingValues = paddingValues,
                                            taskTitle = addEditViewModel.taskTitle.value,
                                            taskDescription = addEditViewModel.taskDescription.value,
                                            taskStartTime = addEditViewModel.taskStartTime.value,
                                            taskEndTime = addEditViewModel.taskEndTime.value,
                                            taskPriority = addEditViewModel.taskPriority.value,
                                            uiEvent = addEditViewModel.eventFlow,
                                            pickerInitialDate = addEditViewModel.pickerInitialDate.value,
                                            calenderStartTime = addEditViewModel.calenderStartTime.value,
                                            calenderEndTime = addEditViewModel.calenderEndTime.value,
                                            onChangeSnackActionState = {isSnackBarActionVisible = false}
                                        )
                                    }

                                    composable(Screens.Instructions.route){
                                        InstructionsBeforeUseScreen()
                                    }
                                }


                            }

                        }
                    }




                }


            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

    }





