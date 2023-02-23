package com.example.task7.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task7.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentMap = Intent(this, MapActivity::class.java)
        val intentRegistration = Intent(this, Registration::class.java)
        auth = Firebase.auth

        binding.signInBtn.setOnClickListener {

            val userName = binding.userNameText.text.toString()
            val password = binding.passwordText.text.toString()
            auth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        intentMap.putExtra("userName", userName)
                        startActivity(intentMap)
                        Log.d("AUTH", "signInWithEmail:success")
                    } else {
                        Log.w("AUTH", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
        binding.signUpTxt.setOnClickListener {
            startActivity(intentRegistration)
        }
    }
}