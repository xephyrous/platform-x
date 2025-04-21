package org.xephyrous.views

import org.xephyrous.data.TestDocument
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.xephyrous.apis.Firebase
import org.xephyrous.data.ViewModel

@Composable
fun Homepage(navController: NavController, viewModel: ViewModel, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()

    Column {
        Text("Homepage")
        Button(
            onClick = {
                coroutineScope.launch {
                    val testDoc = Firebase.Firestore.getDocument<TestDocument>(
                        "users/test",
                        viewModel.auth!!.idToken
                    )

                    println(testDoc)
                }
            }
        ) {
            Text("Test getDocument()")
        }
    }
}