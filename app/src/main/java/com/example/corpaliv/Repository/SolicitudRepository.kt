
package com.example.corpaliv.data

import com.example.corpaliv.model.SolicitudColaboracion

class SolicitudRepository(
    private val solicitudDataStore: SolicitudDataStore
) {
    suspend fun guardarSolicitud(solicitud: SolicitudColaboracion) {
        solicitudDataStore.guardarSolicitud(solicitud)
    }

    suspend fun obtenerSolicitudes(): List<SolicitudColaboracion> {
        return solicitudDataStore.obtenerSolicitudes()
    }

    suspend fun obtenerSolicitudesPorCorreo(
        correo: String
    ): List<SolicitudColaboracion> {
        return solicitudDataStore.obtenerSolicitudesPorCorreo(correo)
    }

    suspend fun eliminarSolicitud(id: String): Boolean {
        return solicitudDataStore.eliminarSolicitud(id)
    }

    suspend fun eliminarSolicitudPropia(
        id: String,
        correoPropietario: String
    ): Boolean {
        return solicitudDataStore.eliminarSolicitudPropia(
            id,
            correoPropietario
        )
    }

    suspend fun actualizarSolicitud(
        solicitud: SolicitudColaboracion
    ): Boolean {
        return solicitudDataStore.actualizarSolicitud(solicitud)
    }
}
