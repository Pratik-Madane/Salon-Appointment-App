package com.example.salonappadmin.Admin_App_UI

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salonappadmin.FirebaseDB.UsersInfo
import com.example.salonappadmin.Navigate_Screens.Route
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



@Composable
fun EditUserScreen(user: UsersInfo, onSave: (UsersInfo) -> Unit) {
    var firstName by remember { mutableStateOf(user.firstName) }
    var lastName by remember { mutableStateOf(user.lastName) }
    var contact by remember { mutableStateOf(user.contact) }
    var email by remember { mutableStateOf(user.email) }
    var password by remember { mutableStateOf(user.password)}
    Column(modifier = Modifier
        .fillMaxSize()
       // .padding(16.dp, 100.dp)
        .imePadding() // Add this line
        .verticalScroll(rememberScrollState()) /* Add this line*/
        ,horizontalAlignment = Alignment.CenterHorizontally
        , verticalArrangement = Arrangement.Center) {
        Text("Update Customer", fontSize = 30.sp, fontWeight = FontWeight.Bold)

        TextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") })
        TextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") })
        TextField(value = contact, onValueChange = { contact = it }, label = { Text("Contact") })
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        TextField(value = password, onValueChange ={password=it},label = { Text("Password") } )
Spacer(modifier = Modifier.height(5.dp))
var context= LocalContext.current
        GradientButton(
            text = "Save",
            gradient = Brush.horizontalGradient(
                colors = listOf(Color(0xFFC97803), Color.White)
            ),
            onClick = { val updatedUser = user.copy(firstName = firstName, lastName = lastName, contact = contact, email = email, password = password, timestamp = getCurrentDateTime())
                onSave(updatedUser)
                Toast.makeText(context,"Update Success",Toast.LENGTH_SHORT).show()

            }
        )

    }
}


// get currentDate & currentTime Function
fun getCurrentDateTime(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault()) // Date and 12-hour format with AM/PM
    return dateFormat.format(calendar.time)
}


