package com.bcp.presenter.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bcp.presenter.ContactsScreen
import com.bcp.presenter.NewScreen

@Composable
fun Navigation(componentActivity: ComponentActivity) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "contacts"
    ) {
        composable("contacts") {
            ContactsScreen(componentActivity = componentActivity)
        }
        composable("new") {
            NewScreen()
        }
    }
}