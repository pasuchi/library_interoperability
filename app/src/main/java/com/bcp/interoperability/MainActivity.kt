package com.bcp.interoperability

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.bcp.interoperability.ui.theme.InteroperabilityTheme
import com.bcp.presenter.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InteroperabilityTheme {
                //val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    /*NavHost(navController = navController, startDestination = Route.listContacts) {
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
                    }*/

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
