package com.example.salonappadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.salonappadmin.User_Preferences.UserPreferences
import com.example.salonappadmin.Navigate_Screens.Navigate
import com.example.salonappadmin.ui.theme.SalonAppAdminTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Initialize UserPreferences
        val userPreferences = UserPreferences(applicationContext)
        setContent {
            SalonAppAdminTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // Check if the user is logged in
                    val isLoggedIn = userPreferences.isLoggedIn()

                   Navigate(innerPadding,isLoggedIn)
                }
            }
        }
    }
}
