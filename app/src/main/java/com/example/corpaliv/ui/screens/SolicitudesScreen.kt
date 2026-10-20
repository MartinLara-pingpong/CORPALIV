
package com.example.corpaliv.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.corpaliv.model.SolicitudColaboracion
import com.example.corpaliv.ui.viewmodel.SolicitudViewModel
import java.io.File

@Composable
fun SolicitudesScreen(
    solicitudViewModel: SolicitudViewModel,
    onVolverClick: () -> Unit
) {
    val estado by solicitudViewModel.uiState.collectAsState()

    LaunchedEffect(solicitudViewModel) {
        solicitudViewModel.cargarSolicitudes()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 24.dp,
            end = 20.dp,
            bottom = 28.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "CORPALIV",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Solicitudes de colaboración",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Aquí puedes consultar las personas que " +
                        "han mostrado interés en colaborar con nosotros.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.size(60.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "🤝",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${estado.totalSolicitudes}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Text(
                            text = if (estado.totalSolicitudes == 1) {
                                "Solicitud registrada"
                            } else {
                                "Solicitudes registradas"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Text(
                            text = "Personas interesadas en colaborar",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Listado de solicitudes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        if (estado.cargando) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 36.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator()

                    Text(
                        text = "Cargando solicitudes...",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else if (estado.error != null) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "No se pudieron cargar los datos",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = estado.error ?: "Se ha producido un error.",
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )

                        Button(
                            onClick = {
                                solicitudViewModel.cargarSolicitudes()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
            }
        } else if (estado.solicitudes.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(24.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            modifier = Modifier.size(76.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "📋",
                                    style = MaterialTheme.typography.headlineLarge
                                )
                            }
                        }

                        Text(
                            text = "Todavía no hay solicitudes",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Cuando alguien complete el formulario " +
                                    "de colaboración, sus datos aparecerán aquí.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        OutlinedButton(
                            onClick = {
                                solicitudViewModel.cargarSolicitudes()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Actualizar listado")
                        }
                    }
                }
            }
        } else {
            items(
                items = estado.solicitudes,
                key = { solicitud -> solicitud.id }
            ) { solicitud ->
                SolicitudCard(solicitud = solicitud)
            }
        }

        item {
            OutlinedButton(
                onClick = onVolverClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("← Volver")
            }
        }
    }
}

@Composable
private fun SolicitudCard(
    solicitud: SolicitudColaboracion
) {
    val context = LocalContext.current

    val bitmap = solicitud.rutaFotografia
        ?.takeIf { it.isNotBlank() }
        ?.let { nombreArchivo ->
            BitmapFactory.decodeFile(
                File(
                    context.filesDir,
                    nombreArchivo
                ).absolutePath
            )
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Surface(
                    modifier = Modifier.size(88.dp),
                    shape = RoundedCornerShape(18.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Fotografía de ${solicitud.nombre}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "👤",
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = solicitud.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = solicitud.correo,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "${solicitud.edad} años",
                            modifier = Modifier.padding(
                                horizontal = 9.dp,
                                vertical = 5.dp
                            ),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    DatoSolicitud(
                        etiqueta = "Disponibilidad",
                        valor = solicitud.fechaDisponibilidad
                    )

                    DatoSolicitud(
                        etiqueta = "Motivo de colaboración",
                        valor = solicitud.motivo
                    )
                }
            }

            Text(
                text = "Preferencias de contacto",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            PreferenciaSolicitud(
                etiqueta = "Acepta contacto",
                valor = solicitud.aceptaContacto
            )

            if (solicitud.aceptaContacto) {
                DatoSolicitud(
                    etiqueta = "Teléfono",
                    valor = solicitud.telefono
                )
            }

            PreferenciaSolicitud(
                etiqueta = "Disponibilidad regular",
                valor = solicitud.disponibilidadRegular
            )

            PreferenciaSolicitud(
                etiqueta = "Recibir novedades",
                valor = solicitud.recibirNovedades
            )
        }
    }
}

@Composable
private fun DatoSolicitud(
    etiqueta: String,
    valor: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = valor.ifBlank { "No especificado" },
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun PreferenciaSolicitud(
    etiqueta: String,
    valor: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (valor) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ) {
            Text(
                text = if (valor) "Sí" else "No",
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                ),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
