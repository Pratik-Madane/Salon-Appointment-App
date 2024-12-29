//package com.example.salonappadmin.viewModel
//
//
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.salonappadmin.FirebaseDB.Invoice
//import com.example.salonappadmin.FirebaseDB.Service
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import java.text.SimpleDateFormat
//import java.util.*
//
//
//class InvoiceViewModel : ViewModel() {
//    private val database: DatabaseReference =
//        FirebaseDatabase.getInstance().getReference("Invoices")
//
//    private val _invoiceList = MutableStateFlow<List<Invoice>>(emptyList())
//    val invoiceList: StateFlow<List<Invoice>> get() = _invoiceList
//
//    private fun generateInvoiceId(): String {
//        return System.currentTimeMillis().toString() // Only numeric ID
//    }
//
//    private fun getCurrentDate(): String {
//        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//        return formatter.format(Date())
//    }
//
//    fun addInvoice(
//        userId: String,
//        services: List<Service>,
//        totalCost: String,
//        onSuccess: () -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        val invoiceId = generateInvoiceId()
//        val postingDate = getCurrentDate()
//        val invoice = Invoice(
//            id = invoiceId,
//            userId = userId,
//            services = services,
//            totalCost = totalCost,
//            postingDate = postingDate
//        )
//
//        viewModelScope.launch {
//            database.child(invoiceId).setValue(invoice)
//                .addOnSuccessListener { onSuccess() }
//                .addOnFailureListener { error ->
//                    onFailure(
//                        error.message ?: "Failed to add invoice"
//                    )
//                }
//        }
//    }
//
//
//    // Fetch invoices from Firebase
//    fun fetchInvoices() {
//        viewModelScope.launch {
//            database.get().addOnSuccessListener { snapshot ->
//                val invoices = snapshot.children.mapNotNull { it.getValue(Invoice::class.java) }
//                _invoiceList.value = invoices
//            }.addOnFailureListener { exception ->
//                exception.printStackTrace()
//            }
//        }
//    }
//
//
//
//    // Update an existing invoice
//    fun updateInvoice(invoiceId: String, updatedInvoice: Invoice, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
//        viewModelScope.launch {
//            database.child(invoiceId).setValue(updatedInvoice)
//                .addOnSuccessListener { onSuccess() }
//                .addOnFailureListener { error -> onFailure(error.message ?: "Failed to update invoice") }
//        }
//    }
//
//    // Delete an invoice by its ID
//    fun deleteInvoice(invoiceId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
//        viewModelScope.launch {
//            database.child(invoiceId).removeValue()
//                .addOnSuccessListener { onSuccess() }
//                .addOnFailureListener { error -> onFailure(error.message ?: "Failed to delete invoice") }
//        }
//    }
//}






package com.example.salonappcustomer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonappcustomer.FirebaseDB.Invoice
import com.example.salonappcustomer.FirebaseDB.Service
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class InvoiceViewModel : ViewModel() {
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Invoices")

    private val _invoiceList = MutableStateFlow<List<Invoice>>(emptyList())
    val invoiceList: StateFlow<List<Invoice>> get() = _invoiceList

    // Initialize a real-time listener for invoices
    private val invoiceListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val invoices = snapshot.children.mapNotNull { it.getValue(Invoice::class.java) }
            _invoiceList.value = invoices
        }

        override fun onCancelled(error: DatabaseError) {
            error.toException().printStackTrace()
        }
    }

    init {
        // Start listening for real-time invoice updates
        database.addValueEventListener(invoiceListener)
    }

    // Generates a unique numeric invoice ID
    private fun generateInvoiceId(): String = System.currentTimeMillis().toString()

    // Gets the current date in the desired format
    private fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(Date())
    }

    // Adds a new invoice to Firebase
    fun addInvoice(
        userId: String,
        services: List<Service>,
        totalCost: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val invoiceId = generateInvoiceId()
        val postingDate = getCurrentDate()
        val invoice = Invoice(
            id = invoiceId,
            userId = userId,
            services = services,
            totalCost = totalCost,
            postingDate = postingDate
        )

        viewModelScope.launch {
            database.child(invoiceId).setValue(invoice)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { error ->
                    onFailure(error.message ?: "Failed to add invoice")
                }
        }
    }

    // Updates an existing invoice
    fun updateInvoice(
        invoiceId: String,
        updatedInvoice: Invoice,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            database.child(invoiceId).setValue(updatedInvoice)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { error ->
                    onFailure(error.message ?: "Failed to update invoice")
                }
        }
    }

    // Deletes an invoice by its ID
    fun deleteInvoice(
        invoiceId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            database.child(invoiceId).removeValue()
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { error ->
                    onFailure(error.message ?: "Failed to delete invoice")
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Remove the Firebase listener to prevent memory leaks
        database.removeEventListener(invoiceListener)
    }
    // Fetch invoices from Firebase
    fun fetchInvoices() {
        viewModelScope.launch {
            database.get().addOnSuccessListener { snapshot ->
                val invoices = snapshot.children.mapNotNull { it.getValue(Invoice::class.java) }
                _invoiceList.value = invoices
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
            }
        }
    }
}
