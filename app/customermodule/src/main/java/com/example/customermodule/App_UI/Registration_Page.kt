package com.example.salonappcustomer.App_UI

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.salonappcustomer.FirebaseDB.UsersInfo
import com.example.salonappcustomer.Navigate_Screens.Route
import com.example.customermodule.R
import com.example.salonappcustomer.viewModel.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale




@Composable
fun Registrion(navController: NavController) {
    val userViewModel: UserViewModel = viewModel()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.loginbg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding() // Add this line
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(22.dp))

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "",
                modifier = Modifier.size(300.dp, 150.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sign Up",
                fontSize = 25.sp,
                color = Color(0xFFC7663A),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(text = "First Name:") },
                modifier = Modifier
                    .fillMaxWidth(0.8F)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(text = "Last Name:") },
                modifier = Modifier
                    .fillMaxWidth(0.8F)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = contact,
                onValueChange = { contact = it },
                label = { Text(text = "Contact Number:") },
                modifier = Modifier
                    .fillMaxWidth(0.8F)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email:") },
                modifier = Modifier
                    .fillMaxWidth(0.8F)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password:") },
                modifier = Modifier
                    .fillMaxWidth(0.8F)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            RegistrationGradientButton(
                text = "Submit",
                gradient = Brush.horizontalGradient(colors = listOf(Color(0xFFC97803), Color.White)),
                onClick = {
                    val user = UsersInfo(firstName=firstName, lastName = lastName, contact =  contact, email = email, password =  password)
                    userViewModel.registerUser(
                        user,
                        onSuccess = {
                            Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                            navController.navigate(Route.Login)
                        },
                        onFailure = { errorMessage ->
                            Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account?", fontSize = 16.sp,
                    color = Color.Black
                )
                TextButton(onClick = { navController.navigate(Route.Login) }) {
                    Text(text = "Sign In", fontSize = 16.sp,
                        fontWeight = FontWeight.Bold, color = Color.Blue)
                }
            }
        }
    }
}






@Composable
fun RegistrationGradientButton(text: String, gradient: Brush, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(),
        modifier = Modifier.padding(20.dp,0.dp)
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .fillMaxWidth(0.8F)
                .height(52.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, color = Color.White)
        }
    }
}


// get currentDate & currentTime Function
fun getCurrentDateTime(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault()) // Date and 12-hour format with AM/PM
    return dateFormat.format(calendar.time)
}























