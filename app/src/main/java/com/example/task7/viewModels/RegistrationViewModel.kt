package com.example.task7.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {

    val signUpIsEnable = MutableLiveData(false)


    fun registration(user: String, password: String) {
//        val a = Context()
//        val auth = Firebase.auth
//                    auth.createUserWithEmailAndPassword(user, password)
//                .addOnCompleteListener(a) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//
//                    } else {
//                        // If sign in fails, display a message to the user.
//
////                        updateUI(null)
//                    }
//                }
    }
}