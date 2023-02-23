package com.example.task7.data

import android.util.Log
import com.example.task7.dataClasses.LocationData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseUtils {

    val db = Firebase.firestore

    fun readCloud(collectionName: String): ArrayList<LocationData> {
        val locationsArray = ArrayList<LocationData>()

        db.collection(collectionName)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val location = LocationData(
                        document.data.get("longitude").toString().toDouble(),
                        document.data.get("latitude").toString().toDouble(),
                        document.data.get("date").toString(),
                        document.data.get("time").toString()
                    )
                    locationsArray.add(location)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("DATABASE_TAG", "Error getting documents.", exception)
            }
        return locationsArray
    }
}