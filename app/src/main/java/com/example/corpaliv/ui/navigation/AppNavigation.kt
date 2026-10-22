
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
import com.example.corpaliv.ui.screens.MisColaboracionesScreen
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
                },
                usuarioViewModel = usuarioViewModel
            )}

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
                    usuarioViewModel.cerrarSesion()

                    navController.navigate("inicio") {
                        popUpTo("inicio") {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                onMisColaboracionesClick = {
                    navController.navigate("mis_colaboraciones")
                }
            )
        }

// Historial de colaboraciones del usuario
        composable("mis_colaboraciones") {
            MisColaboracionesScreen(
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        // Colaboración
        composable("colaboracion") {
            ColaboracionScreen(
                onVolverClick = {
                    navController.popBackStack()
                },
                onVerSolicitudesClick = {
                    navController.navigate("solicitudes")
                },
                usuarioViewModel = usuarioViewModel,
                solicitudViewModel = solicitudViewModel
            )
        }

        // Solicitudes
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
                                "Alba Lab desarrolla productos y actividades " +
                                "de comercialización vinculados a sus iniciativas. " +
                                "La disponibilidad concreta debe consultarse con " +
                                "la organización.",

                        "Servicios e iniciativas de colaboración" to
                                "La vinculación empresarial permite explorar " +
                                "alternativas relacionadas con la inclusión " +
                                "sociolaboral y el impacto social.",

                        "Responsabilidad social empresarial" to
                                "CORPALIV trabaja con empresas, familias y donantes " +
                                "para impulsar oportunidades de inclusión sociolaboral."
                    )
                ),
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        // Historias de personas y experiencias documentadas por CORPALIV
        composable("historias") {
            InfoScreen(
                contenido = SeccionContenido(
                    titulo = "Historias de impacto",
                    descripcion = "Personas y experiencias recogidas en publicaciones " +
                            "de CORPALIV. Consulta las fuentes originales para conocer " +
                            "sus historias completas.",
                    apartados = listOf(
                        "Valentina Preece y María Larraín — Bgift Chile" to
                                "Una publicación de CORPALIV recoge la experiencia " +
                                "de colaboración de representantes de Bgift Chile " +
                                "con Alba Lab y el proyecto «Todos tenemos una luz " +
                                "que hacer brillar». La publicación menciona el trabajo " +
                                "colaborativo que permitió preparar más de 4.500 regalos.\n\n" +
                                "Publicación oficial:\n" +
                                "https://corpaliv.cl/alianzas-bgift-desde-el-inicio-con-alba-lab-y-vamos-por-mas/",

                        "Catalina Salazar — Alba Lab" to
                                "En la publicación del aniversario de Alba Lab se " +
                                "presenta la trayectoria de la red sociolaboral, su " +
                                "propósito de generar oportunidades para jóvenes y " +
                                "adultos con discapacidad múltiple y su relación con " +
                                "empresas y organizaciones colaboradoras.\n\n" +
                                "Publicación oficial:\n" +
                                "https://corpaliv.cl/albalab-cumplimos-un-ano/",

                        "Alejandra Mahecha, Nicolás Hormando Vaca Díez Trillo " +
                                "y Nathaly Molina Gómez — voluntariado" to
                                "Una publicación de CORPALIV presenta una iniciativa " +
                                "de voluntariado destinada a reunir relatos, poemas, " +
                                "dibujos y experiencias de familias vinculadas a la " +
                                "Escuela Especial Jan Van Dijk.\n\n" +
                                "Publicación oficial:\n" +
                                "https://corpaliv.cl/escribenos-tu-historia-especial/"
                    )
                ),
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
