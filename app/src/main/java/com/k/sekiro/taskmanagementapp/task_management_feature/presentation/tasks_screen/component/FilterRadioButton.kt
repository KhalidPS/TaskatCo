package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.k.sekiro.taskmanagementapp.ui.theme.*

@Composable
fun FilterRadioButton(
    txt:String,
    selected:Boolean,
    onSelect:() -> Unit,
    modifier: Modifier = Modifier
){

    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){

        CustomRadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.onBackground
            ),
            radius = MaterialTheme.dimens.homeScreenDimens.radioButtonSize

        )


        Text(text = txt, style = MaterialTheme.typography.bodyMedium)

    }

}

