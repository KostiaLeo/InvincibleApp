package com.lyft.android.interviewapp.ui.navigation

import com.lyft.android.interviewapp.ui.screens.onboarding.DisplayMode

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
    const val confirmUser = "confirmUser"
    const val userName = "userName"
    const val displayMode = "displayMode"
}

object Routes {
    const val login = Screens.loginScreen
    const val onBoarding =
        "${Screens.onBoardingScreen}/{${NavArguments.userName}}/{${NavArguments.displayMode}}"
    const val search = Screens.searchScreen
    const val home = Screens.homeScreen
    const val myMissions = Screens.myMissionsScreen
    const val achievements = Screens.achievementsScreen
    const val profile = Screens.profileScreen
    const val eventDetails =
        "${Screens.eventDetailsScreen}/{${NavArguments.eventId}}/{${NavArguments.confirmUser}}"
}

object Navigation {
    val eventDetailsDestination = { eventId: String, confirmUser: Boolean ->
        Routes.eventDetails.replace("{${NavArguments.eventId}}", eventId)
            .replace("{${NavArguments.confirmUser}}", confirmUser.toString())
    }
    val onBoardingDestination = { userName: String, displayMode: DisplayMode ->
        Routes.onBoarding.replace("{${NavArguments.userName}}", userName)
            .replace("{${NavArguments.displayMode}}", displayMode.name)
    }
}