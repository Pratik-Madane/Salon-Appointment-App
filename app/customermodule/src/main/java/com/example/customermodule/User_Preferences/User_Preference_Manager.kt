//package com.example.salonappadmin.User_Preferences
//
//import android.content.Context
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.stringPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//
//
//private val Context.dataStore by preferencesDataStore("user_preferences")
//class User_Preference_Manager(private val context:Context) {
//
//companion object{
//    private val USER_ID_KEY= stringPreferencesKey("user_id")
//}
//
//     suspend fun saveUserId(userId:String){
//        context.dataStore.edit {
//            it[USER_ID_KEY]=userId
//        }
//    }
//
//        val userId:Flow<String?> = context.dataStore.data.map{
//        it[USER_ID_KEY]
//    }
//
//     suspend fun clearUserId(){
//        context.dataStore.edit {
//            it.remove(USER_ID_KEY)
//        }
//    }
//
//
//
//}