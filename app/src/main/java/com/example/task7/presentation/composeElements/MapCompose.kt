package com.example.task7.presentation.composeElements

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.task7.R
import com.example.task7.composeElements.helpers.LOGIN_TAG
import com.example.task7.dataClasses.LocationData
import com.example.task7.viewModels.MainActivityViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


@Composable
fun map(vm: MainActivityViewModel) {

    vm.getLocations()
    var lastLocation: LatLng? = null
    var cameraPositionState: CameraPositionState? = null
    var cameraPositionStateDefault: CameraPositionState? = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 1f)
    }
    val locations: State<ArrayList<LocationData>> =
        vm.locationsArray.observeAsState(initial = ArrayList<LocationData>())
    if (locations.value.size > 0) {
        lastLocation = LatLng(
            locations.value[locations.value.size - 1].latitude,
            locations.value[locations.value.size - 1].longitude
        )
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(lastLocation, 10f)
        }

    }
    val showDialog: State<Boolean> = vm.showDialog.observeAsState(initial = false)


    Log.d("DatesChaos", locations.value.size.toString())
    if (showDialog.value) {
        dialog(vm)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .clip(
                    RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp)
                )
                .background(Orange)
        ) {
            Text(
                text = stringResource(id = R.string.timeline),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(
                        Alignment.BottomCenter
                    )
                    .padding(bottom = 15.dp),
                textAlign = TextAlign.Center
            )
            Image(painterResource(id = R.drawable.baseline_exit_to_app_24),
                contentDescription = "",
                alignment = Alignment.BottomEnd,
                modifier = Modifier
                    .align(
                        Alignment.BottomEnd
                    )
                    .padding(15.dp)
                    .clickable { vm.currentScreen.value = LOGIN_TAG })
        }
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            cameraPositionState = when (cameraPositionState) {
                null -> cameraPositionStateDefault
                else -> cameraPositionState
            }!!
        ) {
            for (location in locations.value) {
                val myLocation = LatLng(location.latitude, location.longitude)
                Marker(
                    state = MarkerState(position = myLocation),
                    title = "${location.date}, ${location.time}",
                )
            }

        }

        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Grey)
        ) {

            Image(
                painterResource(id = R.drawable.baseline_access_time_filled_24),
                contentDescription = "",
                Modifier
                    .align(Alignment.Center)
                    .clip(
                        RoundedCornerShape(15.dp)
                    )
                    .size(40.dp)
                    .background(
                        Orange
                    )
                    .clickable {
                        vm.showDialog.value = !vm.showDialog.value!!
                    }
            )

        }
    }
}

