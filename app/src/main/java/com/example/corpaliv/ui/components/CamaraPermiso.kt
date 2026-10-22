package com.example.corpaliv.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

/** Devuelve una función que verifica el permiso, lo pide si falta y abre la cámara. */
@Composable
fun rememberAbrirCamara(
    onFoto: (Bitmap?) -> Unit,
    onPermisoDenegado: () -> Unit
): () -> Unit {
    val context = LocalContext.current

    val camaraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap -> onFoto(bitmap) }

    val permisoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) camaraLauncher.launch(null) else onPermisoDenegado()
    }

    return {
        val concedido = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (concedido) camaraLauncher.launch(null)
        else permisoLauncher.launch(Manifest.permission.CAMERA)
    }
}