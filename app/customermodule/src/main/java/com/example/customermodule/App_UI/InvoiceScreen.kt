package com.example.salonappcustomer.App_UI

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.customermodule.R
import com.example.salonappcustomer.User_Preferences.UserPreferences
import com.example.salonappcustomer.viewModel.InvoiceViewModel
import com.example.salonappcustomer.viewModel.UserViewModel


@Composable
fun InvoiceScreen(navController: NavHostController, invoiceId: String, customerId: String) {
    val invoiceViewModel: InvoiceViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()
    val invoiceList = invoiceViewModel.invoiceList.collectAsState().value
    val usersList = userViewModel.usersList.collectAsState().value








    // Find the user by userId
   // val selectedUser = usersList.find { it.id == userId }

    val user by userViewModel.userLiveData.observeAsState() // Observe user data
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val userId = userPreferences.getUserId()

    LaunchedEffect(userId) {
        // Load user data when the profile screen is opened
        userViewModel.getUserProfile(userId!!)
    }

    // Find the Invoice
    val selectedInvoice = invoiceList.find { it.id == invoiceId }

    // Safely construct customer details with fallback values
    val customerName = "${user?.firstName ?: ""} ${user?.lastName ?: ""}".trim()
    val contactNumber = user?.contact ?: "N/A"
    val email = user?.email ?: "N/A"
    val registrationDate = user?.timestamp ?: "N/A"
    val invoiceDate = selectedInvoice?.postingDate ?: "N/A"
    val services = selectedInvoice?.services ?: emptyList()
    val grandTotal = selectedInvoice?.totalCost ?: "N/A"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
            .padding(0.dp, 70.dp),

        ) {
        // Header
        HeaderSection2()

        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
               // .border(1.dp, Color.Black) // Adds a 1dp black border
                .padding(16.dp, 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
                    .border(1.dp, Color.Black) // Adds a 1dp black border
                    .padding(16.dp, 0.dp)
            ) {


                // Invoice ID
                Text(
                    text = "Invoice ${selectedInvoice?.id ?: "N/A"}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(vertical = 8.dp)
                )




                Spacer(modifier = Modifier.height(8.dp))

                // Customer Details
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Customer Details", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        CustomerDetailRow(label = "Name", value = customerName)
                        CustomerDetailRow(label = "Contact No.", value = contactNumber)
                        CustomerDetailRow(label = "Email", value = email)
                        CustomerDetailRow(label = "Registration Date", value = registrationDate)
                        CustomerDetailRow(label = "Invoice Date", value = invoiceDate)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Service Details
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Service Details", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Service", fontWeight = FontWeight.SemiBold)
                            Text("Cost", fontWeight = FontWeight.SemiBold)
                        }
                        Divider(modifier = Modifier.padding(vertical = 4.dp))

                        services.forEach { service ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(service.serviceName)
                                Text(service.cost)
                            }
                        }
                        Divider(modifier = Modifier.padding(vertical = 4.dp))

                        // Grand Total
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Grand Total", fontWeight = FontWeight.Bold)
                            Text(grandTotal, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Print Button
                IconButton(
                    onClick = { /* Implement Print Action */ },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.appoinment),
                        contentDescription = "Print Invoice",
                        tint = Color(0xFF333333),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderSection3() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF512DA8))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "ONE CUT'S HAIR AND BEAUTY STUDIO",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Sangamner",
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun CustomerDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = FontWeight.SemiBold)
        Text(value)
    }
}
