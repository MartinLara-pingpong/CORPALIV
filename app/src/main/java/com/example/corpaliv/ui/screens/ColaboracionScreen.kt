
package com.example.corpaliv.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.corpaliv.model.SolicitudColaboracion
import com.example.corpaliv.ui.viewmodel.SolicitudViewModel
import java.util.UUID

@Composable
fun ColaboracionScreen(
    onVolverClick: () -> Unit,
    solicitudViewModel: SolicitudViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var motivo by remember { mutableStateOf("") }

    var aceptaContacto by remember { mutableStateOf(false) }
    var disponibilidad by remember { mutableStateOf(false) }
    var recibirNovedades by remember { mutableStateOf(false) }

    var errores by remember {
        mutableStateOf(
            com.example.corpaliv.ui.viewmodel.SolicitudErrores()
        )
    }

    var fotografia by remember { mutableStateOf<Bitmap?>(null) }

    val uiState by solicitudViewModel.uiState.collectAsState()

    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            fotografia = bitmap
        }
    }

    LaunchedEffect(solicitudViewModel) {
        solicitudViewModel.cargarSolicitudes()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Quiero colaborar con CORPALIV",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                "Completa tus datos para que podamos conocer " +
                        "tu interés en colaborar."
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    errores = errores.copy(nombre = null)
                },
                label = { Text("Nombre completo") },
                isError = errores.nombre != null,
                supportingText = {
                    errores.nombre?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = correo,
                onValueChange = {
                    correo = it
                    errores = errores.copy(correo = null)
                },
                label = { Text("Correo electrónico") },
                isError = errores.correo != null,
                supportingText = {
                    errores.correo?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth(),
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
                isError = errores.edad != null,
                supportingText = {
                    errores.edad?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = fecha,
                onValueChange = {
                    fecha = it
                    errores = errores.copy(fecha = null)
                },
                label = {
                    Text("Fecha de disponibilidad (DD/MM/AAAA)")
                },
                isError = errores.fecha != null,
                supportingText = {
                    errores.fecha?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = motivo,
                onValueChange = {
                    motivo = it
                    errores = errores.copy(motivo = null)
                },
                label = { Text("¿Cómo te gustaría colaborar?") },
                isError = errores.motivo != null,
                supportingText = {
                    errores.motivo?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Text(
                text = "Fotografía opcional",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedButton(
                onClick = { camaraLauncher.launch(null) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (fotografia == null) {
                        "Tomar fotografía"
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
                        verticalArrangement = Arrangement.spacedBy(8.dp)
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
                            onClick = { fotografia = null },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Eliminar fotografía")
                        }
                    }
                }
            }

            RowColaboracion(
                texto = "Acepto que CORPALIV me contacte",
                marcado = aceptaContacto,
                onCambio = {
                    aceptaContacto = it
                    errores = errores.copy(contacto = null)
                }
            )

            errores.contacto?.let { Text(it) }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tengo disponibilidad para colaborar regularmente",
                    modifier = Modifier.weight(1f)
                )

                Switch(
                    checked = disponibilidad,
                    onCheckedChange = { disponibilidad = it }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Quiero recibir novedades de CORPALIV",
                    modifier = Modifier.weight(1f)
                )

                Switch(
                    checked = recibirNovedades,
                    onCheckedChange = { recibirNovedades = it }
                )
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

                    if (valido) {
                        solicitudViewModel.guardarSolicitud(
                            SolicitudColaboracion(
                                id = UUID.randomUUID().toString(),
                                nombre = nombre.trim(),
                                correo = correo.trim().lowercase(),
                                edad = edad.toInt(),
                                fechaDisponibilidad = fecha.trim(),
                                motivo = motivo.trim(),
                                aceptaContacto = aceptaContacto,
                                disponibilidadRegular = disponibilidad,
                                recibirNovedades = recibirNovedades
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.enviando
            ) {
                Text(
                    if (uiState.enviando) {
                        "Guardando..."
                    } else {
                        "Enviar solicitud"
                    }
                )
            }

            AnimatedVisibility(
                visible = uiState.enviando,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator()
                    Text("Guardando solicitud...")
                }
            }

            AnimatedVisibility(
                visible = uiState.mensaje != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    uiState.mensaje?.let { Text(it) }
                    Text(
                        "Solicitudes guardadas: ${uiState.totalSolicitudes}"
                    )
                }
            }

            AnimatedVisibility(
                visible = uiState.error != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                uiState.error?.let { Text(it) }
            }

            Button(
                onClick = onVolverClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al inicio")
            }
        }
    }
}

@Composable
private fun RowColaboracion(
    texto: String,
    marcado: Boolean,
    onCambio: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = texto,
            modifier = Modifier.weight(1f)
        )

        Checkbox(
            checked = marcado,
            onCheckedChange = onCambio
        )
    }
}
