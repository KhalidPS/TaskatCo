package com.k.sekiro.taskmanagementapp.task_management_feature.presentation.instructions_before_use_screen

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import coil.*
import coil.compose.*
import coil.decode.*
import coil.request.*
import coil.size.*
import com.k.sekiro.taskmanagementapp.R
import com.k.sekiro.taskmanagementapp.ui.theme.*
import java.util.*

@Composable
fun InstructionsBeforeUseScreen() {

    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Column (
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){


        Text(
            text = stringResource(id = R.string.instructions_before_use)+" :",
            fontSize = MaterialTheme.typography.labelLarge.fontSize,
            fontWeight = FontWeight.Bold
        )


        Text(
            text = localeNumber(1) + stringResource(id = R.string.instruction_1) ,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            modifier = Modifier.padding(bottom = 8.dp),
            lineHeight = MaterialTheme.typography.titleLarge.lineHeight
        )

        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context).data(R.drawable.delete_gif).apply {
                    size(Size.ORIGINAL)
                }.build(),
                imageLoader = imageLoader,
            ),
            contentDescription = "swipe to delete gif",
            modifier = Modifier.fillMaxWidth().height(MaterialTheme.dimens.instructionScreenSwipeDeleteGifDimen),
        )


        Text(
            text = localeNumber(2) + stringResource(id = R.string.instruction_2),
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            modifier = Modifier.padding(bottom = 8.dp),
            lineHeight = MaterialTheme.typography.titleLarge.lineHeight
        )

        Text(
            text = localeNumber(3) + stringResource(id = R.string.instruction_3),
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            modifier = Modifier.padding(bottom = 8.dp),
            lineHeight = MaterialTheme.typography.titleLarge.lineHeight
        )



    }
}



private fun localeNumber(num:Int):String = String.format(
    Locale.getDefault(),"%d",
   num
) + ". "

@Preview
@Composable
fun InstructionsBeforeUseScreenPrev() {
        InstructionsBeforeUseScreen()

}