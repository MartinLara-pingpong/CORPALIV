package com.example.corpaliv.ui.screens


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.corpaliv.ui.components.FechaPickerDialog
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.corpaliv.ui.viewmodel.UsuarioViewModel
import java.util.UUID
import com.example.corpaliv.ui.components.FechaPickerDialog
import com.example.corpaliv.ui.components.IconoError
import com.example.corpaliv.ui.components.rememberAbrirCamara
private val ColAzul = Color(0xFF102C50)
private val ColAzulMedio = Color(0xFF245B91)
private val ColVerde = Color(0xFF35A879)
private val ColFondo = Color(0xFFF3F6FA)
private val ColGris = Color(0xFF63738A)

@Composable
fun ColaboracionScreen(
    onVolverClick: () -> Unit,
    onVerSolicitudesClick: () -> Unit,
    solicitudViewModel: SolicitudViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    val uiState by solicitudViewModel.uiState.collectAsState()
    val formulario by solicitudViewModel.formularioState.collectAsState()
    val usuarioState by usuarioViewModel.uiState.collectAsState()
    var mostrarDatePicker by remember { mutableStateOf(false) }

    var donacionSeleccionada by remember {
        mutableIntStateOf(10000)
    }

    var donacionPersonalizada by remember {
        mutableStateOf("")
    }

    var mostrarAvisoDonacion by remember {
        mutableStateOf(false)
    }

    val cantidadDonacion =
        donacionPersonalizada.toIntOrNull() ?: donacionSeleccionada

    val abrirCamara = rememberAbrirCamara(
        onFoto = { bitmap ->
            if (bitmap != null) solicitudViewModel.actualizarFotografia(bitmap)
            else solicitudViewModel.actualizarErrorFotografia("No se capturó ninguna fotografía.")
        },
        onPermisoDenegado = {
            solicitudViewModel.actualizarErrorFotografia(
                "Permiso de cámara denegado. Puedes activarlo en Ajustes."
            )
        }
    )

    LaunchedEffect(solicitudViewModel) {
        solicitudViewModel.cargarSolicitudes()
    }

    if (mostrarAvisoDonacion) {
        AlertDialog(
            onDismissRequest = {
                mostrarAvisoDonacion = false
            },
            title = {
                Text(
                    "Donación seleccionada",
                    color = ColAzul,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    "Has seleccionado una donación de " +
                            "${formatearPesos(cantidadDonacion)} CLP.\n\n" +
                            "Esta versión todavía no procesa pagos reales. " +
                            "Para recibir dinero habrá que integrar una " +
                            "pasarela de pago."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarAvisoDonacion = false
                    }
                ) {
                    Text("Entendido")
                }
            },
            containerColor = Color.White
        )
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
                        "Puedes apoyar a CORPALIV mediante una " +
                                "donación o enviando una solicitud " +
                                "para colaborar.",
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
                        "Apoya con una donación",
                        color = ColAzul,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Elige el importe con el que te gustaría " +
                                "contribuir. Cada aporte cuenta.",
                        color = ColGris,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )

                    Text(
                        "Selecciona un importe",
                        color = ColAzul,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ImporteDonacion(
                            importe = 5000,
                            seleccionado = donacionSeleccionada == 5000 &&
                                    donacionPersonalizada.isEmpty(),
                            modifier = Modifier.weight(1f)
                        ) {
                            donacionSeleccionada = 5000
                            donacionPersonalizada = ""
                        }

                        ImporteDonacion(
                            importe = 10000,
                            seleccionado = donacionSeleccionada == 10000 &&
                                    donacionPersonalizada.isEmpty(),
                            modifier = Modifier.weight(1f)
                        ) {
                            donacionSeleccionada = 10000
                            donacionPersonalizada = ""
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ImporteDonacion(
                            importe = 20000,
                            seleccionado = donacionSeleccionada == 20000 &&
                                    donacionPersonalizada.isEmpty(),
                            modifier = Modifier.weight(1f)
                        ) {
                            donacionSeleccionada = 20000
                            donacionPersonalizada = ""
                        }

                        ImporteDonacion(
                            importe = 50000,
                            seleccionado = donacionSeleccionada == 50000 &&
                                    donacionPersonalizada.isEmpty(),
                            modifier = Modifier.weight(1f)
                        ) {
                            donacionSeleccionada = 50000
                            donacionPersonalizada = ""
                        }
                    }

                    OutlinedTextField(
                        value = donacionPersonalizada,
                        onValueChange = { nuevoValor ->
                            if (
                                nuevoValor.length <= 9 &&
                                nuevoValor.all { it.isDigit() }
                            ) {
                                donacionPersonalizada = nuevoValor
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Otro importe (CLP)") },
                        placeholder = { Text("Ej.: 15000") },
                        leadingIcon = { Text("$") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFFEAF6F0),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                "Tu aporte seleccionado",
                                color = ColGris,
                                fontSize = 12.sp
                            )
                            Text(
                                "${formatearPesos(cantidadDonacion)} CLP",
                                color = ColAzul,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }

                    Button(
                        onClick = {
                            mostrarAvisoDonacion = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        enabled = cantidadDonacion > 0,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ColVerde,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            "Continuar con la donación →",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        "Esta versión aún no procesa pagos reales.",
                        color = ColGris,
                        fontSize = 11.sp
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
                        "Quiero colaborar con CORPALIV",
                        color = ColAzul,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Completa tus datos para que podamos conocer " +
                                "tu interés en colaborar.",
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
                        onValueChange = {},               // no se escribe a mano
                        readOnly = true,
                        label = { Text("Fecha de disponibilidad") },
                        leadingIcon = { Text("📅") },
                        placeholder = { Text("dd/MM/yyyy") },
                        isError = formulario.errores.fecha != null,
                        trailingIcon = {
                            IconButton(onClick = { mostrarDatePicker = true }) {
                                Icon(Icons.Default.DateRange, contentDescription = "Elegir fecha")
                            }
                        },
                        supportingText = {
                            formulario.errores.fecha?.let { Text(it) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    if (mostrarDatePicker) {
                        FechaPickerDialog(
                            onFechaSeleccionada = solicitudViewModel::actualizarFecha,
                            onDismiss = { mostrarDatePicker = false }
                        )
                    }

                    OutlinedTextField(
                        value = formulario.motivo,
                        onValueChange = solicitudViewModel::actualizarMotivo,
                        label = { Text("¿Porque decidiste colaborar?") },
                        placeholder = {
                            Text("")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        minLines = 3,
                        maxLines = 4,
                        isError = formulario.errores.motivo != null,
                        supportingText = {
                            formulario.errores.motivo?.let {
                                Text(
                                    text = it,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        shape = RoundedCornerShape(14.dp)
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
                        "Puedes añadir una fotografía para acompañar " +
                                "tu solicitud.",
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
                        onClick = { abrirCamara() },
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
                        detalle = "Si aceptas, indica tu número de teléfono.",
                        marcado = formulario.aceptaContacto,
                        onCambio = solicitudViewModel::actualizarAceptaContacto,
                        esInterruptor = false
                    )

                    if (formulario.aceptaContacto) {
                        OutlinedTextField(
                            value = formulario.telefono,
                            onValueChange = { nuevoTelefono ->
                                if (
                                    nuevoTelefono.length <= 15 &&
                                    nuevoTelefono.all {
                                        it.isDigit() || it == '+' || it == ' '
                                    }
                                ) {
                                    solicitudViewModel.actualizarTelefono(
                                        nuevoTelefono
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Número de teléfono") },
                            placeholder = { Text("+56 9 1234 5678") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone
                            ),
                            isError = formulario.errores.telefono != null,
                            supportingText = {
                                formulario.errores.telefono?.let {
                                    Text(it)
                                } ?: Text(
                                    "Incluye el código de país si corresponde."
                                )
                            },
                            shape = RoundedCornerShape(14.dp),
                            singleLine = true
                        )
                    }

                    PreferenciaColaboracion(
                        texto = "Tengo disponibilidad para colaborar regularmente",
                        detalle = "Marca esta opción si te gustaría participar de forma habitual.",
                        marcado = formulario.disponibilidadRegular,
                        onCambio = solicitudViewModel::actualizarDisponibilidadRegular,
                        esInterruptor = true
                    )

                    PreferenciaColaboracion(
                        texto = "Quiero recibir novedades",
                        detalle = "Podrás recibir información sobre actividades y oportunidades.",
                        marcado = formulario.recibirNovedades,
                        onCambio = solicitudViewModel::actualizarRecibirNovedades,
                        esInterruptor = true
                    )
                }
            }

            AnimatedVisibility(
                visible = uiState.mensaje != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                uiState.mensaje?.let {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFEAF6F0)
                    ) {
                        Text(
                            text = it,
                            modifier = Modifier.padding(14.dp),
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

            Button(
                onClick = {
                    if (uiState.enviando) return@Button

                    if (usuarioState.correo.isBlank()) {
                        solicitudViewModel.actualizarErrorFotografia(
                            "No se identifica una cuenta. Inicia sesión antes de enviar la solicitud."
                        )
                        return@Button
                    }

                    if (!solicitudViewModel.validarFormulario()) {
                        return@Button
                    }

                    val datos = solicitudViewModel.formularioState.value
                    val edadValidada = datos.edad.toIntOrNull()

                    if (edadValidada == null) return@Button

                    val idSolicitud = UUID.randomUUID().toString()

                    val nombreArchivo = datos.fotografia?.let { bitmap ->
                        solicitudViewModel.guardarFotografia(
                            bitmap = bitmap,
                            idSolicitud = idSolicitud
                        )
                    }

                    if (datos.fotografia != null && nombreArchivo == null) {
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
                            rutaFotografia = nombreArchivo,
                            telefono = if (datos.aceptaContacto) {
                                datos.telefono.trim()
                            } else {
                                ""
                            },
                            propietarioCorreo = usuarioState.correo
                                .trim()
                                .lowercase()
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = !uiState.enviando,
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColAzul,
                    contentColor = Color.White
                )
            ) {
                if (uiState.enviando) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(Modifier.width(10.dp))
                    Text("Enviando solicitud...")
                } else {
                    Text(
                        "Enviar solicitud de colaboración",
                        fontWeight = FontWeight.Bold
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
                    "Ver solicitudes de colaboración →",
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
private fun ImporteDonacion(
    importe: Int,
    seleccionado: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (seleccionado) {
                Color(0xFFEAF6F0)
            } else {
                Color.White
            },
            contentColor = ColAzul
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (seleccionado) {
                ColVerde
            } else {
                Color(0xFFD8E0EA)
            }
        )
    ) {
        Text(
            text = formatearPesos(importe),
            fontWeight = if (seleccionado) {
                FontWeight.Bold
            } else {
                FontWeight.Medium
            }
        )
    }
}

private fun formatearPesos(importe: Int): String {
    return "%,d".format(importe).replace(",", ".")
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