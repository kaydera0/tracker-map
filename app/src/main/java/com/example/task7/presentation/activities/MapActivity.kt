package com.example.task7.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.task7.R
import com.example.task7.data.FirebaseUtils
import com.example.task7.dataClasses.LocationData
import com.example.task7.databinding.ActivityMapBinding
import com.example.task7.presentation.fragments.TimeDialogFragment
import com.example.task7.viewModels.MapActivityViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    @Inject
    lateinit var firebaseUtils: FirebaseUtils
    private val DEFAULT_ZOOM = 15
    private val vm: MapActivityViewModel by viewModels()
    private lateinit var binding: ActivityMapBinding
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationsArray = ArrayList<LocationData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map2) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.exitBtn.setOnClickListener {
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
        }
        binding.timeBtn.setOnClickListener {
            TimeDialogFragment(dialogListener = { vm.dialogResult.value = it }).show(
                supportFragmentManager,
                "TAG"
            )
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val userName = intent.extras?.get("userName").toString()
        locationsArray = firebaseUtils.readCloud(userName)

        map = googleMap

        vm.dialogResult.observe(this, Observer {

            map.clear()
            locationsArray = firebaseUtils.readCloud(userName)
            setMarkers(map)
        })
    }

    private fun setMarkers(map: GoogleMap) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            locationsArray = vm.sortedLocations(locationsArray)

            if (locationsArray.isNotEmpty()) {
                for (i in locationsArray) {
                    map.addMarker(
                        MarkerOptions().position(LatLng(i.latitude, i.longitude))
                            .title("date: ${i.date} , time: ${i.time}")
                    )
                }
                val lastLocation = locationsArray.last()
                map.moveCamera(
                    CameraUpdateFactory
                        .newLatLngZoom(
                            LatLng(lastLocation.latitude, lastLocation.longitude),
                            DEFAULT_ZOOM.toFloat()
                        )
                )
            }
        }
    }
}