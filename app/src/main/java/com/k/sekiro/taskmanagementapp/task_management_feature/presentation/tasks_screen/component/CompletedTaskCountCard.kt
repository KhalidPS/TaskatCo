package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.Task
import com.k.sekiro.taskmanagementapp.ui.theme.AppThemeSettings
import com.k.sekiro.taskmanagementapp.ui.theme.CoolRed
import com.k.sekiro.taskmanagementapp.ui.theme.CoolRed2
import com.k.sekiro.taskmanagementapp.ui.theme.dimens
import java.util.Locale

@Preview
@Composable
fun CompletedTaskCountCard(
    numberOfTodayTasks:Int = 0,
    numberOfCompletedTasks:Int = 0,
) {


    val number = (String.format(stringResource(id = R.string.numberRes),numberOfCompletedTasks)
    + stringResource(id = R.string.slash) + String.format(stringResource(id = R.string.numberRes),numberOfTodayTasks) )

    val isDark = AppThemeSettings.isDarkTheme

    Card(
        shape = RoundedCornerShape(MaterialTheme.dimens.homeScreenDimens.completedTaskCardRoundedCorner),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) CoolRed2 else CoolRed
        ),
        elevation = CardDefaults.cardElevation(
            MaterialTheme.dimens.homeScreenDimens.completedTaskCardElevation
        ),
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
                .height(MaterialTheme.dimens.homeScreenDimens.completedTaskCardHeight)
                .padding(MaterialTheme.dimens.homeScreenDimens.completedTaskCardPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.completed_for_today),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.homeScreenDimens.spacerBetweenCompletedTaskTitleAndTheCount))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.List,
                    contentDescription = null,
                    modifier = Modifier.size(MaterialTheme.dimens.homeScreenDimens.menuListIconSize)
                )

                Spacer(modifier = Modifier.width(MaterialTheme.dimens.homeScreenDimens.spacerBetweenListIconAndTaskCount))

                Text(
                    text = "$number ${stringResource(id = R.string.tasks)}",
                    style = MaterialTheme.typography.bodyLarge
                )

            }

        }
    }
}