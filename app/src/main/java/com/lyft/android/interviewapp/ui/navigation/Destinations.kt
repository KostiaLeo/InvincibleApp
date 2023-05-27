package com.lyft.android.interviewapp.ui.navigation

object Screens {
    const val loginScreen = "login"
    const val onBoardingScreen = "onboarding"
    const val homeScreen = "home"
    const val searchScreen = "search"
    const val myMissionsScreen = "myMissions"
    const val achievementsScreen = "achievements"
    const val profileScreen = "profile"
    const val eventDetailsScreen = "eventDetails"
}

object NavArguments {
    const val eventId = "eventId"
    const val userName = "userName"
}

object Routes {
    const val login = Screens.loginScreen
    const val onBoarding = "${Screens.onBoardingScreen}/{${NavArguments.userName}}"
    const val search = Screens.searchScreen
    const val home = Screens.homeScreen
    const val myMissions = Screens.myMissionsScreen
    const val achievements = Screens.achievementsScreen
    const val profile = Screens.profileScreen
    const val eventDetails = "${Screens.eventDetailsScreen}/{${NavArguments.eventId}}"
}

object Navigation {
    val eventDetailsDestination = { eventId: String ->
        Routes.eventDetails.replace("{${NavArguments.eventId}}", eventId)
    }
    val onBoardingDestination = { userName: String ->
        Routes.onBoarding.replace("{${NavArguments.userName}}", userName)
    }
}