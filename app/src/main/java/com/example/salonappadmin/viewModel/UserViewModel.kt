package com.example.salonappadmin.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonappadmin.FirebaseDB.UsersInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel_Admin : ViewModel() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
    private val _usersList = MutableStateFlow<List<UsersInfo>>(emptyList())
    val usersList: StateFlow<List<UsersInfo>> get() = _usersList

    private val _selectedUser = MutableStateFlow<UsersInfo?>(null)
    val selectedUser: StateFlow<UsersInfo?> get() = _selectedUser

    // New state flow for user count
    private val _userCount = MutableStateFlow(0)
    val userCount: StateFlow<Int> get() = _userCount

    init {
        observeUsers() // Start observing users on initialization
    }

    private fun observeUsers() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.children.mapNotNull { it.getValue(UsersInfo::class.java) }
                    .sortedByDescending { it.timestamp } // Sort users by timestamp in descending order

                _usersList.value = users // Update the state with the sorted list of users
                _userCount.value = users.size // Update user count
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle potential errors
                error.toException().printStackTrace()
            }
        })
    }

    fun fetchUsers() {
        // This function can be left as is, or you can remove it since we're using observeUsers()
        // It's here to maintain your existing structure.
        observeUsers()
    }

    fun getUserById(userId: String) {
        viewModelScope.launch {
            database.child(userId).get().addOnSuccessListener { snapshot ->
                val user = snapshot.getValue(UsersInfo::class.java)
                _selectedUser.value = user
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
            }
        }
    }

    fun updateUser(userId: String, updatedUser: UsersInfo) {
        viewModelScope.launch {
            // Use the userId to update the specific user in the database
            database.child(userId).setValue(updatedUser)
                .addOnSuccessListener {
                    // Reset the selected user after updating
                    _selectedUser.value = null
                }
                .addOnFailureListener { exception ->
                    exception.printStackTrace()
                }
        }
    }

    // New deleteUser method
    fun deleteUser(userId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            database.child(userId).removeValue()
                .addOnSuccessListener {
                    fetchUsers() // Refresh the user list after deletion
                    onSuccess() // Call onSuccess when deletion is successful
                }
                .addOnFailureListener { exception ->
                    onFailure(exception.message ?: "Error deleting user")
                }
        }
    }
}
