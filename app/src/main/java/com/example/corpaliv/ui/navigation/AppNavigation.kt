
package com.example.corpaliv.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.corpaliv.ui.screens.ColaboracionScreen
import com.example.corpaliv.ui.screens.HomeScreen
import com.example.corpaliv.ui.screens.InfoScreen
import com.example.corpaliv.ui.screens.LoginScreen
import com.example.corpaliv.ui.screens.RegistroScreen
import com.example.corpaliv.ui.screens.ResumenScreen
import com.example.corpaliv.ui.screens.SeccionContenido
import com.example.corpaliv.ui.screens.SolicitudesScreen
import com.example.corpaliv.ui.viewmodel.SolicitudViewModel
import com.example.corpaliv.ui.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val solicitudViewModel: SolicitudViewModel = viewModel()

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
                },
                onSeccionClick = { seccion ->
                    navController.navigate(seccion)
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
                onVolverInicio = {
                    navController.popBackStack(
                        route = "inicio",
                        inclusive = false
                    )
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
                onVolverInicio = {
                    navController.navigate("inicio") {
                        popUpTo("inicio") {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                usuarioViewModel = usuarioViewModel
            )
        }




        composable("resumen") {
            ResumenScreen(
                usuarioViewModel = usuarioViewModel,
                onVolverInicio = {
                    navController.navigate("inicio") {
                        launchSingleTop = true
                    }
                },
                onCerrarSesion = {
                    navController.navigate("login") {
                        popUpTo("inicio") {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }


        composable("colaboracion") {
            ColaboracionScreen(
                onVolverClick = {
                    navController.popBackStack()
                },
                onVerSolicitudesClick = {
                    navController.navigate("solicitudes")
                }
            )
        }

        composable("solicitudes") {
            SolicitudesScreen(
                solicitudViewModel = solicitudViewModel,
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("institucion") {
            InfoScreen(
                contenido = SeccionContenido(
                    titulo = "Quiénes somos",
                    descripcion = "CORPALIV trabaja para promover la autonomía, " +
                            "la participación y la inclusión social y laboral.",
                    apartados = listOf(
                        "Nuestra misión" to
                                "Favorecer la inclusión y las oportunidades de " +
                                "participación de las personas con discapacidad múltiple.",
                        "Alba Lab" to
                                "Una iniciativa vinculada a la formación, la producción " +
                                "y la inclusión sociolaboral."
                    )
                ),
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("programas") {
            InfoScreen(
                contenido = SeccionContenido(
                    titulo = "Programas de inclusión",
                    descripcion = "Conoce las áreas de trabajo de CORPALIV y Alba Lab.",
                    apartados = listOf(
                        "Formación" to
                                "Desarrollo de habilidades y preparación para el empleo.",
                        "Inclusión sociolaboral" to
                                "Iniciativas que promueven la autonomía y la participación.",
                        "Vinculación empresarial" to
                                "Oportunidades de colaboración y responsabilidad social empresarial."
                    )
                ),
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("catalogo") {
            InfoScreen(
                contenido = SeccionContenido(
                    titulo = "Productos y servicios",
                    descripcion = "Explora ejemplos de propuestas con impacto social.",
                    apartados = listOf(
                        "Productos elaborados en talleres" to
                                "Ejemplo de catálogo para presentar productos vinculados " +
                                "a iniciativas de inclusión.",
                        "Servicios y alianzas" to
                                "Espacio para mostrar propuestas de colaboración con empresas."
                    )
                ),
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("historias") {
            InfoScreen(
                contenido = SeccionContenido(
                    titulo = "Historias de impacto",
                    descripcion = "La inclusión se construye mediante oportunidades " +
                            "de participación y desarrollo.",
                    apartados = listOf(
                        "Una oportunidad para aprender" to
                                "Historia ilustrativa ficticia sobre el desarrollo de habilidades.",
                        "Colaboración que abre puertas" to
                                "Ejemplo ficticio de una alianza que facilita nuevas oportunidades."
                    )
                ),
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
