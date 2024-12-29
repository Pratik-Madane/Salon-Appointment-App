package com.example.salonappadmin.Admin_App_UI

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salonapp_admin.ui.theme.darkb1
import com.example.salonappadmin.R

@Composable
fun Admin_Profile() {


    Column(modifier = Modifier
        .padding(0.dp, 100.dp, 0.dp, 0.dp)
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    darkb1, Color.White
                )
            )
        ) ){
        Row(
            Modifier
                .border(1.dp, color = Color.Black)
                .fillMaxWidth()
                .height(67.dp)) {
            Text(
                text = "My Profile",
                //  style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(10.dp,20.dp),
                //.shadow(elevation = 8.dp, ambientColor = Color.Red),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Column(modifier = Modifier
            .border(1.dp, color = Color.Black)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)){

//
//            Button(
//                onClick = { /*TODO*/ },
//                Modifier.padding(10.dp, 4.dp, 0.dp, 4.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = darkb,
//                    contentColor = Color.White
//                ),
//                shape = RoundedCornerShape(7.dp),
//            ){
//                Text(text = "Personal Details")
//            }
            // Image(painter = painterResource(id = R.drawable.), contentDescription = )


            //  Spacer(modifier = Modifier.height(15.dp))

            Column(
                //verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(30.dp, 0.dp)
                    .fillMaxHeight()
            ) {





                //User preference variable
               // val userPreferences = UserPreferences(context)
               // val userId = userPreferences.getUserId()

               // for ( data in AgentList )
               // {
                  //  if(userId==data.a_id){


                        Column ( horizontalAlignment = Alignment.CenterHorizontally){


                            Image(
                                painter = painterResource(id = R.drawable.profilepicture), // Reference your drawable here
                                contentDescription = "Profile Picture",
                                modifier = Modifier.size(150.dp) // Set size for the icon
                            )


                                Text(
                                    text = "Admin",
                                    //style = MaterialTheme.typography.headlineSmall,

                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 25.sp,
                                    color = Color.Black
                                )

                        }
                        Spacer(modifier = Modifier.height(17.dp))





                        Text(
                            text = " Mobile Number:   ",
                            //style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp
                        )

                        //and its a second way of accees a Agent data  because its type is a null

                        Text(
                            text = "1234567890",
                            //style = MaterialTheme.typography.headlineSmall,

                            fontWeight = FontWeight.Thin,
                            fontSize = 17.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(15.dp))





                        Text(
                            text = " date of Birth:",
                            //style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp
                        )

                        Text(
                            text = "2-August-2000"
                            ,
                            //style = MaterialTheme.typography.headlineSmall,

                            fontWeight = FontWeight.Thin,
                            fontSize = 17.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(15.dp))


                        Text(
                            text = "Email:",
                            //style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp
                        )

                        Text(
                            text = "admin@gmail.com",
                            //style = MaterialTheme.typography.headlineSmall,

                            fontWeight = FontWeight.Thin,
                            fontSize = 17.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(15.dp))



//                                Text(
//                                    text = " Password:",
//                                    //style = MaterialTheme.typography.headlineSmall,
//                                    fontWeight = FontWeight.SemiBold,
//                                    fontSize = 17.sp
//                                )
//
//                                Text(
//                                    text = data!!.password!!,
//                                    //style = MaterialTheme.typography.headlineSmall,
//
//                                    fontWeight = FontWeight.Thin,
//                                    fontSize = 17.sp,
//                                    color = Color.Gray
//                                )
//
//                                Spacer(modifier = Modifier.height(15.dp))


                        Text(
                            text = " Adress:   ",
                            //style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp
                        )

                        Text(
                            text = "Sangamner",
                            //style = MaterialTheme.typography.headlineSmall,

                            fontWeight = FontWeight.Thin,
                            fontSize = 17.sp,
                            color = Color.Gray
                        )

                        //for loop break



                }

            }



        }
    }
