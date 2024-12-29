package com.example.salonappadmin.Admin_App_UI

import android.widget.Toast
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.salonappadmin.viewModel.ServicesViewModel
import com.example.salonappadmin.FirebaseDB.Service
import com.example.salonappadmin.Navigate_Screens.Route
import com.example.salonappadmin.viewModel.InvoiceViewModel

@Composable
fun AssignServicesScreen(userId: String, navController: NavHostController) {
    val serviceViewModel: ServicesViewModel = viewModel()
    val invoiceViewModel: InvoiceViewModel = viewModel()
    val services by serviceViewModel.serviceList.collectAsState()
    val selectedServices = remember { mutableStateMapOf<String, Boolean>() }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(0.dp, 105.dp, 0.dp, 0.dp)
    ) {
        // Header Section
        HeaderSection()

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = "Assign Services:",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF333333),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Services List Table
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                   // .weight(0.1f)
                    .height(450.dp)
                , // LazyColumn takes available space but leaves room for the button
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Table Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Service Name", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Service Price", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Action", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    Divider(color = Color.Gray, thickness = 1.dp)

                    // LazyColumn for Services
                    LazyColumn {
                        items(services) { service ->
                            ServiceRow(service = service, selectedServices = selectedServices)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {



                    }

                }



            }
            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button
            Button(
                onClick = {
                    val selectedServiceList = services.filter { selectedServices[it.id] == true }
                    val totalCost = selectedServiceList.sumOf { it.cost.toInt() }.toString()

                    // Add invoice using InvoiceViewModel
                    invoiceViewModel.addInvoice(
                        userId = userId,
                        services = selectedServiceList,
                        totalCost = totalCost,
                        onSuccess = {
                            Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show()
                            navController.navigate(Route.InvoiceListScreen)
                        },
                        onFailure = {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF512DA8))
            ) {
                Text(text = "Submit", color = Color.White, fontWeight = FontWeight.Bold)
            }

        }
    }
}



@Composable
fun ServiceRow(
    service: Service,
    selectedServices: MutableMap<String, Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(service.serviceName, fontSize = 16.sp)
        Text("₹${service.cost}", fontSize = 16.sp)

        // Checkbox for selecting service
        Checkbox(
            checked = selectedServices[service.id] ?: false,
            onCheckedChange = { isChecked ->
                selectedServices[service.id] = isChecked
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF512DA8)
            )
        )
    }
    Divider(color = Color.LightGray, thickness = 1.dp)
}
