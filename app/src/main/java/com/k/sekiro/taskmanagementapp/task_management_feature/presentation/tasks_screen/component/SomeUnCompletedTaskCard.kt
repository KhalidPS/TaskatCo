package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.Task
import com.k.sekiro.taskmanagementapp.ui.theme.AppThemeSettings
import com.k.sekiro.taskmanagementapp.ui.theme.CoolBlue
import com.k.sekiro.taskmanagementapp.ui.theme.CoolBlue2
import com.k.sekiro.taskmanagementapp.ui.theme.dimens


@Preview
@Composable
fun SomeUnCompletedTaskCard(
    task:Task = Task.DEFAULT_TASK
) {


    val cardColor = if (AppThemeSettings.isDarkTheme)
        CoolBlue2 else CoolBlue



    Card(
        shape = RoundedCornerShape(MaterialTheme.dimens.homeScreenDimens.uncompletedTaskCardRoundedCorner),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(MaterialTheme.dimens.homeScreenDimens.uncompletedTaskCardElevation),
        onClick = {},
    ) {
        Column(
            modifier = Modifier
                /*                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            CoolBlue,
                            DarkPrimaryColor,
                            LightPrimaryColor,
                        ),
                        startX = .5f,
                    )
                )*/
                .height(MaterialTheme.dimens.homeScreenDimens.uncompletedTaskCardHeight)
                .width(MaterialTheme.dimens.homeScreenDimens.uncompletedTaskCardWidth)
                .padding(MaterialTheme.dimens.homeScreenDimens.uncompletedTaskCardPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = task.title,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.homeScreenDimens.uncompletedCardSpacerBetweenTaskTitleAndTaskDes))

            Text(
                text = task.description,
                overflow = TextOverflow.Ellipsis,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                maxLines = 2,
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.homeScreenDimens.uncompletedCardSpacerBetweenDescriptionAndTime))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    modifier = Modifier.size(MaterialTheme.dimens.homeScreenDimens.uncompletedTimeIconSize)
                )

                Text(
                    text = task.getFormattedTime(),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    modifier = Modifier.padding(start = MaterialTheme.dimens.homeScreenDimens.uncompletedTaskPaddingBetweenTimeIconAndTimeTxt)
                )
            }

        }
    }

}