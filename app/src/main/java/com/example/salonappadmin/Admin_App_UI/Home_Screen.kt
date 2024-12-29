/*package com.example.salonappadmin.Admin_App_UI

import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.salonapp_admin.ui.theme.darkb
import com.example.salonapp_admin.ui.theme.darkb1
import com.example.salonappadmin.Navigate_Screens.Route
import com.example.salonappadmin.viewModel.AppointmentViewModel
import com.example.salonappadmin.viewModel.ServicesViewModel
import com.example.salonappadmin.viewModel.UserViewModel_Admin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch




@Composable
fun Home_Screen(
    navController: NavHostController,
) {
    Scaffold {it
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            darkb1, Color.White
                        )
                    )
                )
        ) {

            val context = LocalContext.current
                    //CircularProgressIndicator()
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {


                        Dashbord( navController)




            }
        }
    }
}




@Composable
fun Dashbord(
    navController: NavController
) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }


    //for  UserViewModel_Admin
    val viewModel: UserViewModel_Admin = viewModel()
    val userCount by viewModel.userCount.collectAsState()

    //for services view model
    val seviceviewmodel:ServicesViewModel=viewModel()
    val serviceCount by seviceviewmodel.serviceCount.collectAsState()

    // For AppointmentViewModel
    val appointmentViewModel: AppointmentViewModel = viewModel()
    val appointmentCount by appointmentViewModel.appointmentCount.collectAsState()
    val selectedAppointmentCount by appointmentViewModel.selectedAppointmentCount.collectAsState()
    val rejectedAppointmentCount by appointmentViewModel.rejectedAppointmentCount.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        darkb1, Color.White
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {

        Box(
            modifier = Modifier
                // .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    //  .width(500.dp) // Set fixed width
                    .height(61.dp) // Set fixed height
                    .background(color = Color.Transparent)
                // .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(7.dp)),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
            ) {


            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .verticalScroll(rememberScrollState()), // Enable vertical scroll for the entire content
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
                )
                {


                    // User preferences to get userId
                    val context = LocalContext.current



                    Text(
                        text = "Dashbord",
                        fontSize = 30.sp,
                        fontFamily = FontFamily.SansSerif,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = darkb
                    )


                }






                Spacer(modifier = Modifier.height(40.dp))

                Column() {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        OutlinedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            onClick = {},
                            modifier = Modifier
                                .size(width = 160.dp, height = 100.dp)
                                .shadow(elevation = 7.dp)

                        ) {


                            OutlinedCard(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(width = 160.dp, height = 30.dp)
                                    .fillMaxSize(),
                                colors = cardColors(
                                    containerColor = darkb
                                ),
                            ) {
                                BasicText(

                                    text = "No Of Customer",
                                    style = TextStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    ),

                                    modifier = Modifier
                                        .padding(7.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .align(Alignment.CenterHorizontally)


                                )
//                                Text(text = "Base Amount is ")

                            }

                            Text(
                                text = userCount.toString(),
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .align(alignment = Alignment.CenterHorizontally)
                            )


                        }


//                    Spacer(modifier = Modifier.padding(30.dp))
                        OutlinedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            onClick = {},
                            modifier = Modifier
                                .size(width = 160.dp, height = 100.dp)
                                .shadow(elevation = 8.dp)
                        ){

                            OutlinedCard(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(width = 160.dp, height = 30.dp)
                                    .fillMaxSize()
                                    .align(alignment = Alignment.CenterHorizontally)
                                    .align(alignment = AbsoluteAlignment.Right),
                                colors = CardDefaults.cardColors(
                                    containerColor = darkb
                                ),


                                ) {
                                BasicText(
                                    text = "No of Services",
                                            style = TextStyle(
                                            color = Color.White,
                                    fontWeight = FontWeight.Bold
                                ),

                                    modifier = Modifier
                                        .padding(7.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .align(Alignment.CenterHorizontally)


                                )

                            }
                            Text(
                                text = serviceCount.toString()
                                , color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .align(alignment = Alignment.CenterHorizontally)
                            )
                        }

                    }


                    //Row2

                    Spacer(modifier = Modifier.padding(1.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly

                    ) {


                        OutlinedCard(

                            modifier = Modifier
                                .size(width = 160.dp, height = 100.dp)
                                .shadow(elevation = 8.dp),
                            shape = RoundedCornerShape(20.dp),
                            onClick = {navController.navigate(Route.AllAppointmentScreen)},
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),

                            )
                        {

                            OutlinedCard(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(width = 160.dp, height = 30.dp)
                                    .fillMaxSize()
                                    .align(alignment = Alignment.CenterHorizontally)
                                    .align(alignment = AbsoluteAlignment.Right),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFFF435F)
                                ),


                                ) {
                                BasicText(
                                    text = "All Appointment",
                                    style = TextStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    ),

                                    modifier = Modifier
                                        .padding(7.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .align(Alignment.CenterHorizontally)

                                )

                            }
                            Text(
                                text = appointmentCount.toString(),
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .align(alignment = Alignment.CenterHorizontally)
                            )
                        }

                        OutlinedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            // onClick = {},
                            modifier = Modifier
                                .size(width = 160.dp, height = 100.dp)
                                .shadow(elevation = 8.dp)
                        ) {
                            OutlinedCard(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(width = 160.dp, height = 30.dp)
                                    .fillMaxSize()
                                    .align(alignment = Alignment.CenterHorizontally)
                                    .align(alignment = AbsoluteAlignment.Right)
                                    .shadow(15.dp, ambientColor = Color.Blue),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFF25C9FA)
                                ),


                                ) {
                                BasicText(
                                    text = "DDDD",
                                    style = TextStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    ),

                                    modifier = Modifier
                                        .padding(7.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .align(Alignment.CenterHorizontally)

                                )


                            }
                            Text(
                                text = "DDDD",
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .align(alignment = Alignment.CenterHorizontally)
                            )
                        }

                    }


                    //Row3

                    Spacer(modifier = Modifier.padding(1.dp))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        OutlinedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            onClick = {},
                            modifier = Modifier
                                .size(width = 160.dp, height = 100.dp)
                                .shadow(elevation = 7.dp)

                        ) {


                            OutlinedCard(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(width = 160.dp, height = 30.dp)
                                    .fillMaxSize(),
                                colors = cardColors(
                                    containerColor = darkb
                                ),
                            ) {
                                BasicText(

                                    text = "EEEE",
                                    style = TextStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    ),

                                    modifier = Modifier
                                        .padding(7.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .align(Alignment.CenterHorizontally)


                                )
//                                Text(text = "Base Amount is ")
//
                            }
                            Text(
                                text = "EEEE",
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .align(alignment = Alignment.CenterHorizontally)
                            )


                        }
//                    Spacer(modifier = Modifier.padding(30.dp))
                        OutlinedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            onClick = {},
                            modifier = Modifier
                                .size(width = 160.dp, height = 100.dp)
                                .shadow(elevation = 8.dp)
                        ) {

                            OutlinedCard(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(width = 160.dp, height = 30.dp)
                                    .fillMaxSize()
                                    .align(alignment = Alignment.CenterHorizontally)
                                    .align(alignment = AbsoluteAlignment.Right),
                                colors = CardDefaults.cardColors(
                                    containerColor = darkb
                                ),


                                ) {
                                BasicText(
                                    text = "FFFF",
                                    style = TextStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    ),

                                    modifier = Modifier
                                        .padding(7.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .align(Alignment.CenterHorizontally)


                                )

                            }
                            Text(
                                text = "FFFF", modifier = Modifier
                                    .padding(top = 15.dp)
                                    .align(alignment = Alignment.CenterHorizontally)
                            )
                        }

                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

            }
        }
    }
}*/





package com.example.salonappadmin.Admin_App_UI

import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.salonapp_admin.ui.theme.darkb
import com.example.salonapp_admin.ui.theme.darkb1
import com.example.salonappadmin.Navigate_Screens.Route
import com.example.salonappadmin.viewModel.AppointmentViewModel
import com.example.salonappadmin.viewModel.ServicesViewModel
import com.example.salonappadmin.viewModel.UserViewModel_Admin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch




@Composable
fun Home_Screen(
    navController: NavHostController,
) {
    Scaffold {it
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            darkb1, Color.White
                        )
                    )
                )
        ) {

            val context = LocalContext.current
            //CircularProgressIndicator()
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {


                Dashbord( navController)




            }
        }
    }
}



@Composable
fun Dashbord(
    navController: NavController
) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    // ViewModel instances
    val viewModel: UserViewModel_Admin = viewModel()
    val userCount by viewModel.userCount.collectAsState()

    val serviceViewModel: ServicesViewModel = viewModel()
    val serviceCount by serviceViewModel.serviceCount.collectAsState()

    val appointmentViewModel: AppointmentViewModel = viewModel()
    val appointmentCount by appointmentViewModel.appointmentCount.collectAsState()
    val selectedAppointmentCount by appointmentViewModel.selectedAppointmentCount.collectAsState()
    val rejectedAppointmentCount by appointmentViewModel.rejectedAppointmentCount.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(darkb1, Color.White)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                // .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    //  .width(500.dp) // Set fixed width
                    .height(66.dp) // Set fixed height
                    .background(color = Color.Transparent)
                // .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(7.dp)),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
            ) {


            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                HeaderSection()

                Text(
                    text = "Dashboard",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = darkb
                )

                Spacer(modifier = Modifier.height(40.dp))

                Column {
                    // Row with cards for user count and service count
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DashboardCard("No Of Customers", userCount.toString())
                        DashboardCard("No of Services", serviceCount.toString())
                    }

                    Spacer(modifier = Modifier.padding(1.dp))

                    // Row with cards for appointment count and selected appointments
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DashboardCard(
                            title = "All Appointments",
                            count = appointmentCount.toString(),
                            onClick = { navController.navigate(Route.AllAppointmentScreen) }
                        )
                        DashboardCard("Selected Appointments", selectedAppointmentCount.toString())
                    }

                    Spacer(modifier = Modifier.padding(1.dp))

                    // Row with card for rejected appointments
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DashboardCard("Rejected Appointments", rejectedAppointmentCount.toString())
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DashboardCard(
                            title = "All Invoice",
                            count = appointmentCount.toString(),
                            onClick = { navController.navigate(Route.InvoiceListScreen) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    count: String,
    onClick: (() -> Unit)? = null
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = { onClick?.invoke() },
        modifier = Modifier
            .size(width = 160.dp, height = 100.dp)
            .shadow(elevation = 8.dp)
    ) {
        OutlinedCard(
            modifier = Modifier
                .padding(4.dp)
                .size(width = 160.dp, height = 30.dp)
                .fillMaxSize(),
            colors = cardColors(containerColor = darkb),
        ) {
            BasicText(
                text = title,
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(7.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Text(
            text = count,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 15.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}
