package org.xephyrous.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ViewModel {
    var oAuthToken: String? by mutableStateOf(null)
    var firebaseUserInfo: FirebaseUserInfo? by mutableStateOf(null)
    var googleUserInfo: GoogleUserInfo? by mutableStateOf(null)
    var userData: UserData? by mutableStateOf(null)
}