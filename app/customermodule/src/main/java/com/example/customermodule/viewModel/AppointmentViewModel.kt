package com.example.salonappcustomer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonappcustomer.FirebaseDB.Appointment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppointmentViewModel : ViewModel() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Appointments")

    private val _appointmentsList = MutableStateFlow<List<Appointment>>(emptyList())
    val appointmentsList: StateFlow<List<Appointment>> get() = _appointmentsList

    private val _selectedAppointment = MutableStateFlow<Appointment?>(null)
    val selectedAppointment: StateFlow<Appointment?> get() = _selectedAppointment

    private val _appointmentCount = MutableStateFlow(0)
    val appointmentCount: StateFlow<Int> get() = _appointmentCount

    init {
        observeAppointments() // Start observing appointments on initialization
    }

    fun addAppointment(appointment: Appointment, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val appointmentId = database.push().key
        appointmentId?.let {
            val appointmentWithId = appointment.copy(id = it)
            database.child(it).setValue(appointmentWithId)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { error -> onFailure(error.message ?: "Failed to add appointment") }
        } ?: onFailure("Failed to generate appointment ID")
    }

    private fun observeAppointments() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val appointments = snapshot.children.mapNotNull { it.getValue(Appointment::class.java) }
                _appointmentsList.value = appointments
                _appointmentCount.value = appointments.size
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        })
    }

    fun fetchAppointments() {
        observeAppointments() // Fetch and observe appointments
    }

    fun getAppointmentById(appointmentId: String) {
        viewModelScope.launch {
            database.child(appointmentId).get().addOnSuccessListener { snapshot ->
                val appointment = snapshot.getValue(Appointment::class.java)
                _selectedAppointment.value = appointment
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
            }
        }
    }



    // Update an appointment's status and remark fields in Firebase
    fun updateAppointment(appointmentId: String, newStatus: String, newRemark: String, remarkDate: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val updates = mapOf(
            "status" to newStatus,
            "remark" to newRemark,
            "remarkDate" to remarkDate
        )

        database.child(appointmentId).updateChildren(updates)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { error ->
                onFailure(error.message ?: "Failed to update appointment")
            }
    }

    // Delete an appointment by ID
    fun deleteAppointment(appointmentId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        database.child(appointmentId).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { error ->
                onFailure(error.message ?: "Failed to delete appointment")
            }
    }




    fun isSlotAvailable(date: String, time: String, onResult: (Boolean) -> Unit) {
        database.orderByChild("aptdate").equalTo(date).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val appointments = snapshot.children.mapNotNull { it.getValue(Appointment::class.java) }
                val isAvailable = appointments.none { it.apttime == time }
                onResult(isAvailable)
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
                onResult(false) // Treat as unavailable in case of error
            }
        })
    }

}
