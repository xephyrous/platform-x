package org.xephyrous.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.xephyrous.UserRole

class ViewModel {
    var oAuthToken: String? by mutableStateOf(null)
    var firebaseInfo: FirebaseUserInfo? by mutableStateOf(null)
    var userData: UserData? by mutableStateOf(null)
    var currentUserRole: UserRole by mutableStateOf(UserRole.Anonymous)
}