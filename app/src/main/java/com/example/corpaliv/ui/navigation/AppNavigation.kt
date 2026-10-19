
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

        // Pantalla principal
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

        // Registro de usuarios
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

        // Inicio de sesión
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

        // Resumen del usuario
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

        // Formulario de colaboración
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

        // Solicitudes recibidas
        composable("solicitudes") {
            SolicitudesScreen(
                solicitudViewModel = solicitudViewModel,
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        // Información institucional
        composable("institucion") {
            InfoScreen(
                contenido = SeccionContenido(
                    titulo = "Quiénes somos",
                    descripcion = "CORPALIV es una organización sin fines de lucro " +
                            "que promueve la autonomía, la participación y la inclusión " +
                            "social y laboral de personas con discapacidad múltiple.",
                    apartados = listOf(
                        "Nuestra misión" to
                                "Promover oportunidades de participación, autonomía " +
                                "e inclusión social y laboral para las personas con " +
                                "discapacidad múltiple.",

                        "Escuela Especial Jan Van Dijk" to
                                "Forma parte del contexto institucional de CORPALIV, " +
                                "orientado al desarrollo y la participación de las " +
                                "personas con discapacidad múltiple.",

                        "Alba Lab" to
                                "Es la red sociolaboral vinculada a CORPALIV, que " +
                                "desarrolla iniciativas de formación, preparación " +
                                "para el empleo, producción y comercialización."
                    )
                ),
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        // Programas de inclusión
        composable("programas") {
            InfoScreen(
                contenido = SeccionContenido(
                    titulo = "Programas de inclusión",
                    descripcion = "CORPALIV y Alba Lab impulsan iniciativas " +
                            "orientadas a la formación, la preparación para el empleo " +
                            "y la participación sociolaboral.",
                    apartados = listOf(
                        "Formación" to
                                "Desarrollo de habilidades y preparación para " +
                                "favorecer la participación y las oportunidades " +
                                "de empleo.",

                        "Inclusión sociolaboral" to
                                "Iniciativas destinadas a promover la autonomía, " +
                                "la participación y las oportunidades de inclusión " +
                                "en el entorno social y laboral.",

                        "Vinculación empresarial" to
                                "Colaboración con empresas, familias y donantes " +
                                "para impulsar oportunidades de participación " +
                                "y colaboración con impacto social."
                    )
                ),
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        // Productos y servicios
        composable("catalogo") {
            InfoScreen(
                contenido = SeccionContenido(
                    titulo = "Productos y servicios",
                    descripcion = "Alba Lab desarrolla actividades de producción " +
                            "y comercialización de productos, además de iniciativas " +
                            "de colaboración con empresas.",
                    apartados = listOf(
                        "Producción y comercialización" to
                                "El caso contempla la elaboración y comercialización " +
                                "de productos vinculados a las iniciativas de Alba Lab. " +
                                "La disponibilidad concreta debe confirmarse con la organización.",

                        "Servicios e iniciativas de colaboración" to
                                "La vinculación empresarial permite explorar " +
                                "alternativas de colaboración relacionadas con la " +
                                "inclusión sociolaboral y el impacto social.",

                        "Responsabilidad social empresarial" to
                                "CORPALIV gestiona iniciativas de responsabilidad " +
                                "social empresarial y vinculación con empresas, " +
                                "familias y donantes."
                    )
                ),
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        // Historias e impacto social
        composable("historias") {
            InfoScreen(
                contenido = SeccionContenido(
                    titulo = "Historias de impacto",
                    descripcion = "Conoce el propósito social de CORPALIV y " +
                            "la importancia de generar oportunidades de participación, " +
                            "autonomía e inclusión.",
                    apartados = listOf(
                        "Oportunidades de desarrollo" to
                                "La formación y la preparación para el empleo buscan " +
                                "favorecer el desarrollo de habilidades y las " +
                                "oportunidades de participación.",

                        "Colaboración con impacto social" to
                                "La participación de empresas, familias y donantes " +
                                "forma parte de las iniciativas de vinculación " +
                                "impulsadas por CORPALIV.",

                        "Un propósito compartido" to
                                "La propuesta busca facilitar que las personas " +
                                "interesadas conozcan la organización y encuentren " +
                                "alternativas para colaborar con su labor."
                    )
                ),
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
