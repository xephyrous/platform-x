package org.xephyrous.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.xephyrous.Screens
import org.xephyrous.UserRole

class ViewModel {
    var currentScreen by mutableStateOf(Screens.Homepage)
    var visible by mutableStateOf(true)

    var events: List<EventData> by mutableStateOf(emptyList())
    var courses: List<Pair<String, CourseData>> by mutableStateOf(emptyList())

    var oAuthToken: String? by mutableStateOf(null)
    var firebaseUserInfo: FirebaseUserInfo? by mutableStateOf(null)
    var googleUserInfo: GoogleUserInfo? by mutableStateOf(null)
    var userData: UserData? by mutableStateOf(UserData(UserRole.Anonymous, ArrayList()))
}