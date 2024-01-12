package com.bcp.presenter.contactlist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bcp.domain.model.ContactModel


@Composable
fun ListContactInteroperability(listContacts: List<ContactModel>, nextScreen: () -> Unit) {
    LazyColumn {
        items(listContacts) {
            Column(
                Modifier
                    .padding(top = 5.dp, bottom = 10.dp, start = 24.dp, end = 24.dp)
                    .clickable {
                        nextScreen()
                    }) {
                Text(text = it.name, modifier = Modifier.padding(top = 5.dp))
                Text(text = it.number, modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                Divider(thickness = 0.5.dp)
            }

        }
    }
}