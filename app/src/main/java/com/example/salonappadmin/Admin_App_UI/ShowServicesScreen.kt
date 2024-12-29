package com.example.salonappadmin.Admin_App_UI



import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.Surface
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
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.salonapp_admin.ui.theme.darkb
import com.example.salonapp_admin.ui.theme.darkb1
import com.example.salonappadmin.FirebaseDB.Service
import com.example.salonappadmin.viewModel.UserViewModel_Admin
import com.example.salonappadmin.R
import com.example.salonappadmin.viewModel.ServicesViewModel


@Composable
fun ShowServicesScreen(navController: NavHostController) {
    val seviceviewModel: ServicesViewModel = viewModel()
    val service by seviceviewModel.serviceList.collectAsState()
    var context= LocalContext.current
    Surface(modifier = Modifier.fillMaxSize()) {
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 105.dp, 0.dp, 0.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(darkb1, Color.White)
                )
            )
            , horizontalAlignment = Alignment.CenterHorizontally) {
            HeaderSection()
            Text(
                text = "Services",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp, // Set the font size to 20.sp
                fontWeight = FontWeight.Bold, // Set the font weight to bold
                color = Color.Black // Change to your desired color
            )

            LazyColumn(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 100.dp)) {

                if (service.isEmpty()) {
                    item {
                        Text(
                            "No service found.",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }


                items(service) { service ->
                    ServiceItem(service,navController,seviceviewModel)
                }

            }
        }
    }
}


@Composable
fun ServiceItem(
    service: Service,
    navController: NavHostController,
    seviceviewModel: ServicesViewModel
) {

    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    // New card color and elevation adjustments
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8FF), // Light grayish blue background for a clean look
            contentColor = Color.DarkGray // Dark gray text color for readability
        ),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp), // Spacing between the elements
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
               // // Display images based on service name with custom styling
              //  ServiceImage(service.serviceName)
                if (service.serviceName == "shave") {
                    Image(
                        painter = painterResource(id = R.drawable.salon2),
                        contentDescription = "",
                        //  modifier = Modifier.fillMaxWidth()
                        modifier = Modifier
                            .size(90.dp)
                            .padding(bottom = 8.dp)
                    )
                }

                if ( service.serviceName == "cuting" || service.serviceName=="Cutting") {
                    Image(painter = painterResource(id = R.drawable.cutting), contentDescription = "",modifier = Modifier
                        .size(90.dp)
                        .padding(bottom = 8.dp))
                }
                if (service.serviceName == "beard treem" || service.serviceName == "Beard Treem") {
                    Image(painter = painterResource(id = R.drawable.treemshave), contentDescription = "",modifier = Modifier
                        .size(90.dp)
                        .padding(bottom = 8.dp))
                }
                if (service.serviceName == "beard cut" || service.serviceName == "Beard Cut") {
                    Image(painter = painterResource(id = R.drawable.shave), contentDescription = "",modifier = Modifier
                        .size(90.dp)
                        .padding(bottom = 8.dp))
                }
                if (service.serviceName == "face massage" || service.serviceName == "Face Massage") {
                    Image(painter = painterResource(id = R.drawable.masaj), contentDescription = "",modifier = Modifier
                        .size(90.dp)
                        .padding(bottom = 8.dp))
                }
                if (service.serviceName == "Small Boy Cut" ) {
                    Image(painter = painterResource(id = R.drawable.hearcut2), contentDescription = "",modifier = Modifier
                        .size(90.dp)
                        .padding(bottom = 8.dp))
                }
                if (service.serviceName == "face pack" || service.serviceName == "Face Pack") {
                    Image(painter = painterResource(id = R.drawable.facepack), contentDescription = "",modifier = Modifier
                        .size(90.dp)
                        .padding(bottom = 8.dp))
                }
                if (service.serviceName == "face pack charcole" || service.serviceName == "Face Pack Charcole") {
                    Image(painter = painterResource(id = R.drawable.facepackcharcore), contentDescription = "",modifier = Modifier
                        .size(90.dp)
                        .padding(bottom = 8.dp))
                }

                Text(
                    text = service.serviceName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF37474F) // Darker color for emphasis
                )
                Text(
                    text = "Cost: ${service.cost}",
                    fontSize = 14.sp,
                    color = Color(0xFF616161) // Subtle gray color for secondary info
                )
                Text(
                    text = "Description: ${service.serviceDescription}",
                    fontSize = 14.sp,
                    color = Color(0xFF616161)
                )
                Text(
                    text = service.timestamp,
                    fontSize = 12.sp,
                    color = Color.Gray // Lighter color for less prominent info
                )
            }

            // Edit button with custom styling
            IconButton(

                onClick = { navController.navigate("editService/${service.id}") },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.pen),
                    contentDescription = null,
                    tint = Color(0xFF42A5F5), // Soft blue color
                    modifier = Modifier.size(26.dp)
                )
            }

            // Delete button with dialog trigger
            IconButton(
                onClick = { showDialog.value = true },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.del),
                    contentDescription = null,
                    tint = Color(0xFFF44336), // Soft red for delete
                    modifier = Modifier.size(26.dp)
                )
            }

            // Show delete confirmation dialog if triggered
            if (showDialog.value) {
                Delet_Serviice_Dialog(
                    serviceId = service.id,
                    viewModel = seviceviewModel,
                    navController = navController,
                    context = context,
                    showDialog = showDialog.value,
                    onDismiss = { showDialog.value = false }
                )
            }
        }
    }
}

// Helper function to display the appropriate image based on service name
@Composable
fun ServiceImage(serviceName: String) {
    val imageRes = when (serviceName.lowercase()) {
        "shave" -> R.drawable.salon2
        "cutting" -> R.drawable.cutting
        "beard trim" -> R.drawable.treemshave
        "beard cut" -> R.drawable.shave
        "face massage" -> R.drawable.masaj
        "small boy cut" -> R.drawable.hearcut2
        "face pack" -> R.drawable.facepack
        "face pack charcoal" -> R.drawable.facepackcharcore
        else -> R.drawable.prev // fallback image if service name doesn't match
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "$serviceName image",
        modifier = Modifier
            .size(90.dp)
            .padding(bottom = 8.dp)
    )
}





//Delete Service DialogBox
@Composable
fun Delet_Serviice_Dialog(
    serviceId: String,
    viewModel: ServicesViewModel,
    navController: NavHostController,
    context: Context,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Delet Services Confirmation") },
            text = { Text("Are you sure you want to Service Delet?") },
            confirmButton = {
                Button(onClick = {
                    // Perform Delet action
                    viewModel.deleteService(serviceId,
                        onSuccess = {
                            Toast.makeText(context, "Service deleted successfully", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = { errorMessage ->
                            Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
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