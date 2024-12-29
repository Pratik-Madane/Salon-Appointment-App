package com.example.salonappadmin.Admin_App_UI




import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.salonappadmin.User_Preferences.UserPreferences
import com.example.salonappadmin.Navigate_Screens.Route
import com.example.salonappadmin.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login_Page(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
            Image(
                painter = painterResource(R.drawable.loginbg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .imePadding() // Add this line
                        .verticalScroll(rememberScrollState()), // Add this line
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "",
                        modifier = Modifier.size(300.dp, 150.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    Text(text = "Sign In",
                        fontSize = 25.sp,
                        color = Color(0xF3DB8403),
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(4.dp) 
                    )

                    OutlinedTextField(
                        value = email,
                        modifier = Modifier,
                        label = { Text(text = "email") },
                        onValueChange = { email = it },
                        textStyle = TextStyle.Default,
                        shape = RoundedCornerShape(30.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.Black)
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    var passwordvisible by remember { mutableStateOf(false) }
                    val icon = if (passwordvisible){
                        painterResource(id = R.drawable.baseline_visibility_24)
                    }else{
                        painterResource(id = R.drawable.baseline_visibility_off_24)
                    }


                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(text = "Password") },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordvisible = !passwordvisible
                            },)
                            {
                                Icon(painter =icon,
                                    contentDescription = "")
                            }
                        },

                        visualTransformation = if (passwordvisible) VisualTransformation.None
                        else PasswordVisualTransformation(),

                        textStyle = TextStyle.Default,
                        shape = RoundedCornerShape(30.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.Black)
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    val gradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFC97803), Color.White)
                    )

                    val context = LocalContext.current

                    GradientButton(
                        text = "Submit",
                        gradient = gradient,
                        onClick = {

                            //User preference variable
                            val userPreferences = UserPreferences(context)


                            if (email == "admin@gmail.com" && password == "11111") {

//after the user successfully logs in, update the login state and stoare userId  in SharedPreferences.
                                userPreferences.setLoggedIn(true)
                               // userPreferences.setUserId(email)

                                navController.navigate(Route.MainScreen)
                                {
                                    popUpTo(Route.Login) { inclusive = true }
                                }
                                Toast.makeText(context, "Sign In Success", Toast.LENGTH_SHORT)
                                    .show()
                                //  Agent= data

                            } else {
                                Toast.makeText(
                                    context,
                                    "Invalid User_Name And Password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
Spacer(modifier =Modifier.height(10.dp) )

                }
            }
        }




//Login Button
@Composable
fun GradientButton(
    text: String,
    gradient: Brush,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .size(width = 265.dp, height = 52.dp) // Set custom width and height
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, color = Color.White)
        }
    }
}

