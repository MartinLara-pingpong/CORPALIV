
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

    suspend fun guardarSolicitud(solicitud: SolicitudColaboracion) {
        context.solicitudesDataStore.edit { preferencias ->
            val solicitudes = preferencias[solicitudesKey]?.let {
                JSONArray(it)
            } ?: JSONArray()

            solicitudes.put(solicitudAJson(solicitud))
            preferencias[solicitudesKey] = solicitudes.toString()
        }
    }

    suspend fun obtenerSolicitudes(): List<SolicitudColaboracion> {
        val preferencias = context.solicitudesDataStore.data.first()
        val contenido = preferencias[solicitudesKey] ?: return emptyList()
        return jsonALista(JSONArray(contenido))
    }

    suspend fun obtenerSolicitudesPorCorreo(
        correo: String
    ): List<SolicitudColaboracion> {
        val correoNormalizado = correo.trim().lowercase()
        if (correoNormalizado.isBlank()) return emptyList()

        return obtenerSolicitudes().filter {
            it.propietarioCorreo.trim().lowercase() == correoNormalizado
        }
    }

    suspend fun eliminarSolicitud(id: String): Boolean {
        var eliminada = false

        context.solicitudesDataStore.edit { preferencias ->
            val contenido = preferencias[solicitudesKey]
                ?: return@edit

            val actuales = JSONArray(contenido)
            val actualizadas = JSONArray()

            for (i in 0 until actuales.length()) {
                val elemento = actuales.getJSONObject(i)

                if (elemento.optString("id") == id) {
                    eliminada = true
                } else {
                    actualizadas.put(elemento)
                }
            }

            preferencias[solicitudesKey] = actualizadas.toString()
        }

        return eliminada
    }

    suspend fun eliminarSolicitudPropia(
        id: String,
        correoPropietario: String
    ): Boolean {
        val correoNormalizado = correoPropietario.trim().lowercase()
        if (correoNormalizado.isBlank()) return false

        var eliminada = false

        context.solicitudesDataStore.edit { preferencias ->
            val contenido = preferencias[solicitudesKey]
                ?: return@edit

            val actuales = JSONArray(contenido)
            val actualizadas = JSONArray()

            for (i in 0 until actuales.length()) {
                val elemento = actuales.getJSONObject(i)
                val mismoId = elemento.optString("id") == id
                val mismoPropietario =
                    elemento.optString("propietarioCorreo")
                        .trim()
                        .lowercase() == correoNormalizado

                if (mismoId && mismoPropietario) {
                    eliminada = true
                } else {
                    actualizadas.put(elemento)
                }
            }

            preferencias[solicitudesKey] = actualizadas.toString()
        }

        return eliminada
    }

    suspend fun actualizarSolicitud(
        solicitudActualizada: SolicitudColaboracion
    ): Boolean {
        var actualizada = false

        context.solicitudesDataStore.edit { preferencias ->
            val contenido = preferencias[solicitudesKey]
                ?: return@edit

            val actuales = JSONArray(contenido)
            val nuevas = JSONArray()

            for (i in 0 until actuales.length()) {
                val elemento = actuales.getJSONObject(i)

                if (elemento.optString("id") == solicitudActualizada.id) {
                    nuevas.put(solicitudAJson(solicitudActualizada))
                    actualizada = true
                } else {
                    nuevas.put(elemento)
                }
            }

            if (actualizada) {
                preferencias[solicitudesKey] = nuevas.toString()
            }
        }

        return actualizada
    }

    private fun solicitudAJson(
        solicitud: SolicitudColaboracion
    ) = JSONObject().apply {
        put("id", solicitud.id)
        put("nombre", solicitud.nombre)
        put("correo", solicitud.correo)
        put("telefono", solicitud.telefono)
        put("edad", solicitud.edad)
        put("fechaDisponibilidad", solicitud.fechaDisponibilidad)
        put("motivo", solicitud.motivo)
        put("aceptaContacto", solicitud.aceptaContacto)
        put("disponibilidadRegular", solicitud.disponibilidadRegular)
        put("recibirNovedades", solicitud.recibirNovedades)
        put("rutaFotografia", solicitud.rutaFotografia)
        put("propietarioCorreo", solicitud.propietarioCorreo)
    }

    private fun jsonALista(
        json: JSONArray
    ): List<SolicitudColaboracion> {
        return List(json.length()) { indice ->
            val elemento = json.getJSONObject(indice)

            SolicitudColaboracion(
                id = elemento.getString("id"),
                nombre = elemento.getString("nombre"),
                correo = elemento.getString("correo"),
                telefono = elemento.optString("telefono", ""),
                edad = elemento.getInt("edad"),
                fechaDisponibilidad =
                    elemento.getString("fechaDisponibilidad"),
                motivo = elemento.getString("motivo"),
                aceptaContacto = elemento.getBoolean("aceptaContacto"),
                disponibilidadRegular =
                    elemento.getBoolean("disponibilidadRegular"),
                recibirNovedades =
                    elemento.getBoolean("recibirNovedades"),
                rutaFotografia = elemento.optString(
                    "rutaFotografia",
                    ""
                ).takeIf { it.isNotBlank() && it != "null" },
                propietarioCorreo =
                    elemento.optString("propietarioCorreo", "")
            )
        }
    }
}
