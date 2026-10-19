
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
private val HomeAmarillo = Color(0xFFFFC247)
private val HomeFondo = Color(0xFFF3F6FA)
private val HomeTextoSecundario = Color(0xFF63738A)

@Composable
fun HomeScreen(
    onRegistroClick: () -> Unit,
    onLoginClick: () -> Unit,
    onColaborarClick: () -> Unit,
    onSeccionClick: (String) -> Unit
) {
    val context = LocalContext.current

    var mostrarContenido by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        delay(100)
        mostrarContenido = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeFondo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
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
                        .background(
                            HomeAzul,
                            RoundedCornerShape(15.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "C",
                        color = Color.White,
                        fontSize = 29.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "CORPALIV",
                        color = HomeAzul,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Inclusión y oportunidades",
                        color = HomeTextoSecundario,
                        fontSize = 11.sp
                    )
                }

                OutlinedButton(
                    onClick = onLoginClick,
                    shape = RoundedCornerShape(13.dp)
                ) {
                    Text(
                        text = "Entrar",
                        color = HomeAzul
                    )
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
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    Text(
                        text = "Un mundo con más oportunidades para todos.",
                        color = Color.White,
                        fontSize = 30.sp,
                        lineHeight = 36.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Conoce nuestra labor y descubre cómo puedes contribuir a una sociedad más inclusiva.",
                        color = Color.White.copy(alpha = 0.88f),
                        fontSize = 14.sp,
                        lineHeight = 21.sp
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
                            text = "Quiero colaborar  →",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Accesos a las secciones
        AnimatedVisibility(
            visible = mostrarContenido,
            enter = fadeIn(tween(700)) +
                    slideInVertically(
                        initialOffsetY = { it / 12 },
                        animationSpec = tween(700)
                    )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Text(
                    text = "Descubre CORPALIV",
                    color = HomeAzul,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Conoce nuestra misión, nuestros programas y nuestro trabajo.",
                    color = HomeTextoSecundario,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }

        TarjetaInicio(
            icono = "🏢",
            titulo = "Quiénes somos",
            descripcion = "Nuestra historia, misión y compromiso.",
            colorIcono = Color(0xFFE2F0ED),
            onClick = { onSeccionClick("institucion") }
        )

        TarjetaInicio(
            icono = "🌱",
            titulo = "Programas de inclusión",
            descripcion = "Iniciativas para promover autonomía y participación.",
            colorIcono = Color(0xFFE5F3E8),
            onClick = { onSeccionClick("programas") }
        )

        TarjetaInicio(
            icono = "🎁",
            titulo = "Productos y servicios",
            descripcion = "Descubre propuestas vinculadas a Alba Lab.",
            colorIcono = Color(0xFFFFF0D2),
            onClick = { onSeccionClick("catalogo") }
        )

        TarjetaInicio(
            icono = "💚",
            titulo = "Historias de impacto",
            descripcion = "Conoce experiencias y oportunidades de inclusión.",
            colorIcono = Color(0xFFE3ECFA),
            onClick = { onSeccionClick("historias") }
        )

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
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Forma parte del cambio",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = HomeAzul
                    )

                    Text(
                        text = "Crea tu cuenta para acceder a tu perfil y participar.",
                        color = HomeTextoSecundario,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
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
                    putExtra(
                        Intent.EXTRA_SUBJECT,
                        "Conoce CORPALIV"
                    )
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Conoce CORPALIV y su labor de inclusión social y laboral. " +
                                "Infórmate sobre sus programas y descubre cómo colaborar."
                    )
                }

                context.startActivity(
                    Intent.createChooser(
                        compartir,
                        "Compartir CORPALIV"
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Compartir CORPALIV")
        }

        Text(
            text = "CORPALIV · Inclusión y oportunidades",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            color = HomeTextoSecundario,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun TarjetaInicio(
    icono: String,
    titulo: String,
    descripcion: String,
    colorIcono: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(21.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
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
                    .background(
                        colorIcono,
                        RoundedCornerShape(17.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icono,
                    fontSize = 24.sp
                )
            }

            Spacer(Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = titulo,
                    color = HomeAzul,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = descripcion,
                    color = HomeTextoSecundario,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }

            Text(
                text = "›",
                color = HomeAzulMedio,
                fontSize = 27.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
