package com.example.salonappcustomer.App_UI

import android.widget.SearchView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.salonappcustomer.FirebaseDB.Service
import com.example.salonappcustomer.Navigate_Screens.Route
import com.example.customermodule.R
//import com.example.salonappcustomer.R
import com.example.salonappcustomer.User_Preferences.UserPreferences
import com.example.salonappcustomer.ui.theme.Purple
import com.example.salonappcustomer.ui.theme.darkb
import com.example.salonappcustomer.ui.theme.darkb1
import com.example.salonappcustomer.ui.theme.vilot
import com.example.salonappcustomer.viewModel.AppointmentViewModel
import com.example.salonappcustomer.viewModel.ServicesViewModel

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch





@Composable
fun Home_Screen(navController: NavHostController) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }



    val seviceViewModel: ServicesViewModel = viewModel()
    val serviceList by seviceViewModel.serviceList.collectAsState()



    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val userId = userPreferences.getUserId()

val AppointmentViewModel:AppointmentViewModel= viewModel()
    val appointments by AppointmentViewModel.appointmentsList.collectAsState()
    // Filter appointments for the current user
    val userAppointments = appointments.filter { it.custumerId == userId }

    // Filter appointments for the current user
   // val userAppointments = appointments.filter { it.customerId == userId }
    val selectedAppointmentsCount = userAppointments.count { it.status == "selected" ||it.status=="Selected" }
    val rejectedAppointmentsCount = userAppointments.count { it.status == "Rejected" || it.status=="rejected"}
    val pendingAppointmentsCount=userAppointments.count{it.status == "pending" || it.status=="Pending"}



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
                    .verticalScroll(rememberScrollState()), // Enable vertical scroll for the entire content
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
                )
                {


                    // User preferences to get userId
                    val context = LocalContext.current



               HeaderSection2()




                    Text(
                        text = "Services",
                        fontSize = 30.sp,
                        fontFamily = FontFamily.SansSerif,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = darkb,
//                        modifier = Modifier
//                            .graphicsLayer(
//                                shadowElevation = 8f, // The elevation value creates the shadow
//                                shape = RoundedCornerShape(8.dp), // Optional: adds rounding to the text background
//                                clip = true // Clipping to the rounded shape
//                            )
                        // fontSize = 22.sp
                    )


                    // Take the latest 4 properties after sorting
                    //  val latestProperties = serviceList


                    if (serviceList.isEmpty()) {
                        // Show "No items found" message if the filtered list is empty
                        Text(
                            text = "No Service found",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        val lazyListState = rememberLazyListState()
                        val coroutineScope = rememberCoroutineScope()


                        // LaunchedEffect to automatically scroll every 3 seconds


                        // LaunchedEffect to automatically scroll every 3 seconds
                        LaunchedEffect(key1 = lazyListState) {
                            while (true) {
                                delay(3000L) // Delay for 3 seconds
                                val totalItems = serviceList.size

                                // If it's the last visible item, reset the scroll to the first item
                                val nextIndex =
                                    if (lazyListState.firstVisibleItemIndex == totalItems - 1) {
                                        0 // Go back to the first item after reaching the end
                                    } else {
                                        lazyListState.firstVisibleItemIndex + 1 // Scroll to the next item
                                    }

                                coroutineScope.launch {
                                    // lazyListState.animateScrollToItem(nextIndex)
                                    // Smooth scrolling with animation over a duration
                                    lazyListState.animateScrollToItem(
                                        index = nextIndex,
                                        scrollOffset = 0 // Optional: set a specific offset if needed
                                    )
                                }
                            }
                        }

                        LazyRow(
                            modifier = Modifier
                                //.padding(0.dp, 0.dp, 15.dp, 0.dp,)
                                .fillMaxWidth(),
                            state = lazyListState //pass to state to lazy row
                        ) {


                            itemsIndexed(items = serviceList) { index, item ->

                                serviceCard(item, navController)
                                Spacer(modifier = Modifier.width(20.dp))
                            }
                        }

                    }
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
                            onClick = { navController.navigate(Route.BookingHistoryScreen) },
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

                                    text = "Booking History",
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
                                text = userAppointments.size.toString(),
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .align(alignment = Alignment.CenterHorizontally)
                            )
                        }


////                    Spacer(modifier = Modifier.padding(30.dp))
//                        OutlinedCard(
//                            colors = CardDefaults.cardColors(
//                                containerColor = Color.White,
//                            ),
//                            onClick = {},
//                            modifier = Modifier
//                                .size(width = 160.dp, height = 100.dp)
//                                .shadow(elevation = 8.dp)
//                        ) {
//
//                            OutlinedCard(
//                                modifier = Modifier
//                                    .padding(4.dp)
//                                    .size(width = 160.dp, height = 30.dp)
//                                    .fillMaxSize()
//                                    .align(alignment = Alignment.CenterHorizontally)
//                                    .align(alignment = AbsoluteAlignment.Right),
//                                colors = CardDefaults.cardColors(
//                                    containerColor = darkb
//                                ),
//
//
//                                ) {
//                                BasicText(
//                                    text = "No of Sold Services",
//                                    style = TextStyle(
//                                        color = Color.White,
//                                        fontWeight = FontWeight.Bold
//                                    ),
//
//                                    modifier = Modifier
//                                        .padding(7.dp)
//                                        .align(Alignment.CenterHorizontally)
//                                        .align(Alignment.CenterHorizontally)
//
//
//                                )
//
//                            }
//                            Text(
//                                text = "1", modifier = Modifier
//                                    .padding(top = 15.dp)
//                                    .align(alignment = Alignment.CenterHorizontally)
//                            )
//                        }

                    }


                    //Row2

                    Spacer(modifier = Modifier.padding(1.dp))
                    /*

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
                                    text = "CCCC",
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

                    }*/


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
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            onClick = { navController.navigate(Route.BookingHistoryScreen) },
                            modifier = Modifier
                                .size(width = 160.dp, height = 100.dp)
                                .shadow(elevation = 7.dp)
                        ) {
                            OutlinedCard(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(width = 160.dp, height = 30.dp)
                                    .fillMaxSize(),
                                colors = CardDefaults.cardColors(containerColor = darkb)
                            ) {
                                BasicText(
                                    text = "Selected Appointments",
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
                                text = selectedAppointmentsCount.toString(),
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }

                        OutlinedCard(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            onClick = {navController.navigate(Route.BookingHistoryScreen)},
                            modifier = Modifier
                                .size(width = 160.dp, height = 100.dp)
                                .shadow(elevation = 8.dp)
                        ) {
                            OutlinedCard(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(width = 160.dp, height = 30.dp)
                                    .fillMaxSize(),
                                colors = CardDefaults.cardColors(containerColor = darkb)
                            ) {
                                BasicText(
                                    text = "Rejected Appointments",
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
                                text = rejectedAppointmentsCount.toString(),
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    }


// Define some custom colors
                    val cardBackgroundColor = Color(0xFFF5E6CC) // Light warm beige
                    val textColor = Color(0xFF5D4037) // Dark brown for text
                    val iconColor = Color(0xFF795548) // Slightly lighter brown for icons
                    val accentColor = Color(0xFFE0A96D) // Accent color for slight highlights

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        OutlinedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = iconColor,
                            ),
                            onClick = { navController.navigate(Route.InvoiceListScreen) },
                            modifier = Modifier
                                .size(width = 160.dp, height = 100.dp)
                                .shadow(elevation = 7.dp)

                        ) {


                            OutlinedCard(
                                modifier = Modifier
                                    .padding(4.dp)
                                    //.size(width = 160.dp, height = 30.dp)
                                    .fillMaxSize(),
                                colors = cardColors(
                                    containerColor = cardBackgroundColor
                                ),
                            ) {
                                BasicText(

                                    text = "Invoice \nHistory",
                                    style = TextStyle(
                                        color = textColor,
                                        fontWeight = FontWeight.Bold
                                    ),

                                    modifier = Modifier
                                        .padding(25.dp)
                                        //.align(Alignment.Center)
                                        .align(Alignment.CenterHorizontally)
                                      //  .align(Alignment.CenterHorizontally)


                                )
//                                Text(text = "Base Amount is ")
//
                            }

//                            Text(
//                                text = userAppointments.size.toString(),
//                                color = Color.Black,
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight.Bold,
//                                modifier = Modifier
//                                    .padding(top = 15.dp)
//                                    .align(alignment = Alignment.CenterHorizontally)
//                            )
                        }


////                    Spacer(modifier = Modifier.padding(30.dp))
//                        OutlinedCard(
//                            colors = CardDefaults.cardColors(
//                                containerColor = Color.White,
//                            ),
//                            onClick = {},
//                            modifier = Modifier
//                                .size(width = 160.dp, height = 100.dp)
//                                .shadow(elevation = 8.dp)
//                        ) {
//
//                            OutlinedCard(
//                                modifier = Modifier
//                                    .padding(4.dp)
//                                    .size(width = 160.dp, height = 30.dp)
//                                    .fillMaxSize()
//                                    .align(alignment = Alignment.CenterHorizontally)
//                                    .align(alignment = AbsoluteAlignment.Right),
//                                colors = CardDefaults.cardColors(
//                                    containerColor = darkb
//                                ),
//
//
//                                ) {
//                                BasicText(
//                                    text = "No of Sold Services",
//                                    style = TextStyle(
//                                        color = Color.White,
//                                        fontWeight = FontWeight.Bold
//                                    ),
//
//                                    modifier = Modifier
//                                        .padding(7.dp)
//                                        .align(Alignment.CenterHorizontally)
//                                        .align(Alignment.CenterHorizontally)
//
//
//                                )
//
//                            }
//                            Text(
//                                text = "1", modifier = Modifier
//                                    .padding(top = 15.dp)
//                                    .align(alignment = Alignment.CenterHorizontally)
//                            )
//                        }

                    }
                }
                Spacer(modifier = Modifier.height(40.dp))

                FooterSection()
                Spacer(modifier = Modifier.height(70.dp))
            }

        }
    }
}



@Composable
fun serviceCard(item: Service, navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // Adjust dimensions based on screen size
    val cardWidth = screenWidth * 1f
    val cardHeight = screenHeight * 0.3f

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
            .padding(30.dp, 10.dp), colors = cardColors(containerColor = Color.White),
        //  colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        onClick = {
            navController.navigate(Route.BookAppointmentScreen)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {




            if (item.serviceName == "shave") {
                Image(
                    painter = painterResource(id = R.drawable.salon2),
                    contentDescription = "",
                    //  modifier = Modifier.fillMaxWidth()
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight * 1f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop

                )
            }
            if (item.serviceName == "Cutting" || item.serviceName == "cuting") {
                Image(painter = painterResource(id = R.drawable.cutting), contentDescription = "",modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight * 1f)
                    .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop)
            }
            if (item.serviceName == "beard treem" || item.serviceName == "Beard Treem") {
                Image(painter = painterResource(id = R.drawable.treemshave), contentDescription = "",modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight * 1f)
                    .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop)
            }
            if (item.serviceName == "beard cut" || item.serviceName == "Beard Cut") {
                Image(painter = painterResource(id = R.drawable.shave), contentDescription = "",modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight * 1f)
                    .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop)
            }
            if (item.serviceName == "face massage" || item.serviceName == "Face Massage") {
                Image(painter = painterResource(id = R.drawable.masaj), contentDescription = "",modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight * 1f)
                    .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop)
            }
            if (item.serviceName == "Small Boy Cut" ) {
                Image(painter = painterResource(id = R.drawable.hearcut2), contentDescription = "",modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight * 1f)
                    .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop)
            }

            if (item.serviceName == "face pack" || item.serviceName == "Face Pack") {
                Image(painter = painterResource(id = R.drawable.facepack), contentDescription = "",modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight * 1f)
                    .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop)
            }
            if (item.serviceName == "face pack charcole" || item.serviceName == "Face Pack Charcole") {
                Image(painter = painterResource(id = R.drawable.facepackcharcore), contentDescription = "",modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight * 1f)
                    .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop)
            }




            // Info section
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = cardHeight * 0.5f) // Position below image
                    .background(color = vilot), // Background color for info
                verticalAlignment = Alignment.Bottom
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row {
                        Text(
                            text = "₹ ",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Purple,
                            fontSize = 22.sp
                        )
                        item.cost?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Purple,
                                fontSize = 22.sp
                            )
                        }
                    }

                    item.serviceName?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                    Row {
                        item.serviceDescription?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 17.sp,
                                color = Color.Black
                            )
                        }

                        item.timestamp?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Medium,
                                fontSize = 17.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}



