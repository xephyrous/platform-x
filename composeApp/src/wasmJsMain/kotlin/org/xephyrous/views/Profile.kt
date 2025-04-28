package org.xephyrous.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import org.xephyrous.components.defaultScreen
import org.xephyrous.components.outlineImageTitleless
import org.xephyrous.components.outlineSecureText
//import org.xephyrous.components.outlineSecureText
import org.xephyrous.components.outlineText
import org.xephyrous.views.ProfileData.email
import org.xephyrous.views.ProfileData.password
import org.xephyrous.views.ProfileData.username
import platformx.composeapp.generated.resources.Res
import platformx.composeapp.generated.resources.Profile
import platformx.composeapp.generated.resources.entire_network

object ProfileData {
    var username by mutableStateOf("BigMrZucc")
    var password by mutableStateOf("ilikefrogs69")
    var email by mutableStateOf("mark.zuccywuccy@meta.com")
}

@Composable
fun Profile(viewController: ViewController, modifier: Modifier = Modifier) {
    defaultScreen(viewController, title = "Profile", painter = painterResource(Res.drawable.Profile)) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ) {
                outlineImageTitleless(size = DpSize(300.dp, 300.dp), painter = painterResource(Res.drawable.entire_network))
                Spacer(Modifier.width(60.dp))
                Column {
                    outlineText(title = "USERNAME", size = DpSize(450.dp, 60.dp), text = username)
                    Spacer(Modifier.height(60.dp))
                    outlineSecureText(title = "PASSWORD", size = DpSize(450.dp, 60.dp), text = password)
                    Spacer(Modifier.height(60.dp))
                    outlineText(title = "EMAIL", size = DpSize(450.dp, 60.dp), text = email)
                }
            }
        }
    }
}
