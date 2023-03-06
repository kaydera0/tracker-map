package com.example.task7.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.task7.R
import com.example.task7.presentation.composeElements.ScreenContainer
import com.example.task7.viewModels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityCompose : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm: MainActivityViewModel by viewModels()

        setContent {
            ScreenContainer(vm)
        }
        vm.signInError.observe(this, Observer {
            if (it) {
                Toast.makeText(
                    this, getString(R.string.authentication_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}