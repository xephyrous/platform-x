package org.xephyrous.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.xephyrous.Screens
import org.xephyrous.UserRole

/**
 * Holds all global mutable application states and values
 */
class ViewModel {
    /** The current [Screens] served by the application */
    var currentScreen by mutableStateOf(Screens.Homepage)

    /** Whether the sidebar is visible */
    var visible by mutableStateOf(true)

    /** The current user's events as [EventData] */
    var events: List<EventData> by mutableStateOf(emptyList())

    /** All available events as [EventData] */
    var allEvents: List<Pair<String, EventData>> by mutableStateOf(emptyList())

    /** All available courses as [CourseData] */
    var courses: List<Pair<String, CourseData>> by mutableStateOf(emptyList())

    /** The OAuth token of the current session */
    var oAuthToken: String? by mutableStateOf(null)

    /** The current user's firebase data */
    var firebaseUserInfo: FirebaseUserInfo? by mutableStateOf(null)

    /** The current user's google account information */
    var googleUserInfo: GoogleUserInfo? by mutableStateOf(null)

    /** A storage container for the current user's information */
    var userData: UserData? by mutableStateOf(UserData(UserRole.Anonymous, ArrayList()))
}