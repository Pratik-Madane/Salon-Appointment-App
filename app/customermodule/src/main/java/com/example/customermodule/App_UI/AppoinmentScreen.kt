package com.example.salonappcustomer.App_UI

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.customermodule.R
import com.example.salonappcustomer.FirebaseDB.Appointment
import com.example.salonappcustomer.FirebaseDB.UsersInfo
//import com.example.salonappcustomer.R

import com.example.salonappcustomer.viewModel.AppointmentViewModel
import com.example.salonappcustomer.viewModel.UserViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
@Composable
fun singleAppoinmentScreen(appoinmentId: String, navController: NavHostController) {
    // Retrieve the view models
    val appointmentViewModel: AppointmentViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()

    // Collect the appointment list from AppointmentViewModel
    val appointments by appointmentViewModel.appointmentsList.collectAsState()

    // Find the appointment based on the provided ID
    val singleAppointment = appointments.find { it.id == appoinmentId }

    // Check if singleAppointment exists, if yes, request user details
    singleAppointment?.let {
        userViewModel.getUserProfile(it.custumerId)
    }

    // Observe the user data from UserViewModel
    val user by userViewModel.userLiveData.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 105.dp, 0.dp, 0.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        HeaderSection2()

        // Display appointment details only if both appointment and user data are available
        if (singleAppointment != null && user != null) {
            singleAppointmentCard(singleAppointment, user!!, navController, appointmentViewModel)
        } else {
            Text("Loading...", modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
fun singleAppointmentCard(
    singleAppoinment: Appointment,
    user: UsersInfo,
    navController: NavHostController,
    appointmentViewModel: AppointmentViewModel
) {
    var appointmentStatus by remember { mutableStateOf(singleAppoinment.status) }
    var remark by remember { mutableStateOf(singleAppoinment.remark) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 5.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "View Appointment",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Display appointment and user details
            AppointmentDetailRow(label = "Appointment Number", value = singleAppoinment.aptNumber)
            AppointmentDetailRow(label = "Name", value = "${user.firstName} ${user.lastName}")
            AppointmentDetailRow(label = "Email", value = user.email)
            AppointmentDetailRow(label = "Mobile Number", value = user.contact)
            AppointmentDetailRow(label = "Appointment Date", value = singleAppoinment.aptdate)
            AppointmentDetailRow(label = "Appointment Time", value = singleAppoinment.apttime)
            AppointmentDetailRow(label = "Apply Date", value = singleAppoinment.timestamp)
            AppointmentDetailRow(label = "Remarks", value =remark )
            //AppointmentDetailRow(label = "Status", value = appointmentStatus)

            Spacer(modifier = Modifier.height(16.dp))


            // Status Column
            Column(
                modifier = Modifier
                   // .weight(0.8f)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Status", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)

                when (singleAppoinment.status) {
                    "pending" -> {
                        Image(
                            painter = painterResource(id = R.drawable.pending1),
                            contentDescription = "Pending",
                            modifier = Modifier.size(140.dp)
                        )
                    }

                    "Selected" -> {
                        Image(
                            painter = painterResource(id = R.drawable.approved),
                            contentDescription = "Approved",
                            modifier = Modifier.size(140.dp)
                        )
                    }

                    "Rejected" -> {
                        Image(
                            painter = painterResource(id = R.drawable.rejected),
                            contentDescription = "Rejected",
                            modifier = Modifier.size(140.dp)
                        )
                    }

                    else -> {
                        Text(
                            text = "Unknown Status",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 18.sp
                        )
                    }
                }

                Text(text = singleAppoinment.remarkDate)
            }


            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


@Composable
fun HeaderSection2() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(130.dp)) {
        Image(
            painter = painterResource(id = R.drawable.header), // Replace with your image resource
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
                text = "ONE CUT'S HAIR AND BEAUTY STUDIO,\nSangamner",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

@Composable
fun AppointmentDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
    }
}

