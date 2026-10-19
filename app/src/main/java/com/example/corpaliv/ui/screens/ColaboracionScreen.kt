package com.example.corpaliv.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.corpaliv.model.SolicitudColaboracion
import com.example.corpaliv.ui.viewmodel.SolicitudViewModel
import java.util.UUID

private val ColAzul = Color(0xFF102C50)
private val ColAzulMedio = Color(0xFF245B91)
private val ColVerde = Color(0xFF35A879)
private val ColFondo = Color(0xFFF3F6FA)
private val ColGris = Color(0xFF63738A)

@Composable
fun ColaboracionScreen(
    onVolverClick: () -> Unit,
    onVerSolicitudesClick: () -> Unit,
    solicitudViewModel: SolicitudViewModel = viewModel()
) {
    val uiState by solicitudViewModel.uiState.collectAsState()
    val formulario by solicitudViewModel.formularioState.collectAsState()

    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            solicitudViewModel.actualizarFotografia(bitmap)
        } else {
            solicitudViewModel.actualizarErrorFotografia(
                "No se capturó ninguna fotografía."
            )
        }
    }

    LaunchedEffect(solicitudViewModel) {
        solicitudViewModel.cargarSolicitudes()
    }

    Scaffold(containerColor = ColFondo) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(ColAzul, RoundedCornerShape(15.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "C",
                        color = Color.White,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "CORPALIV",
                        color = ColAzul,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        "Inclusión y oportunidades",
                        color = ColGris,
                        fontSize = 11.sp
                    )
                }

                OutlinedButton(
                    onClick = onVolverClick,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Volver")
                }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                color = ColAzul
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    ColAzul,
                                    ColAzulMedio,
                                    Color(0xFF147C82)
                                )
                            )
                        )
                        .padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(50.dp),
                        color = Color.White.copy(alpha = 0.14f)
                    ) {
                        Text(
                            "TU PARTICIPACIÓN IMPORTA",
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 8.dp
                            ),
                            color = Color(0xFF9CF0C5),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.7.sp
                        )
                    }

                    Text(
                        "Cada aporte abre nuevas oportunidades.",
                        color = Color.White,
                        fontSize = 26.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        "Cuéntanos cómo te gustaría colaborar. " +
                                "Tu información nos ayudará a conocer tu interés.",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        "Tus datos",
                        color = ColAzul,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Completa los campos para enviar tu solicitud.",
                        color = ColGris,
                        fontSize = 13.sp
                    )

                    OutlinedTextField(
                        value = formulario.nombre,
                        onValueChange = solicitudViewModel::actualizarNombre,
                        label = { Text("Nombre completo") },
                        leadingIcon = { Text("👤") },
                        isError = formulario.errores.nombre != null,
                        supportingText = {
                            formulario.errores.nombre?.let { Text(it) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = formulario.correo,
                        onValueChange = solicitudViewModel::actualizarCorreo,
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Text("✉️") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        isError = formulario.errores.correo != null,
                        supportingText = {
                            formulario.errores.correo?.let { Text(it) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = formulario.edad,
                        onValueChange = solicitudViewModel::actualizarEdad,
                        label = { Text("Edad") },
                        leadingIcon = { Text("🎂") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        isError = formulario.errores.edad != null,
                        supportingText = {
                            formulario.errores.edad?.let { Text(it) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = formulario.fecha,
                        onValueChange = solicitudViewModel::actualizarFecha,
                        label = { Text("Disponibilidad") },
                        leadingIcon = { Text("📅") },
                        placeholder = { Text("Ej.: fines de semana") },
                        isError = formulario.errores.fecha != null,
                        supportingText = {
                            formulario.errores.fecha?.let { Text(it) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = formulario.motivo,
                        onValueChange = solicitudViewModel::actualizarMotivo,
                        label = { Text("¿Cómo te gustaría colaborar?") },
                        leadingIcon = { Text("💚") },
                        isError = formulario.errores.motivo != null,
                        supportingText = {
                            formulario.errores.motivo?.let { Text(it) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        minLines = 3
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Fotografía",
                        color = ColAzul,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Puedes añadir una fotografía para acompañar tu solicitud.",
                        color = ColGris,
                        fontSize = 13.sp
                    )

                    formulario.fotografia?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Fotografía seleccionada",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(210.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    OutlinedButton(
                        onClick = { camaraLauncher.launch(null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            if (formulario.fotografia == null) {
                                "📷 Tomar fotografía"
                            } else {
                                "📷 Cambiar fotografía"
                            }
                        )
                    }

                    if (formulario.fotografia != null) {
                        OutlinedButton(
                            onClick = {
                                solicitudViewModel.actualizarFotografia(null)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text("Eliminar fotografía")
                        }
                    }

                    formulario.errorFotografia?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Tus preferencias",
                        color = ColAzul,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Indica cómo podemos mantener el contacto contigo.",
                        color = ColGris,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )

                    PreferenciaColaboracion(
                        texto = "Acepto que CORPALIV me contacte",
                        detalle = "Necesario para gestionar tu solicitud.",
                        marcado = formulario.aceptaContacto,
                        onCambio = solicitudViewModel::actualizarAceptaContacto,
                        esInterruptor = false
                    )

                    formulario.errores.contacto?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }

                    PreferenciaColaboracion(
                        texto = "Disponibilidad regular",
                        detalle = "Me interesa colaborar de forma habitual.",
                        marcado = formulario.disponibilidadRegular,
                        onCambio = solicitudViewModel::actualizarDisponibilidadRegular,
                        esInterruptor = true
                    )

                    PreferenciaColaboracion(
                        texto = "Recibir novedades",
                        detalle = "Quiero conocer noticias y actividades.",
                        marcado = formulario.recibirNovedades,
                        onCambio = solicitudViewModel::actualizarRecibirNovedades,
                        esInterruptor = true
                    )
                }
            }

            Button(
                onClick = {
                    val valido = solicitudViewModel.validarFormulario()

                    if (valido && !uiState.enviando) {
                        val datos = solicitudViewModel.formularioState.value
                        val edadValidada = datos.edad.toIntOrNull()

                        if (edadValidada != null) {
                            val idSolicitud = UUID.randomUUID().toString()

                            val nombreArchivo = datos.fotografia?.let { bitmap ->
                                solicitudViewModel.guardarFotografia(
                                    bitmap = bitmap,
                                    idSolicitud = idSolicitud
                                )
                            }

                            if (
                                datos.fotografia != null &&
                                nombreArchivo == null
                            ) {
                                solicitudViewModel.actualizarErrorFotografia(
                                    "No se pudo guardar la fotografía. Inténtalo de nuevo."
                                )
                                return@Button
                            }

                            solicitudViewModel.actualizarErrorFotografia(null)

                            solicitudViewModel.guardarSolicitud(
                                SolicitudColaboracion(
                                    id = idSolicitud,
                                    nombre = datos.nombre.trim(),
                                    correo = datos.correo.trim().lowercase(),
                                    edad = edadValidada,
                                    fechaDisponibilidad = datos.fecha.trim(),
                                    motivo = datos.motivo.trim(),
                                    aceptaContacto = datos.aceptaContacto,
                                    disponibilidadRegular = datos.disponibilidadRegular,
                                    recibirNovedades = datos.recibirNovedades,
                                    rutaFotografia = nombreArchivo
                                )
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = !uiState.enviando,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColVerde,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (uiState.enviando) {
                        "Guardando solicitud..."
                    } else {
                        "Enviar solicitud  →"
                    },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            AnimatedVisibility(
                visible = uiState.enviando,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = ColVerde
                    )
                    Spacer(Modifier.width(12.dp))
                    Text("Guardando tus datos...", color = ColGris)
                }
            }

            AnimatedVisibility(
                visible = uiState.mensaje != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFE5F5EB)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        uiState.mensaje?.let {
                            Text(
                                it,
                                color = Color(0xFF17694D),
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Text(
                            "Solicitudes guardadas: ${uiState.totalSolicitudes}",
                            color = Color(0xFF17694D),
                            fontSize = 13.sp
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = uiState.error != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                uiState.error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }
            }

            OutlinedButton(
                onClick = onVerSolicitudesClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ColAzul
                )
            ) {
                Text(
                    "Ver solicitudes de colaboración  →",
                    fontWeight = FontWeight.SemiBold
                )
            }

            OutlinedButton(
                onClick = onVolverClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("← Volver al inicio", color = ColAzul)
            }

            Text(
                "CORPALIV · Inclusión y oportunidades",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                color = ColGris,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun PreferenciaColaboracion(
    texto: String,
    detalle: String,
    marcado: Boolean,
    onCambio: (Boolean) -> Unit,
    esInterruptor: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                texto,
                color = ColAzul,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(3.dp))
            Text(
                detalle,
                color = ColGris,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )
        }

        if (esInterruptor) {
            Switch(
                checked = marcado,
                onCheckedChange = onCambio
            )
        } else {
            Checkbox(
                checked = marcado,
                onCheckedChange = onCambio
            )
        }
    }
}