package org.xephyrous.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.xephyrous.UserRole

class ViewModel {
    var authToken: String? by mutableStateOf(null)
    var currentUserRole: UserRole by mutableStateOf(UserRole.Anonymous)
    var googleUserData: GoogleUserInfo? by mutableStateOf(null)
    var firebaseUserData: FirebaseUserInfo? by mutableStateOf(null)
}