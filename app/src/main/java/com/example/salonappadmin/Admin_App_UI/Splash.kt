package com.example.salonappadmin.Admin_App_UI


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.salonappadmin.Navigate_Screens.Route
import com.example.salonappadmin.R

import kotlinx.coroutines.delay


@Composable
fun Splash(navController: NavHostController, isLoggedIn: Boolean) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val temp= remember {
            mutableStateOf("")
        }
        val image_size by remember {
            mutableStateOf(150)
        }

        Image(
            painter = painterResource(id = R.drawable.logo2),
            contentDescription = "App_Logo",
            modifier = Modifier.size(image_size.dp)
        )
    }
    LaunchedEffect(true) {
        delay(2000)
        if (isLoggedIn)
        {
            navController.navigate(Route.MainScreen)
        } else {
            navController.navigate(Route.Login) // Navigate based on login state
        }
    }
}

