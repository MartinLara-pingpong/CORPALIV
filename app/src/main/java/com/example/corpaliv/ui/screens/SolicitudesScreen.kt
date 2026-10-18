
package com.example.corpaliv.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.asImageBitmap
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

    LaunchedEffect(Unit) {
        solicitudViewModel.cargarSolicitudes()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Solicitudes recibidas",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Total de solicitudes: ${estado.totalSolicitudes}",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedButton(
            onClick = onVolverClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }

        if (estado.cargando) {
            CircularProgressIndicator()
        } else if (estado.error != null) {
            Text(
                text = estado.error ?: "No se pudieron cargar las solicitudes.",
                color = MaterialTheme.colorScheme.error
            )

            Button(
                onClick = { solicitudViewModel.cargarSolicitudes() }
            ) {
                Text("Reintentar")
            }
        } else if (estado.solicitudes.isEmpty()) {
            Text("Todavía no hay solicitudes guardadas.")
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = estado.solicitudes,
                    key = { it.id }
                ) { solicitud ->
                    SolicitudCard(solicitud = solicitud)
                }
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
                File(context.filesDir, nombreArchivo).absolutePath
            )
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = solicitud.nombre,
                style = MaterialTheme.typography.titleLarge
            )

            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Fotografía de ${solicitud.nombre}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Text("Correo: ${solicitud.correo}")
            Text("Edad: ${solicitud.edad}")
            Text("Disponibilidad: ${solicitud.fechaDisponibilidad}")
            Text("Motivo: ${solicitud.motivo}")
            Text("Acepta contacto: ${siNo(solicitud.aceptaContacto)}")
            Text("Disponibilidad regular: ${siNo(solicitud.disponibilidadRegular)}")
            Text("Recibir novedades: ${siNo(solicitud.recibirNovedades)}")
        }
    }
}

private fun siNo(valor: Boolean): String {
    return if (valor) "Sí" else "No"
}
