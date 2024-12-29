package com.example.salonappadmin.Admin_App_UI

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.salonapp_admin.ui.theme.darkb
import com.example.salonapp_admin.ui.theme.darkb1
import com.example.salonappadmin.FirebaseDB.Appointment
import com.example.salonappadmin.FirebaseDB.UsersInfo
import com.example.salonappadmin.R
import com.example.salonappadmin.viewModel.AppointmentViewModel
import com.example.salonappadmin.viewModel.UserViewModel_Admin

@Composable
fun AllAppointmentScreen(navController: NavHostController) {
    val appointmentViewModel: AppointmentViewModel = viewModel()
    val userViewModel: UserViewModel_Admin = viewModel()

    val appointments by appointmentViewModel.appointmentsList.collectAsState()
    val users by userViewModel.usersList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 105.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(darkb1, Color.White)
                )
            )
            //.background(MaterialTheme.colorScheme.background)
    ) {

//        Text(
//            text = "ONE CUT'S HAIR AND BEAUTY STUDIO,\nSangamner",
//            style = MaterialTheme.typography.labelLarge,
//            modifier = Modifier.padding(bottom = 16.dp),
//            textAlign = TextAlign.Center,
//            color = MaterialTheme.colorScheme.primary
//        )

        HeaderSection()
        Column (modifier = Modifier

            .padding(16.dp, 0.dp)){


            Text(
                text = "Appointment's ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0082B4),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn {
                items(appointments) { appointment ->
                    // Find the user details for the appointment
                    val user = users.find { it.id == appointment.custumerId }
                    if (user != null) {
                        AppointmentCard(appointment, user, navController)
                    }
                }
            }

        }
    }

}

@Composable
fun AppointmentCard(appointment: Appointment, user: UsersInfo, navController: NavHostController) {

    val AppointmentViewModel:AppointmentViewModel= viewModel()
    var  context= LocalContext.current
    Card(
        shape = RoundedCornerShape(12.dp), // Softer rounded corners
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)


          //  .padding(vertical = 8.dp)

       , colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8FF), // Light grayish blue background for a clean look
            contentColor = Color.DarkGray // Dark gray text color for readability
        )
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
                Text(
                    text = "Name: ${user.firstName} ${user.lastName}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Mobile: ${user.contact}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
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