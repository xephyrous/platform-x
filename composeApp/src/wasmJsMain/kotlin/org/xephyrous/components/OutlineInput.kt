package org.xephyrous.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.DpSize
import org.xephyrous.data.Callback
import org.xephyrous.data.CallbackStore

@Composable
fun outlineInput(
    title: String, size: DpSize,
    callbackStore: CallbackStore,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    multiline: Boolean = false,
    default: String = ""
) {
    var text by remember { mutableStateOf(default) }

    outlineBox(title, size, alignment) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                callbackStore.softInvoke(Callback.VALUE_CHANGE, it)
            },
            singleLine = !multiline,
            textStyle = TextStyle(color = Color.White, fontWeight = FontWeight.Bold),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFF2D2D2D),
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color(0xFF2D2D2D),
                cursorColor = Color.White,
            )
        )
    }
}

// TODO : State change callback (onValueChange)
@Composable
fun outlineSecureInput(
    title: String, size: DpSize,
    callbackStore: CallbackStore,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    multiline: Boolean = false,
    default: String = ""
) {
    val text = remember { mutableStateOf(default) }
    var show by remember { mutableStateOf(false) }

    outlineBox(title, size, alignment) {
        TextField(
            text.value,
            onValueChange = {
                text.value = it
                callbackStore.softInvoke(Callback.VALUE_CHANGE, it)
            },
            singleLine = !multiline,
            visualTransformation = if (show) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (show)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (show) "Hide password" else "Show password"

                IconButton(onClick = { show = !show }){
                    Icon(imageVector  = image, description)
                }
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}