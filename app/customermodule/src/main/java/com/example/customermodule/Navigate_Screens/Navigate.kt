package com.example.salonappcustomer.Navigate_Screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.salonappcustomer.App_UI.Login_Page
import com.example.salonappcustomer.App_UI.MainScreen
import com.example.salonappcustomer.App_UI.Registrion
import com.example.salonappcustomer.App_UI.Splash
import com.example.salonappcustomer.viewModel.UserViewModel

@Composable
fun Navigate(innerPadding: PaddingValues, isLoggedIn: Boolean) {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()
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
               Login_Page(navController,userViewModel)
        }

        composable(Route.Registration) {
             Registrion(navController)
        }
        composable(Route.MainScreen){
            MainScreen(paddingValues =innerPadding , navControllerLogin = navController)
        }


    })
}