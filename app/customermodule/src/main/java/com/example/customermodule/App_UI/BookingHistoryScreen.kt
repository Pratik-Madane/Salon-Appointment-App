package com.example.salonappcustomer.App_UI

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.salonappcustomer.FirebaseDB.Appointment
import com.example.customermodule.R
//import com.example.salonappcustomer.R
import com.example.salonappcustomer.User_Preferences.UserPreferences
import com.example.salonappcustomer.ui.theme.darkb
import com.example.salonappcustomer.viewModel.AppointmentViewModel

@Composable
fun BookingHistoryScreen(navController: NavHostController,viewModel: AppointmentViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val appointments by viewModel.appointmentsList.collectAsState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 105.dp)
            .background(Color(0xFFF5F5F5)) // Light background

    ) {
        HeaderSection2()
        //Spacer(modifier = Modifier.height(8.dp))
       // BreadcrumbSection()
        Spacer(modifier = Modifier.height(16.dp))
        BookingSection(navController,appointments)
        Spacer(modifier = Modifier.height(24.dp))
        FooterSection()
    }
}


@Composable
fun HeaderSection1() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color(0xFF0082B4)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Booking History",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BreadcrumbSection() {
    Text(
        text = "Home > Booking History",
        fontSize = 14.sp,
        color = Color.Gray,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}
//
//@Composable
//fun BookingTableSection() {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Appointment History",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color(0xFF0082B4),
//            modifier = Modifier.padding(bottom = 8.dp)
//        )
//
//        LazyColumn(
//            modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(vertical = 8.dp)
//        ) {
//            // Header Row
//            item {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color(0xFFE0E0E0))
//                        .padding(vertical = 8.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    TableHeaderCell("Apt Number", Modifier.weight(1.2f))
//                    TableHeaderCell("Date", Modifier.weight(1f))
//                    TableHeaderCell("Time", Modifier.weight(1f))
//                    TableHeaderCell("Status", Modifier.weight(1f))
//                    TableHeaderCell("Action", Modifier.weight(0.8f))
//                }
//            }
//
//            // Example Appointment Rows
//            items(5) { // Replace 5 with your actual data list size
//                BookingRow(
//                    appointmentNumber = "S4102901",
//                    appointmentDate = "2024-11-06",
//                    appointmentTime = "02:30 PM",
//                    appointmentStatus = "Waiting for confirmation"
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun BookingTableSection(appointments: List<Appointment>) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//
//        val context = LocalContext.current
//        val userPreferences = UserPreferences(context)
//        val userId = userPreferences.getUserId()
//
//
//        Text(
//            text = "Appointment History",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color(0xFF0082B4),
//            modifier = Modifier.padding(bottom = 8.dp)
//        )
//
//        LazyColumn(
//            modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(vertical = 8.dp)
//        ) {
//            // Header Row
//            item {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color(0xFFE0E0E0))
//                        .padding(vertical = 8.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    TableHeaderCell("Apt Number", Modifier.weight(1.2f))
//                    TableHeaderCell("Date", Modifier.weight(1f))
//                    TableHeaderCell("Time", Modifier.weight(1f))
//                    TableHeaderCell("Status", Modifier.weight(1f))
//                    TableHeaderCell("Action", Modifier.weight(0.8f))
//                }
//            }
//
//            // Display User-specific Appointments
//            items(appointments.size) { index ->
//                val appointment = appointments[index]
//                BookingRow(
//                    appointmentNumber = appointment.id ?: "",
//                    appointmentDate = appointment.Aptdate ?: "",
//                    appointmentTime = appointment.Apttime ?: "",
//                    appointmentStatus = appointment.status ?: ""
//                )
//            }
//        }
//    }
//}

@Composable
fun BookingSection(navController: NavHostController, appointments: List<Appointment>) {

    val appointmentViewModel: AppointmentViewModel = viewModel()


    val appointments by appointmentViewModel.appointmentsList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val context = LocalContext.current
        val userPreferences = UserPreferences(context)
        val userId = userPreferences.getUserId()

        // Filter appointments for the current user
        val userAppointments = appointments.filter { it.custumerId == userId }

        Text(
            text = "Appointment History",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0082B4),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // LazyColumn with a fixed height (set exactly 300.dp)
        LazyColumn {
            items(userAppointments) { appointment ->


                    AppointmentCard(appointment,navController)

            }
        }
    }

}




@Composable
fun AppointmentCard(appointment: Appointment, navController: NavHostController) {

    val AppointmentViewModel:AppointmentViewModel= viewModel()
    var  context= LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Appointment Details Column
            Column(
                modifier = Modifier
                    .weight(1.2f)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Appointment Number:\n ${appointment.aptNumber}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
//                Text(
//                    text = "Name: ${user.firstName} ${user.lastName}",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//                Text(
//                    text = "Mobile: ${user.contact}",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.onSurface
//                )
                Text(
                    text = "Appointment Date: ${appointment.aptdate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Appointment Time: ${appointment.apttime}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Appointment Stutus Time: ${appointment.remarkDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    //show dilog box variable
                    val showDialog = remember { mutableStateOf(false) }

                    Button(
                        onClick = { navController.navigate("singleAppoinmentScreen/${appointment.id}") },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = "View")
                    }
                    Button(
                        onClick = {

                            //Show dilog Box
                            showDialog.value = true


                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                    ) {
                        Text(text = "Delete")
                    }
                    // Delete confirmation dialog
                    if (showDialog.value) {
                        Delet_Appoinment_Dialog(
                            appointmentId = appointment.id, // Pass the specific appoinment ID here
                            AppointmentViewModel = AppointmentViewModel,
                            navController = navController,
                            context = context,
                            showDialog = showDialog.value,
                            onDismiss = {
                                showDialog.value = false
                            }
                        )
                    }
                }
            }

            // Status Column
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(16.dp),
                //verticalArrangement =Arrangement.Center ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Status", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)

                when (appointment.status) {
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


            }
        }
    }
}



//LogOut DialogBox
@Composable
fun Delet_Appoinment_Dialog(
    appointmentId: String,
    AppointmentViewModel: AppointmentViewModel,
    navController: NavHostController,
    context: Context,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Delet Appoinment Confirmation") },
            text = { Text("Are you sure you want to Appoinment Delet?") },
            confirmButton = {
                Button(onClick = {
                    // Call deleteAppointment from the ViewModel
                    AppointmentViewModel.deleteAppointment(
                        appointmentId = appointmentId,
                        onSuccess = {
                            Toast.makeText(context, "Appointment deleted successfully", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = { error ->
                            Toast.makeText(context, "Failed to delete: $error", Toast.LENGTH_SHORT).show()
                        }
                    )

                    onDismiss() // Close the dialog
                },  colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red, // Background color for dismiss button
                    contentColor = Color.White   // Text color for dismiss button
                )) {
                    Text("Delet")
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = darkb, // Background color for dismiss button
                        contentColor = Color.White   // Text color for dismiss button
                    )
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}









