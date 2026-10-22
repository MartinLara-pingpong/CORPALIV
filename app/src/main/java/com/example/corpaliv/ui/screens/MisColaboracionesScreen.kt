package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.corpaliv.model.SolicitudColaboracion
import com.example.corpaliv.validation.SolicitudValidator
import com.example.corpaliv.ui.viewmodel.SolicitudViewModel
import com.example.corpaliv.ui.viewmodel.UsuarioViewModel

@Composable
fun MisColaboracionesScreen(
    onVolverClick: () -> Unit,
    solicitudViewModel: SolicitudViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    val estado by solicitudViewModel.uiState.collectAsState()
    val usuario by usuarioViewModel.uiState.collectAsState()
    var solicitudEliminar by remember {
        mutableStateOf<SolicitudColaboracion?>(null)
    }
    var solicitudEditando by remember {
        mutableStateOf<SolicitudColaboracion?>(null)
    }
    var motivoEditado by remember { mutableStateOf("") }

    val correoUsuario = usuario.correo.trim().lowercase()

    LaunchedEffect(correoUsuario) {
        if (correoUsuario.isNotBlank()) {
            solicitudViewModel.cargarSolicitudesPropias(correoUsuario)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Mis colaboraciones",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Aquí puedes consultar las solicitudes que has enviado a CORPALIV."
        )

        Spacer(Modifier.height(20.dp))

        when {
            correoUsuario.isBlank() -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("No hay una cuenta identificada.")
                        Text("Inicia sesión para consultar tus colaboraciones.")
                    }
                }
            }

            estado.cargando -> {
                CircularProgressIndicator()
                Spacer(Modifier.height(12.dp))
                Text("Cargando tus colaboraciones...")
            }

            estado.error != null -> {
                Text(
                    text = estado.error ?: "No se pudieron cargar tus solicitudes.",
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        solicitudViewModel.cargarSolicitudesPropias(
                            correoUsuario
                        )
                    }
                ) {
                    Text("Reintentar")
                }
            }

            estado.solicitudes.isEmpty() -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "♡",
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = "Todavía no tienes solicitudes",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            "Cuando envíes una solicitud de colaboración, aparecerá aquí."
                        )
                    }
                }
            }

            else -> {
                Text(
                    text = "Tus solicitudes: ${estado.solicitudes.size}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = estado.solicitudes.filter {
                            it.propietarioCorreo.trim().lowercase() ==
                                    correoUsuario
                        },
                        key = { it.id }
                    ) { solicitud ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    solicitud.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Text("Correo: ${solicitud.correo}")
                                Text("Disponibilidad: ${solicitud.fechaDisponibilidad}")
                                Text("Motivo: ${solicitud.motivo}")

                                Text(
                                    if (solicitud.disponibilidadRegular) {
                                        "Disponibilidad regular: Sí"
                                    } else {
                                        "Disponibilidad regular: No"
                                    }
                                )

                                OutlinedButton(
                                    onClick = {
                                        solicitudEditando = solicitud
                                        motivoEditado = solicitud.motivo
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Editar motivo")
                                }

                                OutlinedButton(
                                    onClick = {
                                        solicitudEliminar = solicitud
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        "Eliminar mi solicitud",
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            onClick = onVolverClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("← Volver a mi perfil")
        }
    }

    solicitudEliminar?.let { solicitud ->
        AlertDialog(
            onDismissRequest = {
                solicitudEliminar = null
            },
            title = {
                Text("¿Eliminar tu solicitud?")
            },
            text = {
                Text("Esta acción no se puede deshacer.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        solicitudViewModel.eliminarSolicitudPropia(
                            id = solicitud.id,
                            correoPropietario = correoUsuario
                        )
                        solicitudEliminar = null
                    }
                ) {
                    Text(
                        "Eliminar",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        solicitudEliminar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    solicitudEditando?.let { original ->
        val errorMotivo = SolicitudValidator.validarMotivo(motivoEditado)

        AlertDialog(
            onDismissRequest = {
                solicitudEditando = null
            },
            title = {
                Text("Editar motivo")
            },
            text = {
                OutlinedTextField(
                    value = motivoEditado,
                    onValueChange = { motivoEditado = it },
                    label = { Text("Motivo de colaboración") },
                    isError = errorMotivo != null,
                    supportingText = {
                        errorMotivo?.let { Text(it) }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    enabled = errorMotivo == null,
                    onClick = {
                        solicitudViewModel.actualizarSolicitud(
                            original.copy(motivo = motivoEditado.trim())
                        )
                        solicitudEditando = null
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        solicitudEditando = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}