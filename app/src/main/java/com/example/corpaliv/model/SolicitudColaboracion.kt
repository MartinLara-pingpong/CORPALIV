
package com.example.corpaliv.model

data class SolicitudColaboracion(
    val id: String,
    val nombre: String,
    val correo: String,
    val edad: Int,
    val fechaDisponibilidad: String,
    val motivo: String,
    val aceptaContacto: Boolean,
    val disponibilidadRegular: Boolean,
    val recibirNovedades: Boolean,
    val rutaFotografia: String? = null
)
