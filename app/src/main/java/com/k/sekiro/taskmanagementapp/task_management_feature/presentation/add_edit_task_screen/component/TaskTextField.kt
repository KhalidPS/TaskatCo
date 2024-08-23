package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.k.sekiro.taskmanagementapp.ui.theme.*

@Preview
@Composable
fun TaskTextField(
    text:String = "",
    hint:String = "",
    onValueChange:(String) -> Unit = {},
    isHintVisible:Boolean = true,
    onFocusChanged:(FocusState) -> Unit = {},
    height:Dp = MaterialTheme.dimens.addEditScreenDimens.taskTextFieldHeight,
    roundedCornerSize:Dp = 16.dp,
    singleLine:Boolean = true,
    maxLines:Int = 1,
    textStyle: TextStyle = TextStyle.Default,
    icon:(@Composable () -> Unit)? = null,
    readOnly:Boolean = false,
    enabled:Boolean = true,
    contentAlignment: Alignment.Vertical = Alignment.CenterVertically,
    paddingValues: PaddingValues = PaddingValues(horizontal = 16.dp)
) {

    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier.
        onFocusChanged {
            onFocusChanged(it)

        },
        decorationBox = {

            Row (
                verticalAlignment = contentAlignment,
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .height(height)
                    .clip(RoundedCornerShape(roundedCornerSize))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(paddingValues)
            ){
                if (icon != null){
                    icon()
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Box(
                    contentAlignment = Alignment.TopStart
                ){
                    it()
                    if (isHintVisible){
                        Text(
                            text = hint,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            color = Color.Black.copy(alpha = .5f)
                        )
                    }

                }
            }
        },
        singleLine = singleLine,
        maxLines = maxLines,
        textStyle = textStyle,
        readOnly = readOnly,
        enabled = enabled
    )

}