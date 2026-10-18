
package com.example.corpaliv.ui.screens

import android.content.Context
import android.graphics.Bitmap
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.corpaliv.model.SolicitudColaboracion
import com.example.corpaliv.ui.viewmodel.SolicitudErrores
import com.example.corpaliv.ui.viewmodel.SolicitudViewModel
import java.io.File
import java.io.FileOutputStream
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
    val context = LocalContext.current

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var motivo by remember { mutableStateOf("") }

    var aceptaContacto by remember { mutableStateOf(false) }
    var disponibilidad by remember { mutableStateOf(false) }
    var recibirNovedades by remember { mutableStateOf(false) }

    var errores by remember { mutableStateOf(SolicitudErrores()) }
    var fotografia by remember { mutableStateOf<Bitmap?>(null) }
    var errorFotografia by remember { mutableStateOf<String?>(null) }

    val uiState by solicitudViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.mensaje) {
        if (uiState.mensaje == "Solicitud guardada correctamente.") {
            nombre = ""
            correo = ""
            edad = ""
            fecha = ""
            motivo = ""
            aceptaContacto = false
            disponibilidad = false
            recibirNovedades = false
            fotografia = null
            errores = SolicitudErrores()
            errorFotografia = null
        }
    }

    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            fotografia = bitmap
            errorFotografia = null
        } else {
            errorFotografia = "No se capturó ninguna fotografía."
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
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                        value = nombre,
                        onValueChange = {
                            nombre = it
                            errores = errores.copy(nombre = null)
                        },
                        label = { Text("Nombre completo") },
                        leadingIcon = { Text("👤") },
                        isError = errores.nombre != null,
                        supportingText = {
                            errores.nombre?.let { Text(it) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = correo,
                        onValueChange = {
                            correo = it
                            errores = errores.copy(correo = null)
                        },
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Text("✉️") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        isError = errores.correo != null,
                        supportingText = {
                            errores.correo?.let { Text(it) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = edad,
                        onValueChange = {
                            if (it.all(Char::isDigit)) {
                                edad = it
                                errores = errores.copy(edad = null)
                            }
                        },
                        label = { Text("Edad") },
                        leadingIcon = { Text("🎂") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        isError = errores.edad != null,
                        supportingText = {
                            errores.edad?.let { Text(it) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = fecha,
                        onValueChange = {
                            fecha = it
                            errores = errores.copy(fecha = null)
                        },
                        label = { Text("Fecha de disponibilidad") },
                        placeholder = { Text("DD/MM/AAAA") },
                        leadingIcon = { Text("📅") },
                        isError = errores.fecha != null,
                        supportingText = {
                            errores.fecha?.let { Text(it) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = motivo,
                        onValueChange = {
                            motivo = it
                            errores = errores.copy(motivo = null)
                        },
                        label = { Text("¿Cómo te gustaría colaborar?") },
                        placeholder = { Text("Cuéntanos tu idea o interés...") },
                        leadingIcon = { Text("💬") },
                        isError = errores.motivo != null,
                        supportingText = {
                            errores.motivo?.let { Text(it) }
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
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Fotografía opcional",
                        color = ColAzul,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Puedes adjuntar una fotografía tomada con la cámara.",
                        color = ColGris,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )

                    OutlinedButton(
                        onClick = { camaraLauncher.launch(null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            if (fotografia == null) {
                                "📷  Tomar fotografía"
                            } else {
                                "Volver a tomar fotografía"
                            }
                        )
                    }

                    AnimatedVisibility(
                        visible = fotografia != null,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        fotografia?.let { bitmap ->
                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = "Fotografía capturada",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(220.dp),
                                    contentScale = ContentScale.Crop
                                )

                                OutlinedButton(
                                    onClick = {
                                        fotografia = null
                                        errorFotografia = null
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(14.dp)
                                ) {
                                    Text("Eliminar fotografía")
                                }
                            }
                        }
                    }

                    errorFotografia?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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
                        marcado = aceptaContacto,
                        onCambio = {
                            aceptaContacto = it
                            errores = errores.copy(contacto = null)
                        },
                        esInterruptor = false
                    )

                    errores.contacto?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }

                    PreferenciaColaboracion(
                        texto = "Disponibilidad regular",
                        detalle = "Me interesa colaborar de forma habitual.",
                        marcado = disponibilidad,
                        onCambio = { disponibilidad = it },
                        esInterruptor = true
                    )

                    PreferenciaColaboracion(
                        texto = "Recibir novedades",
                        detalle = "Quiero conocer noticias y actividades.",
                        marcado = recibirNovedades,
                        onCambio = { recibirNovedades = it },
                        esInterruptor = true
                    )
                }
            }

            Button(
                onClick = {
                    val resultado = solicitudViewModel.validarSolicitud(
                        nombre = nombre,
                        correo = correo,
                        edad = edad,
                        fecha = fecha,
                        motivo = motivo,
                        aceptaContacto = aceptaContacto
                    )

                    errores = resultado

                    val valido =
                        resultado.nombre == null &&
                                resultado.correo == null &&
                                resultado.edad == null &&
                                resultado.fecha == null &&
                                resultado.motivo == null &&
                                resultado.contacto == null

                    if (valido && !uiState.enviando) {
                        val edadValidada = edad.toIntOrNull()

                        if (edadValidada != null) {
                            val idSolicitud = UUID.randomUUID().toString()

                            val nombreArchivo = fotografia?.let { bitmap ->
                                guardarFotografia(
                                    context = context,
                                    bitmap = bitmap,
                                    idSolicitud = idSolicitud
                                )
                            }

                            if (fotografia != null && nombreArchivo == null) {
                                errorFotografia =
                                    "No se pudo guardar la fotografía. Inténtalo de nuevo."
                                return@Button
                            }

                            errorFotografia = null

                            solicitudViewModel.guardarSolicitud(
                                SolicitudColaboracion(
                                    id = idSolicitud,
                                    nombre = nombre.trim(),
                                    correo = correo.trim().lowercase(),
                                    edad = edadValidada,
                                    fechaDisponibilidad = fecha.trim(),
                                    motivo = motivo.trim(),
                                    aceptaContacto = aceptaContacto,
                                    disponibilidadRegular = disponibilidad,
                                    recibirNovedades = recibirNovedades,
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

private fun guardarFotografia(
    context: Context,
    bitmap: Bitmap,
    idSolicitud: String
): String? {
    val nombreArchivo = "solicitud_$idSolicitud.jpg"
    val archivo = File(context.filesDir, nombreArchivo)

    return try {
        val guardada = FileOutputStream(archivo).use { salida ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, salida)
        }

        if (guardada) {
            nombreArchivo
        } else {
            archivo.delete()
            null
        }
    } catch (e: Exception) {
        archivo.delete()
        null
    }
}
