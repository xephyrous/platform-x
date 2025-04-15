package org.xephyrous.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.ktor.http.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.Screens
import org.xephyrous.apis.Firebase
import org.xephyrous.components.outlineInput
import org.xephyrous.components.outlineSecureInput
import org.xephyrous.data.Callback
import org.xephyrous.data.CallbackStore
import org.xephyrous.data.EnumLambda
import platformx.composeapp.generated.resources.Res
import platformx.composeapp.generated.resources.entire_network
import platformx.composeapp.generated.resources.logo

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun Login(navController: NavController, modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Row {
        // Large Logo
        BoxWithConstraints (
            modifier = modifier.fillMaxWidth(0.5f)
                .fillMaxHeight()
                .border(BorderStroke(5.dp, Color.White)),
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                painter = painterResource(Res.drawable.entire_network),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.75f).padding(50.dp)
            )
        }

        // Logo & Form
        BoxWithConstraints (
            modifier = modifier.fillMaxWidth(0.5f),
            contentAlignment = Alignment.CenterStart
        ) {
            Column (
                modifier = Modifier.fillMaxSize().padding(start = 50.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = "PlatformX Logo",
                )

                Spacer(Modifier.height(20.dp))

                outlineInput(
                    "EMAIL",
                    DpSize(500.dp, 50.dp),
                    CallbackStore(
                        EnumLambda(Callback.VALUE_CHANGE) {
                            email = it as String
                        }
                    )
                )

                Spacer(Modifier.height(40.dp))

                outlineSecureInput(
                    "PASSWORD",
                    DpSize(500.dp, 50.dp),
                    CallbackStore(
                        EnumLambda(Callback.VALUE_CHANGE) {
                            password = it as String
                        }
                    )
                )

                Spacer(Modifier.height(40.dp))

                Button(
                    onClick = {
                        GlobalScope.launch {
                            val res = Firebase.Auth.signInWithPassword(
                                email,
                                password
                            )

                            if (res.status.isSuccess()) {
                                navController.navigate(Screens.Homepage.name)
                            } else {
                                // Display error
                            }
                        }
                    },
                    content = {
                        Text("Login")
                    }
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Sign Up",
                    modifier = Modifier.clickable {
                        // Show sign up form
                    },
                )
            }
        }
    }
}
