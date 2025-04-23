package org.xephyrous.views

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.clickableOutlineImage
import org.xephyrous.components.defaultScreen
import platformx.composeapp.generated.resources.Res
import platformx.composeapp.generated.resources.Admin
import platformx.composeapp.generated.resources.modify_courses
import platformx.composeapp.generated.resources.modify_data
import platformx.composeapp.generated.resources.modify_events
import platformx.composeapp.generated.resources.modify_users

@Composable
fun Admin(navController: NavController, modifier: Modifier = Modifier) {
    defaultScreen(
        navController = navController,
        title = "Admin",
        painter = painterResource(Res.drawable.Admin)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 60.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                clickableOutlineImage(
                    title = "MODIFY USERS",
                    size = DpSize(175.dp, 100.dp),
                    painter = painterResource(Res.drawable.modify_users),
                    contentDescription = "Modify users button",
                ) {
                    navController.navigate("ModifyUsers")
                }

                clickableOutlineImage(
                    title = "MODIFY EVENTS",
                    size = DpSize(175.dp, 100.dp),
                    painter = painterResource(Res.drawable.modify_events),
                    contentDescription = "Modify events button",
                ) {
                    navController.navigate("ModifyEvents")
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    clickableOutlineImage(
                        title = "MODIFY DATA",
                        size = DpSize(175.dp, 100.dp),
                        painter = painterResource(Res.drawable.modify_data),
                        contentDescription = "Modify data button",
                    ) {
                        navController.navigate("ModifyData")
                    }

                    clickableOutlineImage(
                        title = "MODIFY COURSES",
                        size = DpSize(175.dp, 100.dp),
                        painter = painterResource(Res.drawable.modify_courses),
                        contentDescription = "Modify courses button",
                    ) {
                        navController.navigate("ModifyCourses")
                    }


                }

            }
        }
    }
}