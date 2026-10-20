
package com.example.corpaliv.ui.screens

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val HomeAzul = Color(0xFF102C50)
private val HomeAzulMedio = Color(0xFF245B91)
private val HomeVerde = Color(0xFF35A879)
private val HomeFondo = Color(0xFFF3F6FA)
private val HomeTextoSecundario = Color(0xFF63738A)

private data class SeccionInicio(
    val icono: String,
    val titulo: String,
    val descripcion: String,
    val colorIcono: Color,
    val ruta: String
)

@Composable
fun HomeScreen(
    onRegistroClick: () -> Unit,
    onLoginClick: () -> Unit,
    onColaborarClick: () -> Unit,
    onSeccionClick: (String) -> Unit
) {
    val context = LocalContext.current

    var mostrarContenido by remember { mutableStateOf(false) }
    var busqueda by remember { mutableStateOf("") }
    var mostrarAccesibilidad by remember { mutableStateOf(false) }
    var escalaTexto by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(Unit) {
        delay(100)
        mostrarContenido = true
    }

    val secciones = listOf(
        SeccionInicio(
            "🏢",
            "Quiénes somos",
            "Nuestra historia, misión y compromiso.",
            Color(0xFFE2F0ED),
            "institucion"
        ),
        SeccionInicio(
            "🌱",
            "Programas de inclusión",
            "Iniciativas para promover autonomía y participación.",
            Color(0xFFE5F3E8),
            "programas"
        ),
        SeccionInicio(
            "🎁",
            "Productos y servicios",
            "Descubre propuestas vinculadas a Alba Lab.",
            Color(0xFFFFF0D2),
            "catalogo"
        ),
        SeccionInicio(
            "💚",
            "Historias de impacto",
            "Conoce experiencias y oportunidades de inclusión.",
            Color(0xFFE3ECFA),
            "historias"
        )
    )

    val seccionesFiltradas = secciones.filter {
        it.titulo.contains(busqueda.trim(), ignoreCase = true) ||
                it.descripcion.contains(busqueda.trim(), ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeFondo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Encabezado
        AnimatedVisibility(
            visible = mostrarContenido,
            enter = fadeIn(tween(450)) +
                    slideInVertically(
                        initialOffsetY = { -it / 8 },
                        animationSpec = tween(450)
                    )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(HomeAzul, RoundedCornerShape(15.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "C",
                        color = Color.White,
                        fontSize = 29.sp * escalaTexto,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "CORPALIV",
                        color = HomeAzul,
                        fontSize = 21.sp * escalaTexto,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Inclusión y oportunidades",
                        color = HomeTextoSecundario,
                        fontSize = 11.sp * escalaTexto
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    TextButton(onClick = { mostrarAccesibilidad = true }) {
                        Text(
                            text = "Aa",
                            color = HomeAzul,
                            fontSize = 16.sp * escalaTexto,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = onLoginClick,
                        shape = RoundedCornerShape(13.dp)
                    ) {
                        Text(
                            text = "Entrar",
                            color = HomeAzul,
                            fontSize = 12.sp * escalaTexto
                        )
                    }
                }
            }
        }

        // Portada principal
        AnimatedVisibility(
            visible = mostrarContenido,
            enter = fadeIn(tween(600)) +
                    slideInVertically(
                        initialOffsetY = { it / 10 },
                        animationSpec = tween(600)
                    )
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                color = HomeAzul
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    HomeAzul,
                                    HomeAzulMedio,
                                    Color(0xFF147C82)
                                )
                            )
                        )
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(17.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(50.dp),
                        color = Color.White.copy(alpha = 0.14f)
                    ) {
                        Text(
                            text = "INCLUSIÓN QUE TRANSFORMA",
                            modifier = Modifier.padding(
                                horizontal = 13.dp,
                                vertical = 9.dp
                            ),
                            color = Color(0xFF9CF0C5),
                            fontSize = 10.sp * escalaTexto,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    Text(
                        text = "Un mundo con más oportunidades para todos.",
                        color = Color.White,
                        fontSize = 30.sp * escalaTexto,
                        lineHeight = 36.sp * escalaTexto,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Conoce nuestra labor y descubre cómo puedes contribuir a una sociedad más inclusiva.",
                        color = Color.White.copy(alpha = 0.88f),
                        fontSize = 14.sp * escalaTexto,
                        lineHeight = 21.sp * escalaTexto
                    )

                    Button(
                        onClick = onColaborarClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF75D7A8),
                            contentColor = HomeAzul
                        )
                    ) {
                        Text(
                            text = "Quiero colaborar →",
                            fontSize = 14.sp * escalaTexto,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Buscador de secciones
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "¿Qué estás buscando?",
                color = HomeAzul,
                fontSize = 21.sp * escalaTexto,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                label = {
                    Text(
                        "Buscar secciones",
                        fontSize = 14.sp * escalaTexto
                    )
                },
                placeholder = {
                    Text(
                        "Ej.: programas, historias...",
                        fontSize = 13.sp * escalaTexto
                    )
                },
                leadingIcon = {
                    Text("🔎", fontSize = 18.sp)
                },
                trailingIcon = {
                    if (busqueda.isNotEmpty()) {
                        TextButton(onClick = { busqueda = "" }) {
                            Text("Borrar")
                        }
                    }
                }
            )
        }

        // Secciones
        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text(
                text = "Descubre CORPALIV",
                color = HomeAzul,
                fontSize = 23.sp * escalaTexto,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Conoce nuestra misión, nuestros programas y nuestro trabajo.",
                color = HomeTextoSecundario,
                fontSize = 14.sp * escalaTexto,
                lineHeight = 20.sp * escalaTexto
            )
        }

        if (seccionesFiltradas.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("🔎", fontSize = 30.sp)
                    Text(
                        text = "No encontramos esa sección",
                        color = HomeAzul,
                        fontSize = 17.sp * escalaTexto,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Prueba con otra palabra.",
                        color = HomeTextoSecundario,
                        fontSize = 13.sp * escalaTexto
                    )
                }
            }
        } else {
            seccionesFiltradas.forEach { seccion ->
                TarjetaInicio(
                    icono = seccion.icono,
                    titulo = seccion.titulo,
                    descripcion = seccion.descripcion,
                    colorIcono = seccion.colorIcono,
                    escalaTexto = escalaTexto,
                    onClick = { onSeccionClick(seccion.ruta) }
                )
            }
        }

        // Invitación a crear una cuenta
        AnimatedVisibility(
            visible = mostrarContenido,
            enter = fadeIn(tween(850)) +
                    slideInVertically(
                        initialOffsetY = { it / 12 },
                        animationSpec = tween(850)
                    )
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Forma parte del cambio",
                        fontSize = 19.sp * escalaTexto,
                        fontWeight = FontWeight.Bold,
                        color = HomeAzul
                    )

                    Text(
                        text = "Crea tu cuenta para acceder a tu perfil y participar.",
                        color = HomeTextoSecundario,
                        fontSize = 14.sp * escalaTexto,
                        lineHeight = 20.sp * escalaTexto
                    )

                    Button(
                        onClick = onRegistroClick,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = HomeVerde
                        )
                    ) {
                        Text(
                            text = "Crear una cuenta",
                            fontSize = 14.sp * escalaTexto,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Compartir la aplicación
        OutlinedButton(
            onClick = {
                val compartir = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "Conoce CORPALIV")
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Conoce CORPALIV y su labor de inclusión social y laboral. " +
                                "Infórmate sobre sus programas y descubre cómo colaborar."
                    )
                }

                context.startActivity(
                    Intent.createChooser(compartir, "Compartir CORPALIV")
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = "Compartir CORPALIV",
                fontSize = 14.sp * escalaTexto
            )
        }

        Text(
            text = "CORPALIV · Inclusión y oportunidades",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            color = HomeTextoSecundario,
            fontSize = 12.sp * escalaTexto
        )
    }

    // Menú de accesibilidad
    if (mostrarAccesibilidad) {
        AlertDialog(
            onDismissRequest = { mostrarAccesibilidad = false },
            title = {
                Text(
                    text = "Accesibilidad",
                    color = HomeAzul,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Ajusta el tamaño del texto para leer con mayor comodidad.")

                    Text(
                        text = "Tamaño: ${(escalaTexto * 100).toInt()}%",
                        fontWeight = FontWeight.SemiBold
                    )

                    Slider(
                        value = escalaTexto,
                        onValueChange = { escalaTexto = it },
                        valueRange = 0.8f..1.5f,
                        steps = 6
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(
                            onClick = {
                                escalaTexto = (escalaTexto - 0.1f)
                                    .coerceAtLeast(0.8f)
                            }
                        ) {
                            Text("A−")
                        }

                        TextButton(
                            onClick = { escalaTexto = 1f }
                        ) {
                            Text("Restablecer")
                        }

                        TextButton(
                            onClick = {
                                escalaTexto = (escalaTexto + 0.1f)
                                    .coerceAtMost(1.5f)
                            }
                        ) {
                            Text("A+")
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { mostrarAccesibilidad = false }
                ) {
                    Text("Listo")
                }
            },
            containerColor = Color.White
        )
    }
}

@Composable
private fun TarjetaInicio(
    icono: String,
    titulo: String,
    descripcion: String,
    colorIcono: Color,
    escalaTexto: Float,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(21.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(colorIcono, RoundedCornerShape(17.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icono,
                    fontSize = 24.sp * escalaTexto
                )
            }

            Spacer(Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = titulo,
                    color = HomeAzul,
                    fontSize = 15.sp * escalaTexto,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = descripcion,
                    color = HomeTextoSecundario,
                    fontSize = 12.sp * escalaTexto,
                    lineHeight = 17.sp * escalaTexto
                )
            }

            Text(
                text = "›",
                color = HomeAzulMedio,
                fontSize = 27.sp * escalaTexto,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
