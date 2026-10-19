
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
}
