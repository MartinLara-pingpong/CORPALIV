
package com.example.corpaliv.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.corpaliv.model.SolicitudColaboracion
import kotlinx.coroutines.flow.first
import org.json.JSONArray
import org.json.JSONObject

private val Context.solicitudesDataStore by preferencesDataStore(
    name = "corpaliv_solicitudes"
)

class SolicitudDataStore(
    private val context: Context
) {
    private val solicitudesKey =
        stringPreferencesKey("solicitudes_colaboracion")

    suspend fun guardarSolicitud(
        solicitud: SolicitudColaboracion
    ) {
        context.solicitudesDataStore.edit { preferencias ->
            val solicitudesActuales =
                preferencias[solicitudesKey]?.let {
                    JSONArray(it)
                } ?: JSONArray()

            val nuevaSolicitud = JSONObject().apply {
                put("id", solicitud.id)
                put("nombre", solicitud.nombre)
                put("correo", solicitud.correo)
                put("edad", solicitud.edad)
                put("fechaDisponibilidad", solicitud.fechaDisponibilidad)
                put("motivo", solicitud.motivo)
                put("aceptaContacto", solicitud.aceptaContacto)
                put("disponibilidadRegular", solicitud.disponibilidadRegular)
                put("recibirNovedades", solicitud.recibirNovedades)
                put("rutaFotografia", solicitud.rutaFotografia)
            }

            solicitudesActuales.put(nuevaSolicitud)
            preferencias[solicitudesKey] = solicitudesActuales.toString()
        }
    }

    suspend fun eliminarSolicitud(id: String): Boolean {
        var eliminada = false

        context.solicitudesDataStore.edit { preferencias ->
            val contenido = preferencias[solicitudesKey] ?: return@edit
            val solicitudesActuales = JSONArray(contenido)
            val solicitudesActualizadas = JSONArray()

            for (indice in 0 until solicitudesActuales.length()) {
                val solicitud = solicitudesActuales.getJSONObject(indice)

                if (solicitud.getString("id") == id) {
                    eliminada = true
                } else {
                    solicitudesActualizadas.put(solicitud)
                }
            }

            preferencias[solicitudesKey] = solicitudesActualizadas.toString()
        }

        return eliminada
    }


    suspend fun obtenerSolicitudes(): List<SolicitudColaboracion> {
        val preferencias = context.solicitudesDataStore.data.first()
        val contenido = preferencias[solicitudesKey]
            ?: return emptyList()

        val json = JSONArray(contenido)

        return List(json.length()) { indice ->
            val elemento = json.getJSONObject(indice)

            SolicitudColaboracion(
                id = elemento.getString("id"),
                nombre = elemento.getString("nombre"),
                correo = elemento.getString("correo"),
                edad = elemento.getInt("edad"),
                fechaDisponibilidad = elemento.getString("fechaDisponibilidad"),
                motivo = elemento.getString("motivo"),
                aceptaContacto = elemento.getBoolean("aceptaContacto"),
                disponibilidadRegular = elemento.getBoolean("disponibilidadRegular"),
                recibirNovedades = elemento.getBoolean("recibirNovedades"),
                rutaFotografia = elemento
                    .optString("rutaFotografia", "")
                    .takeIf { it.isNotBlank() && it != "null" }
            )
        }
    }
}
