
package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ColaboracionScreen(
    onVolverClick: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var motivo by remember { mutableStateOf("") }

    var aceptaContacto by remember { mutableStateOf(false) }
    var disponibilidad by remember { mutableStateOf(false) }
    var recibirNovedades by remember { mutableStateOf(false) }
    var enviado by remember { mutableStateOf(false) }

    var errorNombre by remember { mutableStateOf(false) }
    var errorCorreo by remember { mutableStateOf(false) }
    var errorEdad by remember { mutableStateOf(false) }
    var errorFecha by remember { mutableStateOf(false) }
    var errorMotivo by remember { mutableStateOf(false) }
    var errorContacto by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Quiero colaborar con CORPALIV")

            Text(
                "Completa tus datos para que podamos conocer " +
                        "tu interés en colaborar."
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    errorNombre = false
                    enviado = false
                },
                label = { Text("Nombre completo") },
                isError = errorNombre,
                supportingText = {
                    if (errorNombre) {
                        Text("El nombre es obligatorio")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = correo,
                onValueChange = {
                    correo = it
                    errorCorreo = false
                    enviado = false
                },
                label = { Text("Correo electrónico") },
                isError = errorCorreo,
                supportingText = {
                    if (errorCorreo) {
                        Text("Introduce un correo válido")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = edad,
                onValueChange = {
                    if (it.all { caracter -> caracter.isDigit() }) {
                        edad = it
                        errorEdad = false
                        enviado = false
                    }
                },
                label = { Text("Edad") },
                isError = errorEdad,
                supportingText = {
                    if (errorEdad) {
                        Text("Introduce una edad entre 18 y 120 años")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fecha,
                onValueChange = {
                    fecha = it
                    errorFecha = false
                    enviado = false
                },
                label = { Text("Fecha de disponibilidad (DD/MM/AAAA)") },
                isError = errorFecha,
                supportingText = {
                    if (errorFecha) {
                        Text("Introduce una fecha válida con formato DD/MM/AAAA")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = motivo,
                onValueChange = {
                    motivo = it
                    errorMotivo = false
                    enviado = false
                },
                label = { Text("¿Cómo te gustaría colaborar?") },
                isError = errorMotivo,
                supportingText = {
                    if (errorMotivo) {
                        Text("Cuéntanos cómo quieres colaborar")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            RowColaboracion(
                texto = "Acepto que CORPALIV me contacte",
                marcado = aceptaContacto,
                onCambio = {
                    aceptaContacto = it
                    errorContacto = false
                    enviado = false
                }
            )

            if (errorContacto) {
                Text("Debes aceptar el contacto para enviar la solicitud")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tengo disponibilidad para colaborar regularmente",
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = disponibilidad,
                    onCheckedChange = {
                        disponibilidad = it
                        enviado = false
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Quiero recibir novedades de CORPALIV",
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = recibirNovedades,
                    onCheckedChange = {
                        recibirNovedades = it
                        enviado = false
                    }
                )
            }

            Button(
                onClick = {
                    errorNombre = nombre.isBlank()

                    errorCorreo =
                        !android.util.Patterns.EMAIL_ADDRESS
                            .matcher(correo.trim())
                            .matches()

                    val edadNumero = edad.toIntOrNull()
                    errorEdad = edadNumero == null ||
                            edadNumero < 18 ||
                            edadNumero > 120

                    errorFecha = !fecha.matches(
                        Regex("""\d{2}/\d{2}/\d{4}""")
                    )

                    errorMotivo = motivo.isBlank()
                    errorContacto = !aceptaContacto

                    enviado = !errorNombre &&
                            !errorCorreo &&
                            !errorEdad &&
                            !errorFecha &&
                            !errorMotivo &&
                            !errorContacto
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar solicitud")
            }

            if (enviado) {
                Text("Solicitud validada correctamente.")
                Text(
                    "Disponibilidad regular: " +
                            if (disponibilidad) "Sí" else "No"
                )
                Text(
                    "Recibir novedades: " +
                            if (recibirNovedades) "Sí" else "No"
                )
            }

            Button(
                onClick = onVolverClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al inicio")
            }
        }
    }
}

@Composable
private fun RowColaboracion(
    texto: String,
    marcado: Boolean,
    onCambio: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = texto,
            modifier = Modifier.weight(1f)
        )

        Checkbox(
            checked = marcado,
            onCheckedChange = onCambio
        )
    }
}
