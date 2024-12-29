package com.example.salonappcustomer.App_UI

import android.content.Context
import androidx.navigation.NavController
import com.example.salonappcustomer.User_Preferences.UserPreferences
import com.example.salonappcustomer.Navigate_Screens.Route

fun logout(navControllerLogin: NavController, context: Context) {
    val userPreferences = UserPreferences(context)
    userPreferences.clearLoginState()

    // Navigate to login page
    navControllerLogin.navigate(Route.Login) {
        popUpTo(0) { inclusive = true }
    }
}
