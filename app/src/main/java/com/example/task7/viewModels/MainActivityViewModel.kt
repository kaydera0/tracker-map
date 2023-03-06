package com.example.task7.viewModels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task7.data.FirebaseUtils
import com.example.task7.dataClasses.LocationData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val firebaseUtils: FirebaseUtils
) : ViewModel() {

    var locationsArray = MutableLiveData<ArrayList<LocationData>>()
    val isLoad = MutableLiveData(false)
    val showDialog = MutableLiveData(false)
    val currentScreen = MutableLiveData("login")
    val currentUserName = MutableLiveData("")
    val signInError = MutableLiveData(false)
    val dialogResult = MutableLiveData<Int>(1)
    val msInDay = 1000 * 60 * 60 * 24

    @SuppressLint("SimpleDateFormat", "SuspiciousIndentation")
    fun sortedLocations(
        locationsArray: ArrayList<LocationData>
    ): ArrayList<LocationData> {
        val days = dialogResult.value
        val currentDate = Date()
        val targetDate = Date(Date().time - msInDay * days!!)
        val sortedArray = ArrayList<LocationData>()

        for (i in locationsArray) {
            val date: Date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(i.date + " " + i.time)!!

            if (date in targetDate..currentDate) {
                sortedArray.add(i)
            }
        }
        return sortedArray
    }

    fun getLocations() {
        if (currentUserName.value?.isNotEmpty() == true) {
            viewModelScope.launch {
            val result = firebaseUtils.readCloud(currentUserName.value!!)
            delay(1000)
            locationsArray.value = sortedLocations(result)}
        } else {
            locationsArray.value?.add(LocationData(1.35, 103.87, "01-01-2000", "00:00:00"))
        }
    }
}