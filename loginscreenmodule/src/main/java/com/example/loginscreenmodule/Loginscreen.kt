package com.example.loginscreenmodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.loginscreenmodule.databinding.LoginScreenBinding
import com.example.task7.presentation.activities.MapActivity
import com.example.task7.presentation.activities.Registration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Loginscreen : AppCompatActivity() {

    private lateinit var binding: LoginScreenBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentMap = Intent(this, MapActivity::class.java)
        val intentRegistration = Intent(this, Registration::class.java)
        auth = Firebase.auth

        binding.signInBtn.setOnClickListener {

            val email = binding.userNameText.text.toString()
            val password = binding.passwordText.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(intentMap)
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("AUTH", "signInWithEmail:success ------------>>>>>> from Module")
                        val user = auth.currentUser
//                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("AUTH", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
//                        updateUI(null)
                    }
                }
        }
        binding.signUpTxt.setOnClickListener {
            startActivity(intentRegistration)

            val email = binding.userNameText.text.toString()
            val password = binding.passwordText.text.toString()

//            auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d("AUTH", "createUserWithEmail:success")
//                        val user = auth.currentUser
//                        Toast.makeText(baseContext, "success",
//                            Toast.LENGTH_SHORT).show()
////                        updateUI(user)
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w("AUTH", "createUserWithEmail:failure", task.exception)
//                        Toast.makeText(baseContext, "failed.",
//                            Toast.LENGTH_SHORT).show()
////                        updateUI(null)
//                    }
//                }
        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if(currentUser != null){
////            reload();
//        }
    }
}