package com.bcp.presenter

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun NewScreen() {
    Column(modifier = Modifier) {
        var textSearchBox by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(value = textSearchBox, onValueChange = {
            textSearchBox = it
        })
    }
}