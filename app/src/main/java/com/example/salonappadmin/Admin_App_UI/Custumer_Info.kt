package com.example.salonappadmin.Admin_App_UI

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.salonapp_admin.ui.theme.darkb
import com.example.salonapp_admin.ui.theme.darkb1
import com.example.salonappadmin.viewModel.UserViewModel_Admin
import com.example.salonappadmin.FirebaseDB.UsersInfo
import com.example.salonappadmin.Navigate_Screens.Route
import com.example.salonappadmin.R
import com.example.salonappadmin.ui.theme.cardBackgroundColor
import com.example.salonappadmin.ui.theme.iconColor
import com.example.salonappadmin.ui.theme.textColor


@Composable
fun CustomerInfo(navController: NavHostController) {
    val viewModel: UserViewModel_Admin = viewModel()
    val users by viewModel.usersList.collectAsState()
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
                text = "Customers",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp, // Set the font size to 20.sp
                fontWeight = FontWeight.Bold, // Set the font weight to bold
                color = Color.Black // Change to your desired color
            )

            LazyColumn(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 100.dp)) {

                if (users.isEmpty()) {
                    item {
                        Text(
                            "No users found.",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }


                items(users) { user ->
                    UserItem(user,navController)
                }

            }
        }
    }
}






@Composable
fun UserItem(user: UsersInfo, navController: NavHostController) {
    val selected = remember { mutableStateOf("temp") }
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(12.dp), // Softer rounded corners
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor // Custom light beige background
        ),
    ) {
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(16.dp)) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    , // Padding within the card
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Display user information in a column with improved styling
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp), // Added padding between text and icons
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${user.firstName} ${user.lastName}",
                        color = textColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Contact: ${user.contact}",
                        color = textColor.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Email: ${user.email}",
                        color = textColor.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }

                Column(horizontalAlignment = Alignment.End) {

                    Row() {


                        // Edit IconButton
                        IconButton(
                            onClick = { navController.navigate("editUser/${user.id}") },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                painterResource(id = R.drawable.pen),
                                contentDescription = null,
                                tint = iconColor
                            )
                        }

                        // Delete IconButton with dialog handling
                        val viewModel: UserViewModel_Admin = viewModel()
                        val showDialog = remember { mutableStateOf(false) }

                        IconButton(onClick = {
                            selected.value = "del"
                            showDialog.value = true
                        }) {
                            Icon(
                                painterResource(id = R.drawable.del),
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        if (showDialog.value) {
                            Delet_Customer_Dialog(
                                userId = user.id,
                                viewModel = viewModel,
                                navController = navController,
                                context = context,
                                showDialog = showDialog.value,
                                onDismiss = { showDialog.value = false }
                            )
                        }

                    }


                }
            }
            Button(
                onClick = { navController.navigate("AssignServicesScreen/${user.id}") },
                colors = ButtonDefaults.buttonColors(
                    iconColor, // Sets the background color
                    contentColor = Color.White  // Sets the text color
                    ,
                )
            ) {
                Text(text = "Assign Services")
            }
        }
    }
}




//LogOut DialogBox
@Composable
fun Delet_Customer_Dialog(
    userId: String,
    viewModel: UserViewModel_Admin,
    navController: NavController,
    context: Context,
    showDialog: Boolean,
    onDismiss: () -> Unit,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Delet Customer Confirmation") },
            text = { Text("Are you sure you want to Customer Delet?") },
            confirmButton = {
                Button(onClick = {
                    // Perform Delet action
                    viewModel.deleteUser(userId,
                        onSuccess = {
                            Toast.makeText(context, "User deleted successfully", Toast.LENGTH_SHORT).show()
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