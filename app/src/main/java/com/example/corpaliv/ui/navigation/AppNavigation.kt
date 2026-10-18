
package com.example.corpaliv.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.corpaliv.ui.screens.ColaboracionScreen
import com.example.corpaliv.ui.screens.HomeScreen
import com.example.corpaliv.ui.screens.LoginScreen
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
                },
                onLoginClick = {
                    navController.navigate("login")
                },
                onColaborarClick = {
                    navController.navigate("colaboracion")
                }
            )
        }

        composable("registro") {
            RegistroScreen(
                onRegistroClick = {
                    navController.navigate("resumen") {
                        popUpTo("inicio")
                    }
                },
                usuarioViewModel = usuarioViewModel
            )
        }


        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("resumen") {
                        popUpTo("inicio") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onRegistroClick = {
                    navController.navigate("registro")
                },
                usuarioViewModel = usuarioViewModel
            )
        }


        composable("resumen") {
            ResumenScreen(
                usuarioViewModel = usuarioViewModel
            )
        }

        composable("colaboracion") {
            ColaboracionScreen(
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
