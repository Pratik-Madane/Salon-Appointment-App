package com.example.salonappadmin.Navigate_Screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.salonappadmin.Admin_App_UI.Login_Page
import com.example.salonappadmin.Admin_App_UI.MainScreen
import com.example.salonappadmin.Admin_App_UI.Splash


@Composable
fun Navigate(innerPadding: PaddingValues, isLoggedIn: Boolean) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.Splash, builder = {

//        composable<Splash> {
//            Splash(navController)
//        }
//        composable<Login> {
//         //   Login_Page(navController)
//        }
//
//        composable<Registration> {
//           // Registrion(navController)
//        }

        composable(Route.Splash) {
            Splash(navController,isLoggedIn)
        }
        composable(Route.Login) {
               Login_Page(navController)
        }

        composable(Route.MainScreen) {
            MainScreen(innerPadding, navController)
        }
//        composable(Route.Registration) {
//             Registrion(navController)
//        }


    })
}

