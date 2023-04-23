package com.lyft.android.interviewapp.ui.navigation

object Screens {
    const val searchScreen = "search"
    const val loginScreen = "login"
    const val eventDetailsScreen = "eventDetails"
}

object NavArguments {
    const val eventId = "eventId"
}

object Routes {
    const val login = Screens.loginScreen
    const val search = Screens.searchScreen
    const val eventDetails = "${Screens.eventDetailsScreen}/${NavArguments.eventId}"
}

object Navigation {
    val eventDetailsDestination = { eventId: String ->
        Routes.eventDetails.replace(NavArguments.eventId, eventId)
    }
}