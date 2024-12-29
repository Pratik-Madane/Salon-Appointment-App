package com.example.salonappadmin.Admin_App_UI

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salonappadmin.FirebaseDB.Service
import com.example.salonappadmin.FirebaseDB.UsersInfo
import com.example.salonappadmin.R

@Composable
fun EditServiceScreen(service: Service,onSave: (Service) -> Unit){

    var serviceName by remember { mutableStateOf(service.serviceName) }
    var serviceDescription by remember { mutableStateOf(service.serviceDescription) }
    var cost by remember { mutableStateOf(service.cost) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(Uri.parse(service.imageUrl)) }
    var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current
    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            selectedImageUri = uri
            uri?.let {
                bitmapImage = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(60.dp, 110.dp)
            .imePadding()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Service",
            fontSize = 24.sp,
            color = Color(0xFFFF5722) // Orange color
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Salon Services:",
            fontSize = 18.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = serviceName,
            onValueChange = { serviceName = it },
            label = { Text("Service Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = serviceDescription,
            onValueChange = { serviceDescription = it },
            label = { Text("Service Description") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = cost,
            onValueChange = { cost = it },
            label = { Text("Cost") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Service Picture:",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 0.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Display selected or default image
            if (bitmapImage != null) {
                Image(
                    bitmap = bitmapImage!!.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.LightGray)
                        .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.imagegallery),
                        contentDescription = "Image select from gallery"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AB7F0),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
                ),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .height(40.dp)
            ) {
                Text(
                    text = "Select Image",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        val gradient = Brush.horizontalGradient(
            colors = listOf(Color(0xFFC97803), Color.White)
        )

        GradientButton(
            text = "Save",
            gradient = gradient,
            onClick = {
                // Check if an image has been selected, handle upload or URI conversion
                val updatedService = service.copy(
                    serviceName = serviceName,
                    serviceDescription = serviceDescription,
                    cost = cost,
                    imageUrl = selectedImageUri?.toString() ?: service.imageUrl, // Use existing image URL if no new one
                    timestamp = getCurrentDateTime()
                )

                onSave(updatedService)
                Toast.makeText(context, "Service updated successfully", Toast.LENGTH_SHORT).show()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "© ONE CUT'S HAIR AND BEAUTY STUDIO, Sangamner.",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
