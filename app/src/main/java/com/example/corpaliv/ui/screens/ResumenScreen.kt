
package com.example.corpaliv.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.corpaliv.ui.viewmodel.UsuarioViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

private const val ARCHIVO_FOTO_PERFIL = "foto_perfil.jpg"

@Composable
fun ResumenScreen(
    usuarioViewModel: UsuarioViewModel,
    onCerrarSesion: () -> Unit,
    onVolverInicio: () -> Unit,
    onMisColaboracionesClick: () -> Unit
) {
    val uiState by usuarioViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var fotoPerfil by remember {
        mutableStateOf<Bitmap?>(null)
    }
    var cargandoFoto by remember {
        mutableStateOf(true)
    }
    var mensajeFoto by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        fotoPerfil = withContext(Dispatchers.IO) {
            cargarFoto(context)
        }
        cargandoFoto = false
    }

    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            scope.launch {
                val guardada = withContext(Dispatchers.IO) {
                    guardarFoto(context, bitmap)
                }

                if (guardada) {
                    fotoPerfil = bitmap
                    mensajeFoto = "Foto de perfil guardada correctamente."
                } else {
                    mensajeFoto = "No se pudo guardar la foto."
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Mi perfil",
            style = MaterialTheme.typography.headlineMedium
        )

        if (cargandoFoto) {
            Text("Cargando foto...")
        } else if (fotoPerfil != null) {
            Image(
                bitmap = fotoPerfil!!.asImageBitmap(),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Text("Todavía no tienes una foto de perfil.")
        }

        Button(
            onClick = {
                camaraLauncher.launch(null)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (fotoPerfil == null) {
                    "Hacer foto de perfil"
                } else {
                    "Cambiar foto de perfil"
                }
            )
        }

        if (fotoPerfil != null) {
            OutlinedButton(
                onClick = {
                    scope.launch {
                        val eliminada = withContext(Dispatchers.IO) {
                            eliminarFoto(context)
                        }

                        if (eliminada) {
                            fotoPerfil = null
                            mensajeFoto = "Foto de perfil eliminada."
                        } else {
                            mensajeFoto = "No se pudo eliminar la foto."
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar foto")
            }
        }

        if (mensajeFoto.isNotBlank()) {
            Text(mensajeFoto)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Información de tu cuenta",
            style = MaterialTheme.typography.titleMedium
        )

        Text("Nombre: ${uiState.nombre}")
        Text("Correo: ${uiState.correo}")
        Text("Dirección: ${uiState.direccion}")

        Spacer(modifier = Modifier.height(8.dp))

        // NUEVO ACCESO AL HISTORIAL DE COLABORACIONES

        Button(
            onClick = onMisColaboracionesClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("♡ Mis colaboraciones")
        }

        Button(
            onClick = onVolverInicio,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("← Volver al inicio")
        }

        OutlinedButton(
            onClick = onCerrarSesion,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}

private fun guardarFoto(
    context: Context,
    bitmap: Bitmap
): Boolean {
    return try {
        context.openFileOutput(
            ARCHIVO_FOTO_PERFIL,
            Context.MODE_PRIVATE
        ).use { output ->
            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                90,
                output
            )
        }
    } catch (e: Exception) {
        false
    }
}

private fun cargarFoto(context: Context): Bitmap? {
    return try {
        val archivo = File(
            context.filesDir,
            ARCHIVO_FOTO_PERFIL
        )

        if (archivo.exists()) {
            BitmapFactory.decodeFile(archivo.absolutePath)
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}

private fun eliminarFoto(context: Context): Boolean {
    return try {
        val archivo = File(
            context.filesDir,
            ARCHIVO_FOTO_PERFIL
        )

        !archivo.exists() || archivo.delete()
    } catch (e: Exception) {
        false
    }
}
