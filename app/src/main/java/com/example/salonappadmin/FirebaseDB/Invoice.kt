package com.example.salonappadmin.FirebaseDB


data class Invoice(
    val id: String = "",
    val userId: String = "",
    val services: List<Service> = emptyList(),
    val totalCost: String = "",
    val postingDate: String = ""
)
