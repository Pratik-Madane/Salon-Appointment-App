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
import com.example.salonappcustomer.FirebaseDB.Service
import com.example.salonappcustomer.Navigate_Screens.Route

import com.example.customermodule.R
import com.example.salonappcustomer.ui.theme.darkb1
import com.example.salonappcustomer.viewModel.ServicesViewModel


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
            HeaderSection2()
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
    val selected = remember { mutableStateOf("temp") }
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

            // Action Button Row
            Row(
                modifier = Modifier
                    //.fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { navController.navigate(Route.BookAppointmentScreen) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = darkb1
                    ),
                    modifier = Modifier
                        .defaultMinSize(minWidth = 100.dp)
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = "Book Now",
                        color = Color.White
                    )
                }
                IconButton(
                    onClick = {
                        Toast.makeText(
                            context,
                            "Added to favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.hart), // Replace with your icon resource
                        contentDescription = "Favorite Icon",
                        tint = Color.Red
                    )
                }
            }

            // Show delete confirmation dialog if triggered
//            if (showDialog.value) {
//                Delet_Serviice_Dialog(
//                    serviceId = service.id,
//                    viewModel = seviceviewModel,
//                    navController = navController,
//                    context = context,
//                    showDialog = showDialog.value,
//                    onDismiss = { showDialog.value = false }
//                )
//            }
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
        else -> R.drawable.facepack // fallback image if service name doesn't match
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "$serviceName image",
        modifier = Modifier
            .size(90.dp)
            .padding(bottom = 8.dp)
    )
}




