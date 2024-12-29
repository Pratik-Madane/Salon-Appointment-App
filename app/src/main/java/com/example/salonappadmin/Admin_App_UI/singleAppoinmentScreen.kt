package com.example.salonappadmin.Admin_App_UI

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.salonapp_admin.ui.theme.darkb1
import com.example.salonappadmin.FirebaseDB.Appointment
import com.example.salonappadmin.FirebaseDB.UsersInfo
import com.example.salonappadmin.Navigate_Screens.Route
import com.example.salonappadmin.R
import com.example.salonappadmin.ui.theme.PurpleGrey80
import com.example.salonappadmin.viewModel.AppointmentViewModel
import com.example.salonappadmin.viewModel.UserViewModel_Admin
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun singleAppoinmentScreen(appoinmentId: String, navController: NavHostController) {

    
    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){

    }


    val appointmentViewModel: AppointmentViewModel = viewModel()
    val userViewModel: UserViewModel_Admin = viewModel()

    val appointments by appointmentViewModel.appointmentsList.collectAsState()
    val users by userViewModel.usersList.collectAsState()

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp, 120.dp)
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//
//        Text(
//            text = "ONE CUT'S HAIR AND BEAUTY STUDIO,\nSangamner",
//            style = MaterialTheme.typography.labelLarge,
//            modifier = Modifier.padding(bottom = 16.dp),
//            textAlign = TextAlign.Center,
//            color = MaterialTheme.colorScheme.primary
//        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp,105.dp,0.dp,0.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        HeaderSection()


       // Spacer(modifier = Modifier.height(16.dp))



        //find the user Appoinment
        val singleAppoinment=appointments.find { it.id==appoinmentId }

        // Find the user details for the appointment
        val user = users.find { it.id == singleAppoinment!!.custumerId }

        if (user != null) {
            singleAppointmentCard(singleAppoinment, user, navController,appointmentViewModel)
        }



    }
    
    
}



@Composable
fun HeaderSection() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(130.dp)) {
        Image(
            painter = painterResource(id = R.drawable.salon1), // Replace with your image resource
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
fun singleAppointmentCard(
    singleAppoinment: Appointment?,
    user: UsersInfo,
    navController: NavHostController,
    appointmentViewModel: AppointmentViewModel
) {
    var appointmentStatus by remember { mutableStateOf(singleAppoinment!!.status) }
    var remark by remember { mutableStateOf(singleAppoinment!!.remark) }
    val appointmentId = singleAppoinment!!.id
    var cotext= LocalContext.current


           var brush = Brush.verticalGradient(
                colors = listOf(darkb1, Color.White)
            )


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp,5.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

        ,colors = CardDefaults.cardColors(
            containerColor = PurpleGrey80 // Custom light beige background
    ) ){
        Column(modifier = Modifier.padding(16.dp).imePadding() // Add this line
            .verticalScroll(rememberScrollState()), // Add this line
             ) {
            Text(
                text = "View Appointment",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)

            )

            // Appointment Info
            AppointmentDetailRow(label = "Appointment Number", value = singleAppoinment!!.aptNumber)
            AppointmentDetailRow(label = "Name", value = "${user.firstName} ${user.lastName}")
            AppointmentDetailRow(label = "Email", value = user.email)
            AppointmentDetailRow(label = "Mobile Number", value = user.contact)
            AppointmentDetailRow(label = "Appointment Date", value = singleAppoinment.aptdate)
            AppointmentDetailRow(label = "Appointment Time", value = singleAppoinment.apttime)
            AppointmentDetailRow(label = "Apply Date", value = singleAppoinment.timestamp)
            AppointmentDetailRow(label = "Status", value = appointmentStatus)

            Spacer(modifier = Modifier.height(16.dp))

            // Remarks Section
            Text(
                text = "Remarks:",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = remark,
                onValueChange = { remark = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { Text(text = "Enter remarks...") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Status Dropdown
            Text(
                text = "Status:",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            StatusDropdown(
                selectedStatus = appointmentStatus,
                onStatusSelected = { newStatus -> appointmentStatus = newStatus }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            Button(
                onClick = {
                    val currentRemarkDate = getCurrentDate()
                    appointmentViewModel.updateAppointment(
                        appointmentId = appointmentId,
                        newStatus = appointmentStatus,
                        newRemark = remark,
                        remarkDate = currentRemarkDate,
                        onSuccess = {
                            Toast.makeText(cotext, "Appointment Status Successfully Updated", Toast.LENGTH_SHORT).show()
                            navController.navigate(Route.AllAppointmentScreen)
                        },
                        onFailure = { error ->
                            Toast.makeText(cotext, "Failed to update: $error", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
                ,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Submit", color = Color.White)
            }
        }
    }
}

// Helper function to get the current date as a string
fun getCurrentDate(): String {
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return currentDateTime.format(formatter)
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
@Composable
fun StatusDropdown(selectedStatus: String, onStatusSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf(selectedStatus) }
    val statusOptions = listOf("pending", "Selected", "Rejected")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.small)
            .padding(horizontal = 16.dp, vertical = 12.dp)
           // .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), shape = MaterialTheme.shapes.small)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = status,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Icon",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            statusOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        status = option
                        onStatusSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
