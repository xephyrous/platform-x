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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.xephyrous.data.Callback
import org.xephyrous.data.CallbackStore

/**
 * Creates a customizable text input field with a title and outlined box styling.
 * The input field allows for single-line or multi-line text entry and triggers a callback
 * when the value changes.
 *
 * This composable creates a standard text field wrapped in an outline box. The input field
 * can be customized for size, title, alignment, and more. The `CallbackStore` is used
 * to invoke a callback function when the text changes.
 *
 *
 * @param title The title to be displayed above the input field.
 * @param size The size of the outline box containing the input field.
 * @param callbackStore A `CallbackStore` used to manage callbacks when the value changes.
 * @param textSize The font size for the text inside the input field, default is 14.sp.
 * @param alignment The alignment of the title, default is [OutlineBoxTitleAlignment.LEFT].
 * @param alignmentSpacing The spacing between the title and the input field, default is 10.dp.
 * @param multiline A boolean that determines if the input field should allow multiple lines of text, default is false.
 * @param default The default value to prepopulate the input field, default is an empty string.
 */
@Composable
fun outlineInput(
    title: String, size: DpSize,
    callbackStore: CallbackStore,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    alignmentSpacing: Dp = 10.dp,
    multiline: Boolean = false,
    default: String = ""
) {
    var text by remember { mutableStateOf(default) }

    outlineBox(title, size, textSize, alignment, alignmentSpacing) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                callbackStore.softInvoke(Callback.VALUE_CHANGE, it) //Trigger callback on value change
            },
            singleLine = !multiline, //Determines whether the input field is single-line or multi-line
            textStyle = TextStyle(color = Color.White, fontWeight = FontWeight.Bold),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFF2D2D2D), //Background color for the input field
                focusedIndicatorColor = Color.White, //Color for the indicator when focused
                unfocusedIndicatorColor = Color(0xFF2D2D2D), //Color for the indicator when unfocused
                cursorColor = Color.White, //Color for the cursor
            ),
            modifier = Modifier.fillMaxWidth() //The input field should take the full width of the container

        )
    }
}

/**
 * Creates a secure text input field (such as a password field) with a title and outlined box styling.
 * The input field includes a toggle to show or hide the text, and triggers a callback
 * when the value changes.
 *
 * This composable creates a secure input field where the text is obscured by default (for sensitive input like passwords).
 * It includes an icon button to toggle visibility, allowing users to switch between showing and hiding the text.
 * The `CallbackStore` is used to invoke a callback function when the value changes.
 *
 * @param title The title to be displayed above the input field.
 * @param size The size of the outline box containing the input field.
 * @param callbackStore A `CallbackStore` used to manage callbacks when the value changes.
 * @param textSize The font size for the text inside the input field, default is 14.sp.
 * @param alignment The alignment of the title, default is [OutlineBoxTitleAlignment.LEFT].
 * @param alignmentSpacing The spacing between the title and the input field, default is 10.dp.
 * @param multiline A boolean that determines if the input field should allow multiple lines of text, default is false.
 * @param default The default value to prepopulate the input field, default is an empty string.
 */
@Composable
fun outlineSecureInput(
    title: String, size: DpSize,
    callbackStore: CallbackStore,
    textSize: TextUnit = 14.sp,
    alignment: OutlineBoxTitleAlignment = OutlineBoxTitleAlignment.LEFT,
    alignmentSpacing: Dp = 10.dp,
    multiline: Boolean = false,
    default: String = ""
) {
    val text = remember { mutableStateOf(default) }
    var show by remember { mutableStateOf(false) }

    outlineBox(title, size, textSize, alignment, alignmentSpacing) {
        TextField(
            text.value,
            onValueChange = {
                text.value = it
                callbackStore.softInvoke(Callback.VALUE_CHANGE, it) // Trigger callback on value change
            },
            singleLine = !multiline, //Determines whether the input field is single-line or multi-line
            textStyle = TextStyle(color = Color.White, fontWeight = FontWeight.Bold),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFF2D2D2D), //Background color for the input field
                focusedIndicatorColor = Color.White, //Color for the indicator when focused
                unfocusedIndicatorColor = Color(0xFF2D2D2D), //Color for the indicator when unfocused
                cursorColor = Color.White, //Color for the cursor
            ),
            visualTransformation = if (show) VisualTransformation.None else PasswordVisualTransformation(), //Toggle visual transformation for password field
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), //Set the keyboard type for passwords
            trailingIcon = {
                val image = if (show)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (show) "Hide password" else "Show password"

                IconButton(onClick = { show = !show }) { //Toggle visibility on icon click
                    Icon(
                        imageVector  = image, description,
                        tint = Color.White //Icon color
                    )
                }
            },
            modifier = Modifier.fillMaxSize(), //The input field should take the full size of the container
        )
    }
}