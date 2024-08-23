package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.utils.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.k.sekiro.taskmanagementapp.*

@Composable
fun appNameStyle(): AnnotatedString {
    return buildAnnotatedString {

        withStyle(
            SpanStyle(
                fontFamily = FontFamily.Cursive,
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
        ){
            append("T")
        }
        withStyle(
            SpanStyle(
                fontFamily = FontFamily.Cursive,
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                fontWeight = FontWeight.Bold
            )
        ){
            append("askat")

        }

        withStyle(
            SpanStyle(
                fontFamily = FontFamily.Cursive,
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
        ){
            append("C")
        }

        withStyle(
            SpanStyle(
                fontFamily = FontFamily.Monospace
            )
        ){
            append("o")
        }
    }
}