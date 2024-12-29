package com.example.salonappcustomer.App_UI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
//import com.example.salonappcustomer.R


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons

import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.customermodule.R
import com.example.salonappcustomer.FirebaseDB.Appointment
import com.example.salonappcustomer.User_Preferences.UserPreferences
import com.example.salonappcustomer.viewModel.AppointmentViewModel
import java.util.Calendar
import java.util.UUID

@Composable
fun BookAppointmentScreen(navController: NavHostController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(0.dp, 100.dp)
        .imePadding()
        .verticalScroll(rememberScrollState()))
    {
        HeaderSection()
      //  ContactInfoSection()
        AppointmentForm()
        FooterSection()
    }
}

@Composable
fun HeaderSection() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)) {
        Image(
            painter = painterResource(id = R.drawable.salon4), // Replace with your image resource
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x99000000)) // Dark overlay for readability
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            , verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Book Appointment",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Our team delivers premium services. Book an appointment today.",
                fontSize = 14.sp,
                color = Color.White
                , modifier = Modifier.padding(50.dp,5.dp)
            )
        }
    }
}



@Composable
fun AppointmentForm() {
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    val viewModel: AppointmentViewModel = viewModel()

    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val userId = userPreferences.getUserId()

    // State to control the visibility of the dialog and store appointment number
    var showDialog by remember { mutableStateOf(false) }
    var appointmentNumber by remember { mutableStateOf("") }

    // Date Picker Dialog
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            date = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Time Picker Dialog
    val timePickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hourOfDay, minute ->
            time = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    // Main Form Column
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color(0xFFF2F2F2), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text("Appointment Date", fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Text(text = if (date.isNotEmpty()) date else "Select Date", fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Icon(painter = painterResource(id = R.drawable.baseline_calendar_month_24), contentDescription = "Calendar Icon")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Appointment Time", fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { timePickerDialog.show() }
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Text(text = if (time.isNotEmpty()) time else "Select Time", fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Icon(painter = painterResource(id = R.drawable.baseline_watch_later_24), contentDescription = "Clock Icon")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Message", fontWeight = FontWeight.Bold)
        BasicTextField(
            value = message,
            onValueChange = { message = it },
            textStyle = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(12.dp)
        )

        var showSlotUnavailableDialog by remember { mutableStateOf(false) }

        Button(
            onClick = {
                if (date.isEmpty() || time.isEmpty() || message.isEmpty()) {
                    Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                viewModel.isSlotAvailable(date, time) { isAvailable ->
                    if (isAvailable) {
                        val uniqueAppointmentNumber = generateUniqueAppointmentNumber().toString()
                        val appointment = Appointment(
                            aptdate = date,
                            apttime = time,
                            message = message,
                            custumerId = userId!!,
                            aptNumber = uniqueAppointmentNumber,
                            timestamp = getCurrentDateTime(),
                            status = "pending"
                        )

                        viewModel.addAppointment(
                            appointment,
                            onSuccess = {
                                appointmentNumber = uniqueAppointmentNumber
                                showDialog = true
                                date = ""
                                time = ""
                                message = ""
                            },
                            onFailure = { errorMessage ->
                                Toast.makeText(context, "Failed to book appointment: $errorMessage", Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        // Trigger the dialog box for slot unavailability
                        showSlotUnavailableDialog = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFAD845F))
        ) {
            Text("Make an Appointment", color = Color.White)
        }

// Slot Unavailable Dialog

        if (showSlotUnavailableDialog) {
            Dialog(onDismissRequest = { showSlotUnavailableDialog = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .padding(20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Icon at the top
                        Image(
                            painter = painterResource(id = R.drawable.slot), // Replace with your custom icon
                            contentDescription = "Slot Unavailable Icon",
                            //tint = Color.Red,
                            modifier = Modifier
                                .size(64.dp)



                                .padding(bottom = 16.dp)
                        )
//                        Image(
//                            painter = painterResource(id = R.drawable.slot), // Replace with your image resource
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.fillMaxSize()
//                        )

                        // Title
                        Text(
                            text = "Slot Unavailable",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFFD32F2F), // A vibrant red color
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Message
                        Text(
                            text = "This time slot is already booked. Please choose a different time.",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // OK Button
                        Button(
                            onClick = { showSlotUnavailableDialog = false },
                            colors = ButtonDefaults.buttonColors(Color(0xFFAD845F)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "OK",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }



//        if (showSlotUnavailableDialog) {
//            Dialog(onDismissRequest = { showSlotUnavailableDialog = false }) {
//                Box(
//                    modifier = Modifier
//                        .size(300.dp)
//                        .background(Color.White, RoundedCornerShape(16.dp))
//                        .padding(16.dp)
//                ) {
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = "Slot Unavailable",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 18.sp,
//                            color = Color.Red
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            text = "This slot is already booked. Please select a different time.",
//                            fontSize = 16.sp,
//                            color = Color.Black
//                        )
//                        Spacer(modifier = Modifier.height(16.dp))
//                        Button(onClick = { showSlotUnavailableDialog = false }) {
//                            Text("OK")
//                        }
//                    }
//                }
//            }
//        }


//        Button(
//            onClick = {
//                val uniqueAppointmentNumber = generateUniqueAppointmentNumber().toString()
//                val appointment = Appointment(
//                    aptdate = date,
//                    apttime = time,
//                    message = message,
//                    custumerId = userId!!,
//                    aptNumber = uniqueAppointmentNumber,
//                    timestamp = getCurrentDateTime(),
//                    status = "pending"
//                )
//
//                viewModel.addAppointment(
//                    appointment,
//                    onSuccess = {
//                        appointmentNumber = uniqueAppointmentNumber
//                        showDialog = true
//                        date = ""
//                        time = ""
//                        message = ""
//                    },
//                    onFailure = { errorMessage ->
//                        Toast.makeText(context, "Failed to book appointment: $errorMessage", Toast.LENGTH_SHORT).show()
//                    }
//                )
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 16.dp),
//            colors = ButtonDefaults.buttonColors(Color(0xFFAD845F))
//        ) {
//            Text("Make an Appointment", color = Color.White)
//        }


    }






    // Show Dialog with Blurred Background Effect
    if (showDialog) {
        // Dimmed overlay to create blurred effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        // Confirmation Dialog
        Dialog(onDismissRequest = { showDialog = false }) {
            Box(
                modifier = Modifier
                    .size(width = 300.dp, height = 200.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Thank you for booking!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFFAD845F)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your appointment number is: $appointmentNumber",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}



@Composable
fun FooterSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contact Us",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "ONE CUT, Akole Naka, Sangamner",
            color = Color.Gray
        )
        Text(
            text = "9960326232",
            color = Color.Gray
        )
        Text(
            text = "onecut@gmail.com",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "About Us",
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Our main focus is on quality and hygiene...",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}



//for Unique Appoinment Number
fun generateUniqueAppointmentNumber(): Long {
    return System.currentTimeMillis()
}