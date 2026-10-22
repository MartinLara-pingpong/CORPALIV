package com.example.corpaliv.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable

@Composable
fun IconoError(hayError: Boolean, descripcion: String) {
    if (hayError) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = descripcion,
            tint = MaterialTheme.colorScheme.error
        )
    }
}