package com.example.salonappadmin.Admin_App_UI

import android.content.Context
import androidx.navigation.NavController
import com.example.salonappadmin.Navigate_Screens.Route
import com.example.salonappadmin.User_Preferences.UserPreferences

fun logout(navControllerLogin: NavController, context: Context) {
    val userPreferences = UserPreferences(context)
    userPreferences.clearLoginState()

    // Navigate to login page
    navControllerLogin.navigate(Route.Login) {
        popUpTo(0) { inclusive = true }
    }
}
