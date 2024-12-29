package com.example.salonappadmin.Admin_App_UI//package com.example.salonappadmin.App_UI
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.navigation.NavController
//import com.google.firebase.Firebase
//import com.google.firebase.database.database
//
//
//@Composable
//fun Registrion(navController: NavController) {
//
//
//    // Write a message to the database
//    val database = Firebase.database
//    val myRef = database.getReference("Users")
//
//    var first_Name by remember { mutableStateOf("") }
//    var last_Name by remember { mutableStateOf("") }
//    var contact by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var currentTime by remember { mutableStateOf(getCurrentDateTime()) }
//    val context= LocalContext.current
//
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        Image(
//            painter = painterResource(R.drawable.loginbg),
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop
//        )
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .imePadding() // Add this line
//                        .verticalScroll(rememberScrollState()),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                   // verticalArrangement = Arrangement.Center
//
//                ) {
//                    Spacer(modifier = Modifier.height(22.dp))
//
//                        Image(
//                            painter = painterResource(R.drawable.logo),
//                            contentDescription = "",
//                            modifier = Modifier.size(300.dp, 150.dp)
//                        )
//
//                        Spacer(modifier = Modifier.height(16.dp))
//
//                        Text(
//                            text = "Sign Up",
//                            fontSize = 25.sp,
//                            color = Color(0xFFC7663A),
//                            fontWeight = FontWeight.ExtraBold,
//                            modifier = Modifier.padding(4.dp)
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//
//
//
//                        OutlinedTextField(
//                            value = first_Name,
//                            onValueChange = { first_Name = it },
//                            label = {Text(text = "First Name:")},
//                            modifier = Modifier
//                                .fillMaxWidth(0.8F)
//                                .padding(horizontal = 16.dp)
//                        )
//
//                        Spacer(modifier = Modifier.height(16.dp))
//
//                        OutlinedTextField(
//                            value = last_Name,
//                            onValueChange = { last_Name = it },
//                            label = { Text(text = "Last Name:")},
//
//                            modifier = Modifier
//                                .fillMaxWidth(0.8F)
//                                .padding(horizontal = 16.dp)
//                        )
//
//                        Spacer(modifier = Modifier.height(16.dp))
//
//                        OutlinedTextField(
//                            value = contact,
//                            onValueChange = { contact = it },
//                            label = { Text(text = "Contact Number:")},
//                            modifier = Modifier
//                                .fillMaxWidth(0.8F)
//                                .padding(horizontal = 16.dp)
//                        )
//
//                        Spacer(modifier = Modifier.height(16.dp))
//
//                        OutlinedTextField(
//                            value = email,
//                            onValueChange = { email = it },
//                            label = { Text(text = "Email:")},
//                            modifier = Modifier
//                                .fillMaxWidth(0.8F)
//                                .padding(horizontal = 16.dp)
//                        )
//
//                   // val sanitizedEmailKey = email.replace(".", ",")  // Replace "." with ","
//                   // Now you can use sanitizedEmailKey as a key in Realtime Database
//
//
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                        OutlinedTextField(
//                            value = password,
//                            onValueChange = { password = it },
//                            label = {Text(text = "Password:")},
//                            modifier = Modifier
//                                .fillMaxWidth(0.8F)
//                                .padding(horizontal = 16.dp)
//                        )
//
//                        Spacer(modifier = Modifier.height(25.dp))
//
//                        val gradient = Brush.horizontalGradient(
//                            colors = listOf(Color(0xFFC97803), Color.White)
//                        )
//
//                        val context = LocalContext.current
//                        RegistrationGradientButton(
//                            text = "Submit",
//                            gradient = gradient,
//                            onClick = { if(first_Name.isNotEmpty() && last_Name.isNotEmpty() && contact.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && currentTime.isNotEmpty()){
//
//                                val userInfo= UsersInfo(first_Name,last_Name,contact.toLong(),email,password,currentTime)
//                                myRef.child(email).setValue(userInfo).addOnSuccessListener {
//                                    first_Name=""
//                                    last_Name=""
//                                    contact=""
//                                    email=""
//                                    password=""
//                                    currentTime=""
//                                    Toast.makeText(context,"Registration Successful",Toast.LENGTH_SHORT).show()
//                                }.addOnFailureListener{
//                                    Toast.makeText(context,"Registration Fail,$it",Toast.LENGTH_SHORT).show()
//                                }
//
//                            }else{
//                               Toast.makeText(context, "Plese Fill the all Details",Toast.LENGTH_SHORT).show()
//                            }
//                            }
//                        )
//
//                        Spacer(modifier = Modifier.height(6.dp))
//
//                        Row(
//                            modifier = Modifier.fillMaxSize(),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Center
//                        ) {
//
//                            Text(
//                                text = "Already have an account?", fontSize = 16.sp,
//                                 color = Color.Black
//                            )
//                            TextButton(onClick = {navController.navigate(Route.Login)}) {
//                                Text(text = "Sign In", fontSize = 16.sp,
//                                    fontWeight = FontWeight.Bold,color = Color.Blue)
//                          //  }
//                        }
//                    }
//                }
//            }
//        }
//
//@Composable
//fun RegistrationGradientButton(text: String, gradient: Brush, onClick: () -> Unit) {
//    Button(
//        onClick = onClick,
//        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
//        contentPadding = PaddingValues(),
//        modifier = Modifier.padding(20.dp,0.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .background(gradient)
//                .fillMaxWidth(0.8F)
//                .height(52.dp)
//                .padding(horizontal = 16.dp, vertical = 8.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = text, color = Color.White)
//        }
//    }
//}
//
//
//// get currentDate & currentTime Function
//fun getCurrentDateTime(): String {
//    val calendar = Calendar.getInstance()
//    val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault()) // Date and 12-hour format with AM/PM
//    return dateFormat.format(calendar.time)
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
