package com.example.task7.presentation.composeElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.task7.viewModels.MainActivityViewModel

@Composable
fun dialog(vm: MainActivityViewModel) {
    var dialogOpen = remember {
        mutableStateOf(true)
    }

    if (dialogOpen.value) {
        AlertDialog(
            onDismissRequest = {
                dialogOpen.value = false
            },
            buttons = {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(Grey), verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            vm.dialogResult.value = 1
                            dialogOpen.value = false
                        }, Modifier.padding(10.dp), colors = ButtonDefaults.buttonColors(
                            Orange
                        )
                    ) {
                        Text(text = "1")
                    }
                    Button(
                        onClick = {
                            vm.dialogResult.value = 3
                            dialogOpen.value = false
                        },
                        Modifier.padding(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            Orange
                        )
                    ) {
                        Text(text = "3")

                    }
                    Button(
                        onClick = {
                            vm.dialogResult.value = 7
                            dialogOpen.value = false
                        },
                        Modifier.padding(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            Orange
                        )
                    ) {
                        Text(text = "7")

                    }
                }
            },
            title = {
                Text(text = "Time period")
            },
            text = {
                Text(text = "Choose time period in days")
            },
            modifier = Modifier // Set the width and padding
                .fillMaxWidth()
                .padding(32.dp),
            shape = RoundedCornerShape(5.dp),
            backgroundColor = Color.White,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }
}