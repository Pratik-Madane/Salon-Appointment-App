package com.example.salonappcustomer.FirebaseDB

data class Service(
    val id: String = "", // Add ID field
    val serviceName: String = "",
    val serviceDescription: String = "",
    val cost: String = "",
    val imageUrl: String = "",
    val timestamp: String = ""
)