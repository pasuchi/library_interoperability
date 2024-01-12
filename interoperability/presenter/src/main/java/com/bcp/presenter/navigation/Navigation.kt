package com.bcp.presenter.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bcp.presenter.contactlist.ContactsScreen
import com.bcp.presenter.NewScreen
import com.bcp.presenter.banklist.BankList

@Composable
fun Navigation(componentActivity: ComponentActivity) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "contacts"
    ) {
        composable("contacts") {
            ContactsScreen(
                componentActivity = componentActivity,
                goToSelectBanks = {
                    navController.navigate(Route.selectBanks)
                })
        }
        composable("new") {
            NewScreen()
        }
        composable(route = Route.selectBanks) {
            BankList()
        }
        composable(route = Route.amountPayment) {

        }
    }
}