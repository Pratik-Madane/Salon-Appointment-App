package com.example.salonappadmin.Navigate_Screens


//@Serializable
//object Splash
//
//@Serializable
//object Login
//
//@Serializable
//object Registration

object Route{
    var Splash="Splash"
    var Login="Login"
    var Registration="Registration"
    var MainScreen="MainScreen"
    var Home_Screen="Home_Screen"
    var Customer_Info="Customer_Info"
    //var editUser="editUser{}"
     val editUser = "editUser/{userId}" // Define the route with a placeholder
    var Admin_Profile="Admin_Profile"
    var AddServicesScreen="AddServicesScreen"
    var ShowServicesScreen="ShowServicesScreen"
    var AllAppointmentScreen="AllAppointmentScreen"
    var singleAppoinmentScreen="singleAppoinmentScreen"
    var AssignServicesScreen="AssignServicesScreen"
    var InvoiceListScreen="InvoiceListScreen"
  //  var InvoiceScreen="InvoiceScreen"
     val InvoiceScreen = "invoice_screen/{userId}/{invoiceId}"
}