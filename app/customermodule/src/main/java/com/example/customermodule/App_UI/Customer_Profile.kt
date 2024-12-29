package com.example.salonappcustomer.App_UI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customermodule.R
//import com.example.salonappcustomer.R
import com.example.salonappcustomer.User_Preferences.UserPreferences
import com.example.salonappcustomer.ui.theme.darkb1
import com.example.salonappcustomer.viewModel.UserViewModel
import java.lang.reflect.Modifier

@Composable
fun Customer_Profile(userViewModel: UserViewModel) {
    val user by userViewModel.userLiveData.observeAsState() // Observe user data
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val userId = userPreferences.getUserId()

    LaunchedEffect(userId) {
        // Load user data when the profile screen is opened
        userViewModel.getUserProfile(userId!!)
    }

    Column(
        modifier = androidx.compose.ui.Modifier
            .padding(0.dp, 100.dp, 0.dp, 0.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(darkb1, Color.White)
                )
            )
    ) {
        Row(
            androidx.compose.ui.Modifier
                .border(1.dp, color = Color.Black)
                .fillMaxWidth()
                .height(67.dp)
        ) {
            Text(
                text = "My Profile",
                modifier = androidx.compose.ui.Modifier.padding(10.dp, 20.dp),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = androidx.compose.ui.Modifier.height(5.dp))

        Column(
            modifier = androidx.compose.ui.Modifier
                .border(1.dp, color = Color.Black)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
            //, verticalArrangement = Arrangement.Center
            , horizontalAlignment = Alignment.CenterHorizontally
        ) {
            user?.let { data ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.profilepicture),
                        contentDescription = "Profile Picture",
                        modifier = androidx.compose.ui.Modifier.size(150.dp)
                    )

                    Text(
                        text = "${data.firstName} ${data.lastName}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 25.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = androidx.compose.ui.Modifier.height(17.dp))

                    ProfileDetail(label = "Mobile Number", value = data.contact)
                    ProfileDetail(label = "Email", value = data.email)
                    ProfileDetail(label = "password:", value = data.password?: "Not Available")

                }
            } ?: run {
                // Display loading or an error message if user data is null
                Text(text = "Loading profile...", color = Color.Gray)
            }
        }
    }
}

@Composable
fun ProfileDetail(label: String, value: String) {
    Column {
        Text(
            text = "$label:",
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp
        )
        Text(
            text = value,
            fontWeight = FontWeight.Thin,
            fontSize = 17.sp,
            color = Color.Gray
        )
        Spacer(modifier = androidx.compose.ui.Modifier.height(15.dp))
    }
}





