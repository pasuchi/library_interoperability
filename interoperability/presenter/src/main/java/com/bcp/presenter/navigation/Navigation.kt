package com.bcp.presenter.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.loader.app.LoaderManager
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bcp.presenter.banklist.BankListViewModel
import com.bcp.presenter.banklist.BanksScreen
import com.bcp.presenter.contactlist.ContactsScreen
import com.bcp.presenter.contactlist.ContactsViewModel

@Composable
fun Navigation(componentActivity: ComponentActivity) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.listContacts
    ) {
        composable(Route.listContacts) {
            val vm: ContactsViewModel = hiltViewModel()

            ContactsScreen(
                onUIEvent = vm::onEvent,
                uiState = vm.uiState.collectAsStateWithLifecycle().value,
                initLoaderManager = {
                    LoaderManager.getInstance(componentActivity)
                        .initLoader(0, null, vm.loaderManager)
                },
                nextScreen = { route ->
                    navController.navigate(route)
                }
            )
        }
        composable(route = Route.selectBanks) {
            val vm: BankListViewModel = hiltViewModel()

            BanksScreen(vm.uiState.collectAsStateWithLifecycle().value, vm::onEvent)
        }
        composable(route = Route.amountPayment) {

        }
    }
}