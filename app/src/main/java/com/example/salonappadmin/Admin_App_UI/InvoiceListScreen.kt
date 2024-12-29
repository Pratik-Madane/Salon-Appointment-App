package com.example.salonappadmin.Admin_App_UI


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.salonappadmin.FirebaseDB.Invoice
import com.example.salonappadmin.Navigate_Screens.Route
import com.example.salonappadmin.viewModel.InvoiceViewModel
import com.example.salonappadmin.viewModel.UserViewModel_Admin
import kotlinx.coroutines.launch

@Composable
fun InvoiceListScreen(navController: NavHostController) {
    val invoiceViewModel: InvoiceViewModel = viewModel()
    val invoiceList by invoiceViewModel.invoiceList.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    // Fetch invoices initially
    LaunchedEffect(Unit) {
        invoiceViewModel.fetchInvoices()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF0F0F0)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 105.dp)
        ) {
            Text(
                text = "Invoice List",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
                color = Color(0xFF333333)
            )
            val context = LocalContext.current

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(invoiceList) { invoice ->
                    InvoiceItem(
                        invoice = invoice,
                        onViewClick = { userId, invoiceId ->
                            // Navigate to the InvoiceScreen route with userId and invoiceId
                            navController.navigate("invoice_screen/$userId/$invoiceId")
                        },
                        onDeleteClick = {
                            coroutineScope.launch {
                                invoiceViewModel.deleteInvoice(invoice.id, {
                                    Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show()
                                }, { error ->
                                    Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
                                })
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun InvoiceItem(
    invoice: Invoice,
    onViewClick: (userId: String, invoiceId: String) -> Unit,
    onDeleteClick: () -> Unit
) {
    // Get the UserViewModel_Admin instance
    val userViewModel: UserViewModel_Admin = viewModel()

    // Observe the usersList to get all users
    val usersList = userViewModel.usersList.collectAsState().value

    // Find the user by userId from the invoice
    val selectedUser = usersList.find { it.id == invoice.userId }

    // Handle the case where selectedUser is null, you can show a placeholder or handle errors
    val customerName = selectedUser?.let {
        if (it.firstName.isNotBlank()) it.firstName else it.lastName
    } ?: "Unknown Customer"

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Invoice ID: ${invoice.id}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF444444),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Customer: $customerName",
                    color = Color(0xFF666666),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Date: ${invoice.postingDate}",
                    color = Color(0xFF999999),
                    fontSize = 12.sp
                )
            }
            Column {
                Button(
                    onClick = { onViewClick(invoice.userId, invoice.id) },
                    colors = ButtonDefaults.buttonColors(Color(0xFF007BFF)),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("View", color = Color.White)
                }
                Button(
                    onClick = onDeleteClick,
                    colors = ButtonDefaults.buttonColors(Color(0xFFFF4D4D))
                ) {
                    Text("Delete", color = Color.White)
                }
            }
        }
    }
}


//
//@Composable
//fun InvoiceListScreen(navController: NavHostController) {
//    val invoiceViewModel: InvoiceViewModel = viewModel()
//    val invoiceList by invoiceViewModel.invoiceList.collectAsState()
//    val coroutineScope = rememberCoroutineScope()
//
//    // Fetch invoices initially
//    LaunchedEffect(Unit) {
//        invoiceViewModel.fetchInvoices()
//    }
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = Color(0xFFF0F0F0)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp, 105.dp)
//        ) {
//            Text(
//                text = "Invoice List",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 16.dp),
//                textAlign = TextAlign.Center,
//                color = Color(0xFF333333)
//            )
//            val context = LocalContext.current
//
//            LazyColumn(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(invoiceList) { invoice ->
//                    InvoiceItem(
//                        invoice = invoice,
//                        onViewClick = { userId, invoiceId ->
//                            // Navigate and pass userId and invoiceId
//                            navController.navigate("invoice_screen/${userId}/${invoiceId}")
//                        },
//                        onDeleteClick = {
//                            coroutineScope.launch {
//                                invoiceViewModel.deleteInvoice(invoice.id, {
//                                    Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show()
//                                }, { error ->
//                                    Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
//                                })
//                            }
//                        }
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun InvoiceItem(
//    invoice: Invoice,
//    onViewClick: (userId: String, invoiceId: String) -> Unit,
//    onDeleteClick: () -> Unit
//) {
//    // Get the UserViewModel_Admin instance
//    val userViewModel: UserViewModel_Admin = viewModel()
//
//    // Observe the usersList to get all users
//    val usersList = userViewModel.usersList.collectAsState().value
//
//    // Find the user by userId from the invoice
//    val selectedUser = usersList.find { it.id == invoice.userId }
//
//    // Handle the case where selectedUser is null, you can show a placeholder or handle errors
//    val customerName = selectedUser?.let {
//        if (it.firstName.isNotBlank()) it.firstName else it.lastName
//    } ?: "Unknown Customer"
//
//    Card(
//        shape = MaterialTheme.shapes.medium,
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color.White)
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column {
//                Text(
//                    text = "Invoice ID: ${invoice.id}",
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF444444),
//                    fontSize = 16.sp
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = "Customer: ${customerName}",
//                    color = Color(0xFF666666),
//                    fontSize = 14.sp
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = "Date: ${invoice.postingDate}",
//                    color = Color(0xFF999999),
//                    fontSize = 12.sp
//                )
//            }
//            Column {
//                Button(
//                    onClick = { onViewClick(invoice.userId, invoice.id) },
//                    colors = ButtonDefaults.buttonColors(Color(0xFF007BFF)),
//                    modifier = Modifier.padding(end = 8.dp)
//                ) {
//                    Text("View", color = Color.White)
//                }
//                Button(
//                    onClick = onDeleteClick,
//                    colors = ButtonDefaults.buttonColors(Color(0xFFFF4D4D))
//                ) {
//                    Text("Delete", color = Color.White)
//                }
//            }
//        }
//    }
//}
