package com.example.task7.presentation.composeElements

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import com.example.task7.composeElements.helpers.LOGIN_TAG
import com.example.task7.composeElements.helpers.MAP_TAG
import com.example.task7.composeElements.helpers.REGISTRATION_TAG
import com.example.task7.viewModels.MainActivityViewModel



@Composable
fun ScreenContainer(vm: MainActivityViewModel){

    val screen: State<String> = vm.currentScreen.observeAsState(initial = LOGIN_TAG)

    when(screen.value){
        LOGIN_TAG -> login(vm)
        REGISTRATION_TAG -> registration(vm)
        MAP_TAG -> map(vm)
    }
}