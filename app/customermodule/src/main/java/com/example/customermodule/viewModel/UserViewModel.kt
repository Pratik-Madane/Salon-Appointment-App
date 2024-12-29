package com.example.salonappcustomer.viewModel

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonappcustomer.FirebaseDB.UsersInfo
import com.example.salonappcustomer.User_Preferences.UserPreferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class UserViewModel : ViewModel() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    private val _usersList = MutableStateFlow<List<UsersInfo>>(emptyList())
    val usersList: StateFlow<List<UsersInfo>> get() = _usersList

    fun registerUser(user: UsersInfo, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            // Create a unique ID for the user
            val userId = database.push().key

            // Check if userId is not null
            if (userId != null) {
                // Create a user instance with the ID
                val userWithId = user.copy(id = userId)

                // Save the user to the database using the unique ID
                database.child(userId).setValue(userWithId)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFailure(it.message ?: "Error") }
            } else {
                onFailure("Failed to generate user ID")
            }
        }
    }


        // LiveData to observe user data
        private val _userLiveData = MutableLiveData<UsersInfo?>()
        val userLiveData: LiveData<UsersInfo?> = _userLiveData

        // LiveData to observe login status
        private val _loginStatus = MutableLiveData<Boolean>()
        val loginStatus: LiveData<Boolean> = _loginStatus



        // Function to log in a user with email and password
        fun loginUser(
            email: String,
            password: String,
            context: Context,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit
        ) {
            //User preference variable
            val userPreferences = UserPreferences(context)
            database.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var userFound = false
                        for (userSnapshot in snapshot.children) {
                            val user = userSnapshot.getValue(UsersInfo::class.java)
                            if (user?.password == password) {
                                //stoare user id
                                userPreferences.setUserId(user.id)
                                _userLiveData.value = user
                                _loginStatus.value = true
                                userFound = true
                                onSuccess()
                                break
                            }
                        }
                        if (!userFound) {
                            _loginStatus.value = false
                            onFailure("Incorrect password")
                        }
                    } else {
                        _loginStatus.value = false
                        onFailure("User not found")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    _loginStatus.value = false
                    onFailure(error.message)
                }
            })
        }


    //private val _userLiveData = MutableLiveData<UsersInfo?>()
    //val userLiveData: LiveData<UsersInfo?> = _userLiveData


    // Function to fetch the user's profile by user ID
    fun getUserProfile(userId: String) {
        database.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(UsersInfo::class.java)
                    _userLiveData.value = user
                } else {
                    _userLiveData.value = null // No data found
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _userLiveData.value = null // Handle any error, for simplicity setting to null
            }
        })
    }
}
