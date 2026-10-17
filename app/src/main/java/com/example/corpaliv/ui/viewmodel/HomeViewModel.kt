
package com.example.corpaliv.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class HomeViewModel {

    var mostrarInformacion by mutableStateOf(false)
        private set

    fun alternarInformacion() {
        mostrarInformacion = !mostrarInformacion
    }
}
