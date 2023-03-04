package com.example.task7

import com.example.task7.dataClasses.LocationData
import com.example.task7.viewModels.MapViewModel
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.ArrayList

class MapViewModelTest {

    private val mapViewModel = MapViewModel()
    @Test
    fun testSorted() {

        val testArr = ArrayList<LocationData>()
        testArr.add(LocationData(1.0,1.0,"04-03-2023","00:00:00"))      //today
        testArr.add(LocationData(2.0,2.0,"03-03-2023","01:00:00"))      //yesterday
        val sortedArr = mapViewModel.sortedLocations(testArr)
        assertEquals(testArr.size - 1, sortedArr.size)
    }
}