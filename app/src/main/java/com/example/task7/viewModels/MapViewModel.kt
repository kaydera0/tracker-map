package com.example.task7.viewModels

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task7.dataClasses.LocationData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

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
}