package com.example.task7.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.task7.R
import com.example.task7.data.FirebaseUtils
import com.example.task7.dataClasses.LocationData
import com.example.task7.databinding.FragmentMapBinding
import com.example.task7.viewModels.MapViewModel
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
class MapFragment @Inject constructor() : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding

    @Inject
    lateinit var firebaseUtils: FirebaseUtils
    private val DEFAULT_ZOOM = 15
    private val vm: MapViewModel by viewModels()
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationsArray = ArrayList<LocationData>()
    private val USERNAME_TAG = "userName"
    private val DEFAULT_STR = "userName"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map2) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.exitBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_loginFragment)
        }
        binding.timeBtn.setOnClickListener {
            TimeDialogFragment(dialogListener = { vm.dialogResult.value = it }).show(
                childFragmentManager,
                "TAG"
            )
        }
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val userName = arguments?.getString(USERNAME_TAG, DEFAULT_STR).toString()
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