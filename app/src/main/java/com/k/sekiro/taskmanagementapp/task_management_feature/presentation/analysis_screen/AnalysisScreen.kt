package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.analysis_screen

import android.content.*
import android.util.*
import androidx.activity.compose.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.k.sekiro.taskmanagementapp.*
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.analysis_screen.component.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.component.*
import com.k.sekiro.taskmanagementapp.ui.theme.*
import kotlinx.coroutines.*
import kotlin.math.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnalysisScreen(
    navController: NavHostController = rememberNavController(),
    coroutineScope:CoroutineScope = rememberCoroutineScope(),
    drawerState:DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    context: Context = LocalContext.current,
    paddingValues: PaddingValues = PaddingValues(),
    todayTask:List<Task>,
) {
    val completedTasks = remember (todayTask){
        todayTask.filter { it.isCompleted }
    }

    val unCompletedTasks = remember (todayTask){
        todayTask.filter { !it.isCompleted }
    }

    val tabItems = listOf(stringResource(id = R.string.done), stringResource(id = R.string.time))
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect(key1 = selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }


        Column(Modifier.padding(paddingValues)) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                indicator = { tabPositions ->
                    if (selectedTabIndex < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = DarkPrimaryColor
                        )
                    }
                },
            ) {
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = item,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(MaterialTheme.dimens.analysisDimens.tapPadding)
                            )
                               },
                        selectedContentColor = DarkPrimaryColor,
                        unselectedContentColor = LightPrimaryColor
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.analysisDimens.pagerPadding)
                    .weight(1f)
            ) { index ->
                if (index == 0){
                    AnalyticsCompleting{
                        return@AnalyticsCompleting if ((completedTasks.size + unCompletedTasks.size) != 0) {
                            completedTasks.size / (completedTasks.size + unCompletedTasks.size).toFloat()
                        } else {
                            1f
                        }
                    }
                }else{
                    TimeAnalysisPage(context = context, todayTasks = todayTask)
                }
            }
        }



}




