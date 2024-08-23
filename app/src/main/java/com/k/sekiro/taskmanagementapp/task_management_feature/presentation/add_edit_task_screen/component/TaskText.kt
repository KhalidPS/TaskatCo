package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.add_edit_task_screen.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
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
fun TaskText(
    text:String = "",
    helperMessage:String = "",
    onValueChange:(String) -> Unit = {},
    height:Dp = MaterialTheme.dimens.addEditScreenDimens.taskTextFieldHeight,
    roundedCornerSize:Dp = 16.dp,
    singleLine:Boolean = true,
    maxLines:Int = 1,
    textStyle: TextStyle = TextStyle.Default,
    icon:(@Composable () -> Unit)? = null,
    readOnly:Boolean = true,
    contentAlignment: Alignment.Vertical = Alignment.CenterVertically,
    paddingValues: PaddingValues = PaddingValues(horizontal = 16.dp),
    clickable:Boolean = true,
    onClick:() -> Unit = {},
    enabled:Boolean = true
) {

    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        decorationBox = {
            Row (
                verticalAlignment = contentAlignment,
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .height(height)
                    .clip(RoundedCornerShape(roundedCornerSize))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(
                        enabled = clickable,
                        onClick = onClick
                    )
                    .padding(paddingValues)
            ){
                if (icon != null){
                    icon()
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier.weight(1f)
                ){
                    it()
                }

                if (helperMessage.isNotBlank())
                    Text(
                        text = helperMessage,
                        color = Color.Black.copy(
                            alpha = .4f
                        ),
                    )


            }
        },
        singleLine = singleLine,
        maxLines = maxLines,
        textStyle = textStyle,
        readOnly = readOnly,
        enabled = enabled,
    )

}