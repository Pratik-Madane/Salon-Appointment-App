
package com.example.salonappcustomer.App_UI

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.salonappcustomer.Navigate_Screens.Route
import com.example.customermodule.R
import com.example.salonappcustomer.ui.theme.darkb
import com.example.salonappcustomer.ui.theme.darkb1
import com.example.salonappcustomer.viewModel.AppointmentViewModel
import com.example.salonappcustomer.viewModel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(paddingValues: PaddingValues, navControllerLogin: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    val selected = remember { mutableStateOf(Icons.Default.Home) }

    // Navigation Drawer
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.padding(top = 100.dp)) {
                Box(
                    modifier = Modifier
                        .background(darkb)
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Icon",
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                }

                NavigationDrawerItems(coroutineScope, drawerState, navControllerLogin,navController)
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Customer",
                                color = Color.White,
                                fontSize = 20.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = darkb),
                        navigationIcon = {
                            IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                               // Icon(Icons.Rounded.Menu, contentDescription = "Menu", tint = Color.White)
                                Image(
                                    painter = painterResource(id = R.drawable.profilepicture),
                                    contentDescription = "App_Logo",
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomAppBarComponent(selected, navController)
                },
                content = {it


                    val viewModel: UserViewModel = viewModel()



                    NavHost(navController = navController, startDestination = Route.Home_Screen) {
                        composable(Route.Home_Screen) {
                            Home_Screen(navController)
                        }
                        composable(Route.Customer_Profile) {
                            Customer_Profile(viewModel)
                        }
                        composable(Route.BookAppointmentScreen) {
                            BookAppointmentScreen(navController)
                        }
                        
                        composable(Route.ShowServicesScreen) { 
                            ShowServicesScreen(navController = navController)
                        }
                        composable(Route.BookingHistoryScreen) {
                            BookingHistoryScreen(navController)
                        }

                        composable("singleAppoinmentScreen/{appoinmentId}") { backStackEntry ->
                            val appoinmentId =
                                backStackEntry.arguments?.getString("appoinmentId") ?: return@composable
                            val viewModel: AppointmentViewModel = viewModel()



                            //  val service by viewModel.selectedSevice.collectAsState()

                            if (appoinmentId != null) {
                                singleAppoinmentScreen(appoinmentId,navController)
                            } else {
                                Text("Loading user information...")
                            }
                        }
                        composable(Route.InvoiceListScreen) {
                            InvoiceListScreen(navController)
                        }

                        composable("invoice_screen/{userId}/{invoiceId}") { backStackEntry ->
                            val customerId = backStackEntry.arguments?.getString("userId") ?: return@composable
                            val invoiceId = backStackEntry.arguments?.getString("invoiceId") ?: return@composable

                            InvoiceScreen(navController = navController, customerId = customerId, invoiceId = invoiceId)
                        }

                    }

                }
            )
        }
    )
}


// NavigationDrawerItems to display drawer items
@Composable
fun NavigationDrawerItems(
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    navControllerLogin: NavController,
    navController: NavHostController
) {

    var context= LocalContext.current
    NavigationDrawerItem(
        label = { Text("Customer Profile", color = darkb) },
        icon = { Icon(Icons.Default.Person, contentDescription = "Admin Profile") },
        selected = false,
        onClick = {
            coroutineScope.launch { drawerState.close() }
            navController.navigate(Route.Customer_Profile) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )

   /* NavigationDrawerItem(
        label = { Text("Customers", color = darkb) },
        icon = {
//            Icon(painter = painterResource(id = R.drawable.people), contentDescription ="Customer" , modifier = Modifier.size(30.dp), tint = Color.Red)
            Icon(
                painterResource(id = R.drawable.people),
                contentDescription = null,
                modifier =  Modifier.size(30.dp),
                tint = Color.Black
            )
               },
        selected = false,
        onClick = {
            coroutineScope.launch { drawerState.close() }
            navController.navigate(Route.ShowServicesScreen)
            {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )*/


    //show dilog box variable
    val showDialog = remember { mutableStateOf(false) }

    NavigationDrawerItem(label = {
        Text(
            text = "LogOut",
            color = darkb
        )
    },
        selected = false,
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_logout_24),
                contentDescription = "LogOut"
            )
        },
        onClick = {
            coroutineScope.launch {
                drawerState.close()
            }
            //Show dilog Box
            showDialog.value = true
        })


    // Place the LogoutDialog outside of the IconButton onClick handler
    LogoutDialog(
        navControllerLogin = navControllerLogin,
        context = context,
        showDialog = showDialog.value,
        onDismiss = {
            showDialog.value = false
        } // Close the dialog when dismissed
    )


    // Add more NavigationDrawerItems as required
}




// BottomAppBar Component
@Composable
fun BottomAppBarComponent(selected: MutableState<ImageVector>, navController: NavController) {
    BottomAppBar(containerColor = darkb,modifier = Modifier.height(70.dp) // Set the height of the BottomAppBar
             ) {
        IconButton(
            onClick = {
                selected.value = Icons.Default.Home
                navController.navigate(Route.Home_Screen){
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            modifier = Modifier.weight(1f)
        ) {
//            Icon(
//                Icons.Default.Home,
//                contentDescription = "Home_Screen",
//                tint = if (selected.value == Icons.Default.Home) Color.White else Color.Black,
//                modifier = Modifier.size(if (selected.value == Icons.Default.Home) 35.dp else 26.dp)
//            )
            Icon(painter = painterResource(id = R.drawable.homeico), contentDescription = "home Icon",
                tint = if (selected.value == Icons.Default.Home) Color.White else Color.Black,
                modifier = Modifier.size(if (selected.value == Icons.Default.Home) 35.dp else 26.dp))
        }
/*
        IconButton(
            onClick = {
                selected.value = Icons.Default.Person
                navController.navigate(Route.Customer_Profile){
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Profile",
                tint = if (selected.value == Icons.Default.Person) Color.White else Color.Black,
                modifier = Modifier.size(if (selected.value == Icons.Default.Person) 35.dp else 26.dp)
            )
        }
*/
        FloatingActionButton(
            onClick = { selected.value = Icons.Default.Add
                      navController.navigate(Route.BookAppointmentScreen){
                          popUpTo(navController.graph.startDestinationId)
                          launchSingleTop = true
                      }},
            containerColor = if (selected.value == Icons.Default.Add) darkb1 else darkb,
            modifier = Modifier.size(if (selected.value == Icons.Default.Add) 55.dp else 50.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
        }

        IconButton(
            onClick = {
                selected.value = Icons.Default.Notifications
                navController.navigate(Route.ShowServicesScreen){
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            modifier = Modifier.weight(1f)
        ) {
//            Icon(
//                Icons.Default.Notifications,
//                contentDescription = "Notifications",
//                tint = if (selected.value == Icons.Default.Notifications) Color.White else Color.Black,
//                modifier = Modifier.size(if (selected.value == Icons.Default.Notifications) 35.dp else 26.dp)
//            )
            Icon(painter = painterResource(id = R.drawable.services), contentDescription = "home Icon",
                tint = if (selected.value == Icons.Default.Notifications) Color.White else Color.Black,
                modifier = Modifier.size(if (selected.value == Icons.Default.Notifications) 35.dp else 26.dp))
        }

        /*
        IconButton(
            onClick = {
                selected.value = Icons.Default.Star
                 navController.navigate(Route.Customer_Profile)
                 {
                     popUpTo(navController.graph.startDestinationId)
                     launchSingleTop = true
                 }
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Profile",
                tint = if (selected.value == Icons.Default.Star) Color.White else Color.Black,
                modifier = Modifier.size(if (selected.value == Icons.Default.Star) 35.dp else 26.dp)
            )
        }*/
    }
}


//LogOut DialogBox
@Composable
fun LogoutDialog(
    context: Context,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    navControllerLogin: NavController,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Logout Confirmation") },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                Button(onClick = {
                    // Perform logout action
                    logout(navControllerLogin, context)
                    onDismiss() // Close the dialog
                },  colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red, // Background color for dismiss button
                    contentColor = Color.White   // Text color for dismiss button
                )) {
                    Text("Logout")
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