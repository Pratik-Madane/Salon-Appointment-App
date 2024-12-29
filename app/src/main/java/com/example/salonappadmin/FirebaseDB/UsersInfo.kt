package com.example.salonappadmin.FirebaseDB

import com.example.salonappadmin.Admin_App_UI.getCurrentDateTime


data class UsersInfo(
    val id: String = "", // Add ID field
    val firstName: String = "",
    val lastName: String = "",
    val contact: String = "",
    val email: String = "",
    val password: String = "",
    val timestamp: String = ""
)
