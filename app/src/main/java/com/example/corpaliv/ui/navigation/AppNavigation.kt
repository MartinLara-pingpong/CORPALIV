
package com.example.corpaliv.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.corpaliv.ui.screens.HomeScreen
import com.example.corpaliv.ui.screens.RegistroScreen
import com.example.corpaliv.ui.screens.ResumenScreen
import com.example.corpaliv.ui.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(navController: NavHostController) {

    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") {
            HomeScreen(
                onRegistroClick = {
                    navController.navigate("registro")
                }
            )
        }

        composable("registro") {
            RegistroScreen(
                onRegistroClick = {
                    navController.navigate("resumen")
                },
                usuarioViewModel = usuarioViewModel
            )
        }

        composable("resumen") {
            ResumenScreen(
                usuarioViewModel = usuarioViewModel
            )
        }
    }
}
