package com.bcp.interoperability

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.loader.app.LoaderManager
import com.bcp.interoperability.ui.theme.InteroperabilityTheme
import com.bcp.presenter.ContactsScreen
import com.bcp.presenter.ContactsViewModel
import com.bcp.presenter.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InteroperabilityTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(componentActivity = this@MainActivity)

                    /*val vm: ContactsViewModel = hiltViewModel()
                    ContactsScreen(
                        onTexChange = vm::getTextInput,
                        listContacts = vm.result.collectAsState().value,
                        textValue = vm.searchText.collectAsState().value,
                        initLoaderManager = {
                            LoaderManager.getInstance(this).restartLoader(0, null, vm.loaderManager)
                        }
                    )*/
                }
            }
        }
    }
}
