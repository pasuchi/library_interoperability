package com.bcp.interoperability

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.loader.app.LoaderManager
import com.bcp.interoperability.ui.theme.InteroperabilityTheme
import com.bcp.presenter.ListConstactsScreen
import com.bcp.presenter.ListContactsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InteroperabilityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val vm: ListContactsViewModel = hiltViewModel()
                    ListConstactsScreen(
                        onTexChange = vm::getTextInput,
                        listContacts = vm.result.collectAsState().value,
                        textValue = vm.searchText.collectAsState().value,
                        initLoaderManager = {
                            LoaderManager.getInstance(this).restartLoader(0, null, vm.loaderManager)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InteroperabilityTheme {
        Greeting("Android")
    }
}