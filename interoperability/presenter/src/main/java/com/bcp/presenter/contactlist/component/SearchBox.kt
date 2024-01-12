package com.bcp.presenter.contactlist.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBox(onTexChange: (String) -> Unit, textValue: String) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        value = textValue,
        onValueChange = onTexChange
    )
}