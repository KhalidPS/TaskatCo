package com.k.sekiro.taskmanagementapp.task_management_feature.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.k.sekiro.taskmanagementapp.ui.theme.*
import com.k.sekiro.taskmanagementapp.R

@Composable
fun InstructionsBeforeUseScreen() {
    Column (
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ){
        Text(
            text = stringResource(id = R.string.instructions_before_use)+" :",
            fontSize = MaterialTheme.typography.labelLarge.fontSize,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "1. ddasf sdsadasdsadsad asdasdasd" +
                    "sadsadsad" +
                    "sdsad" +
                    "sad",
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        Text(
            text = "2. ddasf sdsadasdsadsad asdasdasd" +
                    "sadsadsad" +
                    "sdsad" +
                    "sad",
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            modifier = Modifier.padding(bottom = 8.dp)
        )

    }
}

@Preview
@Composable
fun InstructionsBeforeUseScreenPrev() {
        InstructionsBeforeUseScreen()

}