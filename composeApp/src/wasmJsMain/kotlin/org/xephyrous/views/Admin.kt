package org.xephyrous.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.*
import org.xephyrous.data.ViewModel
import platformx.composeapp.generated.resources.*

@Composable
fun Admin(coroutineScope: CoroutineScope, viewModel: ViewModel, alertHandler: AlertBox, modifier: Modifier = Modifier) {
    defaultScreen(
        coroutineScope,
        viewModel,
        title = "Admin",
        painter = painterResource(Res.drawable.Admin),
        alertHandler = alertHandler,
    ) {
        var boxWidth by remember { mutableStateOf(0.dp) }
        var boxHeight by remember { mutableStateOf(0.dp) }

        val localDensity = LocalDensity.current

        var modifyUsers by remember { mutableStateOf(false) }
        var modifyEvents by remember { mutableStateOf(false) }
        var modifyData by remember { mutableStateOf(false) }
        var modifyCourses by remember { mutableStateOf(false) }

        Box(
            modifier = modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    boxWidth = with(localDensity) { it.size.width.toDp() }
                    boxHeight = with(localDensity) { it.size.height.toDp() }
                }
        ) {
            clickableOutlineImage(
                title = "MODIFY USERS",
                size = DpSize(boxWidth/9, boxWidth/9),
                xOffset = boxWidth*2/7-boxWidth/18,
                yOffset = boxHeight/2-boxWidth/18,
                painter = painterResource(Res.drawable.modify_users),
                alignment = OutlineBoxTitleAlignment.CENTER,
                contentDescription = "Modify users button",
            ) {
                modifyUsers = true
            }

            clickableOutlineImage(
                title = "MODIFY EVENTS",
                size = DpSize(boxWidth/9, boxWidth/9),
                xOffset = boxWidth*3/7-boxWidth/18,
                yOffset = boxHeight/2-boxWidth/18,
                painter = painterResource(Res.drawable.modify_events),
                alignment = OutlineBoxTitleAlignment.CENTER,
                contentDescription = "Modify events button",
            ) {
                modifyEvents = true
            }

            clickableOutlineImage(
                title = "MODIFY DATA",
                size = DpSize(boxWidth/9, boxWidth/9),
                xOffset = boxWidth*4/7-boxWidth/18,
                yOffset = boxHeight/2-boxWidth/18,
                painter = painterResource(Res.drawable.modify_data),
                alignment = OutlineBoxTitleAlignment.CENTER,
                contentDescription = "Modify data button",
            ) {
                 modifyData = true
            }

            clickableOutlineImage(
                title = "MODIFY COURSES",
                size = DpSize(boxWidth/9, boxWidth/9),
                xOffset = boxWidth*5/7-boxWidth/18,
                yOffset = boxHeight/2-boxWidth/18,
                painter = painterResource(Res.drawable.modify_courses),
                alignment = OutlineBoxTitleAlignment.CENTER,
                contentDescription = "Modify courses button",
            ) {
                modifyCourses = true
            }

            viewPanel ("Modify Users", DpSize(boxWidth/9, boxWidth/9), DpSize(boxWidth-50.dp, boxHeight-50.dp),
                startingXOffset = boxWidth*2/7-boxWidth/18, startingYOffset = boxHeight/2-boxWidth/18, openXOffset = 25.dp, openYOffset = 25.dp,
                modifyUsers, closeHandler = { modifyUsers = false }
            ) {
                Text("Hello World")
            }

            viewPanel ("Modify Events", DpSize(boxWidth/9, boxWidth/9), DpSize(boxWidth-50.dp, boxHeight-50.dp),
                startingXOffset = boxWidth*3/7-boxWidth/18, startingYOffset = boxHeight/2-boxWidth/18, openXOffset = 25.dp, openYOffset = 25.dp,
                modifyEvents, closeHandler = { modifyEvents = false }
            ) {
                Text("Hello World")
            }

            viewPanel ("Modify Data", DpSize(boxWidth/9, boxWidth/9), DpSize(boxWidth-50.dp, boxHeight-50.dp),
                startingXOffset = boxWidth*4/7-boxWidth/18, startingYOffset = boxHeight/2-boxWidth/18, openXOffset = 25.dp, openYOffset = 25.dp,
                modifyData, closeHandler = { modifyData = false }
            ) {
                Text("Hello World")
            }

            viewPanel ("Modify Courses", DpSize(boxWidth/9, boxWidth/9), DpSize(boxWidth-50.dp, boxHeight-50.dp),
                startingXOffset = boxWidth*5/7-boxWidth/18, startingYOffset = boxHeight/2-boxWidth/18, openXOffset = 25.dp, openYOffset = 25.dp,
                modifyCourses, closeHandler = { modifyCourses = false }
            ) {
                Text("Hello World")
            }
        }
    }
}