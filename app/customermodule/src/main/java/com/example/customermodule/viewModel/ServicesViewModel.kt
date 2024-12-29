
package com.example.salonappcustomer.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonappcustomer.FirebaseDB.Service
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServicesViewModel : ViewModel() {
  //  private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Services")
    private val _servicesList = MutableStateFlow<List<Service>>(emptyList())
    val serviceList: StateFlow<List<Service>> get() = _servicesList

    private val _selectedService = MutableStateFlow<Service?>(null)
    val selectedSevice: StateFlow<Service?> get() = _selectedService


    // New state flow for user count
    private val _serviceCount = MutableStateFlow(0)
    val serviceCount: StateFlow<Int> get() = _serviceCount


    private val database = FirebaseDatabase.getInstance().getReference("Services")

    fun AddServices(service: Service, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val serviceId = database.push().key
        serviceId?.let {
            val serviceWithId = service.copy(id = it)
            database.child(it).setValue(serviceWithId)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { error -> onFailure(error.message ?: "Failed to add service") }
        } ?: onFailure("Failed to generate service ID")
    }





    init {
        observeService() // Start observing users on initialization
    }

    private fun observeService() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val service = snapshot.children.mapNotNull { it.getValue(Service::class.java) }
                _servicesList.value = service // Update the state with the new list of users
                _serviceCount.value = service.size // Update user count
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle potential errors
                error.toException().printStackTrace()
            }
        })
    }





    fun fetchServices() {
        // This function can be left as is, or you can remove it since we're using observeUsers()
        // It's here to maintain your existing structure.
        observeService()
    }



    fun getServiceById(serviceId: String) {
        viewModelScope.launch {
            database.child(serviceId).get().addOnSuccessListener { snapshot ->
                val user = snapshot.getValue(Service::class.java)
                _selectedService.value = user
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
            }
        }
    }





}





