package com.example.task7.viewModels

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task7.data.FirebaseUtils
import com.example.task7.dataClasses.LocationData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MapActivityViewModel @Inject constructor(val firebaseUtils: FirebaseUtils) : ViewModel() {

    val dialogResult = MutableLiveData<Int>(1)
    val locationsArray = MutableLiveData<ArrayList<LocationData>>()
    val msInDay = 1000 * 60 * 60 * 24

    @SuppressLint("SimpleDateFormat", "SuspiciousIndentation")
    fun sortedLocations(
        locationsArray:ArrayList<LocationData>
    ): ArrayList<LocationData> {
//        val locationsArray = firebaseUtils.readCloud(userName)
        val days = dialogResult.value
        val currentDate = Date()

        val targetDate = Date(Date().time - msInDay * days!!)
        Log.d("DatesChaos", "${targetDate} -----.....----- target date")


        val sortedArray = ArrayList<LocationData>()


        for (i in locationsArray) {

            val date: Date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(i.date + " " + i.time)!!
            if (date in targetDate..currentDate) {
                sortedArray.add(i)
            }


        }
        Log.d("DatesChaos", "${sortedArray.size} -----.....----- sortedArray.size")
        return sortedArray

    }
}