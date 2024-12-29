package com.example.salonappcustomer.FirebaseDB

import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.salonappcustomer.App_UI.getCurrentDateTime
import org.checkerframework.checker.units.qual.Time
import java.net.PasswordAuthentication

data class UsersInfo(
    val id: String = "", // Add ID field
    val firstName: String = "",
    val lastName: String = "",
    val contact: String = "",
    val email: String = "",
    val password: String = "",
    val timestamp: String = getCurrentDateTime()
)
