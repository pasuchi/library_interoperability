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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bcp.interoperability.navigation.Route
import com.bcp.interoperability.ui.theme.InteroperabilityTheme
import com.bcp.presenter.banklist.BankList
import com.bcp.presenter.banklist.BankListViewModel
import com.bcp.presenter.contactlist.ListConstactsScreen
import com.bcp.presenter.contactlist.ListContactsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InteroperabilityTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = Route.listContacts) {
                        composable(route = Route.listContacts) {
                            val vm: ListContactsViewModel = hiltViewModel()
                            ListConstactsScreen(
                                onTexChange = vm::getTextInput,
                                listContacts = vm.result.collectAsState().value,
                                textValue = vm.searchText.collectAsState().value,
                                initLoaderManager = {
                                    LoaderManager.getInstance(this@MainActivity)
                                        .restartLoader(0, null, vm.loaderManager)
                                },
                                nextScreen = {
                                    navController.navigate(Route.selectBanks)
                                }
                            )
                        }
                        composable(route = Route.selectBanks) {
                            val vm: BankListViewModel = hiltViewModel()
                            BankList()

                        }
                        composable(route = Route.amountPayment) {

                        }
                    }

                }
            }
        }
    }
}
