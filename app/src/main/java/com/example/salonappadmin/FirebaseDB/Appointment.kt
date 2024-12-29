package com.example.salonappadmin.FirebaseDB

data class Appointment(
    val id:String="",
    val aptdate: String = "",
    val apttime: String = "",
    val message: String = "",
    val custumerId:String="",
    val aptNumber:String="",
    val timestamp: String = "",
    val remark:String="",
    val status:String="",
    val remarkDate:String=""
)