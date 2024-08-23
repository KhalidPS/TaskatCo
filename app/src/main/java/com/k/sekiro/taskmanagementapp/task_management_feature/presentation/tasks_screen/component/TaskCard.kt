package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen.component

import android.content.*
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.datastore.core.*
import androidx.datastore.preferences.core.*
import com.k.sekiro.taskmanagementapp.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.converters.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*
import com.k.sekiro.taskmanagementapp.ui.theme.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.k.sekiro.taskmanagementapp.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.time.Duration.Companion.minutes

@Composable
 fun TaskCard(
    task: Task = Task.DEFAULT_TASK,
    elevation: Dp = MaterialTheme.dimens.homeScreenDimens.taskCardElevation,
    borderStroke: BorderStroke? = null,
    showPinnedIcon:Boolean = false,
    modifier: Modifier = Modifier ,
    alarmService: AlarmService,
    dataStore: DataStore<Preferences>,
    scope: CoroutineScope,
    onCheckedChange:(Task)-> Unit,
    onTaskClicked:() -> Unit = {},
) {



    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(MaterialTheme.dimens.homeScreenDimens.taskCardRounded),
        elevation = CardDefaults.cardElevation(elevation),
        border = borderStroke,
        onClick = onTaskClicked
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(MaterialTheme.dimens.homeScreenDimens.taskRowContainerPadding)
                .padding(end = MaterialTheme.dimens.homeScreenDimens.taskRowContainerPadding)
        ) {

/*            Box(
                modifier = Modifier
                    .width(10.dp)
                    .clip(CircleShape)
                    .background(Color.Black)


            )*/

            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.homeScreenDimens.spaceBetweenToggleIconAndPendIcon)
            ){

                if (showPinnedIcon){

                    val transX = remember {
                        Animatable(0f)
                    }

                    val transY = remember {
                        Animatable(-100f)
                    }
                    val density = LocalDensity.current.density



                    LaunchedEffect(key1 = Unit) {
                            launch {
                                transX.animateTo(
                                    targetValue = 5 * density,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioNoBouncy,
                                    )
                                )
                            }

                            launch {
                                transY.animateTo(
                                    targetValue = 5 * density,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = 50f
                                    )
                                )
                            }
                    }
                    Icon(
                        imageVector = Icons.Default.PushPin,
                        contentDescription = null,
                        modifier = Modifier
                            .size(MaterialTheme.dimens.homeScreenDimens.pendingIconSize)
                            .graphicsLayer {
                                translationX = transX.value
                                translationY = transY.value
                                rotationZ = if (Locale.getDefault().language == "ar") 30f else -30f
                            },
                        tint = Color.Red,
                    )
                }


/*                CustomRadioButton(
                    selected = task.isCompleted,
                    onClick = { onCheckedChange(task.copy(isCompleted = !task.isCompleted, isRestored = true)) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.inversePrimary
                    ),
                    radius = MaterialTheme.dimens.homeScreenDimens.radioButtonSize,
                )*/


                CustomIconToggleButton(
                    checked = task.isCompleted,
                    onCheckedChange = { onCheckedChange(task.copy(isCompleted = !task.isCompleted, isRestored = true)) },
                    size = MaterialTheme.dimens.homeScreenDimens.iconToggleButtonSize,
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.homeScreenDimens.iconToggleButtonPadding)
                ) {

                    AnimatedVisibility(
                        visible = task.isCompleted,
                        enter = fadeIn(
                            animationSpec = tween(
                                durationMillis = 1000
                            )
                        ),
                        exit = fadeOut(
                            animationSpec = tween(
                                durationMillis = 100
                            )
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.inversePrimary,
                            modifier = Modifier.size(MaterialTheme.dimens.homeScreenDimens.iconToggleButtonSize)
                        )
                    }

                    AnimatedVisibility(
                        visible = !task.isCompleted,
                        enter = fadeIn(
                            animationSpec = tween(
                                durationMillis = 1000
                            )
                        ),
                        exit = fadeOut(
                            animationSpec = tween(
                                durationMillis = 100
                            )
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Circle,
                            contentDescription = null,
                            tint = Black200,
                            modifier = Modifier.size(MaterialTheme.dimens.homeScreenDimens.iconToggleButtonSize)
                        )
                    }

 /*                   Icon(
                        imageVector = if(task.isCompleted) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
                        contentDescription = null,
                        tint = if (task.isCompleted) MaterialTheme.colorScheme.inversePrimary else Black200
                    )*/


                }


                if(showPinnedIcon){
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.homeScreenDimens.spacerIfShowPendingIcon))
                }
            }


            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = if (task.isCompleted) MaterialTheme.colorScheme.onBackground.copy(alpha = .5f) else MaterialTheme.colorScheme.onBackground,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.homeScreenDimens.spacerBetweenTaskTitleAndDescription))

                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = task.description,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.homeScreenDimens.spacerBetweenColumnAndTimer))



                    if (!task.isCompleted && task.isEnteredTime) {

                        var idFromService by remember {
                            mutableIntStateOf(0)
                        }

                        LaunchedEffect(key1 = Unit) {
                            //scope.launch {
                                dataStore.data.collectLatest { preferences ->
                                    idFromService = preferences[intPreferencesKey("taskId")]?:0
                                }
                            //}

                        }

                        val zero = String.format(stringResource(id = R.string.numberRes),0)

                        Text(
                            text = if (task.id == idFromService)  alarmService.timeValue.value else "$zero$zero:$zero$zero:$zero$zero",
                            modifier = Modifier.weight(0.3f),
                            color = if (AppThemeSettings.isDarkTheme)
                                Color.Red else Color.Red.copy(alpha = .5f),
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                        )

                    } else {
                        Spacer(
                            modifier = Modifier
                                .width(MaterialTheme.dimens.homeScreenDimens.spacerIfNoTimerWidth)
                                .weight(MaterialTheme.dimens.homeScreenDimens.spacerIfNoTimerWeight)
                        )
                    }




                    Box(
                        modifier = Modifier
                            .size(MaterialTheme.dimens.homeScreenDimens.taskCardPrioritySize)
                            .clip(MaterialTheme.dimens.homeScreenDimens.taskCardPriorityRounded)
                            .background(
                                if (task.priority == Priority.NORMAL.value) {
                                    NormalPriorityColor
                                } else if (task.priority == Priority.HIGH.value) {
                                    HighPriorityColor
                                } else {
                                    LowPriorityColor
                                }
                            )
                    )

                }

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.homeScreenDimens.spacerBetweenTaskTitleAndDescription))



                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(// Canvas here instead of icon
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier.size(MaterialTheme.dimens.homeScreenDimens.taskCardDateIconSize),
                            tint = if (AppThemeSettings.isDarkTheme)
                                LightPrimaryColor else DarkPrimaryColor
                        )

                        Text(
                            text = task.getFormattedDate(),
                            modifier = Modifier.padding(start = MaterialTheme.dimens.homeScreenDimens.taskCardPaddingDateTxt),
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                color = MaterialTheme.colorScheme.background
                            ),
                        )
                    }




                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(// Canvas here instead of icon
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = null,
                            modifier = Modifier.size(MaterialTheme.dimens.homeScreenDimens.taskCardTimeIconSize),
                            tint = if (AppThemeSettings.isDarkTheme)
                                LightPrimaryColor else DarkPrimaryColor
                        )

                        Text(
                            text = task.getFormattedTime(),
                            modifier = Modifier.padding(start = MaterialTheme.dimens.homeScreenDimens.taskCardPaddingTimeTxt),
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                color = MaterialTheme.colorScheme.background
                            ),
                        )
                    }


                }
            }

/*            Spacer(modifier = Modifier.width(MaterialTheme.dimens.homeScreenDimens.spacerBetweenColumnAndTimer))



            if (!task.isCompleted && task.isEnteredTime) {
                Text(
                    text = "00:00",
                    modifier = Modifier.weight(0.3f),
                    color = if (isSystemInDarkTheme() || AppThemeSettings.isDarkTheme)
                        Color.Red else Color.Red.copy(alpha = .5f),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .width(MaterialTheme.dimens.homeScreenDimens.spacerIfNoTimerWidth)
                        .weight(MaterialTheme.dimens.homeScreenDimens.spacerIfNoTimerWeight)
                )
            }




            Box(
                modifier = Modifier
                    .size(MaterialTheme.dimens.homeScreenDimens.taskCardPrioritySize)
                    .clip(MaterialTheme.dimens.homeScreenDimens.taskCardPriorityRounded)
                    .background(NormalPriorityColor)
            )*/

        }
    }
}