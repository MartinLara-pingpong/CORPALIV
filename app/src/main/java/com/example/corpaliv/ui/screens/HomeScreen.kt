
package com.example.corpaliv.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onRegistroClick: () -> Unit,
    onLoginClick: () -> Unit,
    onColaborarClick: () -> Unit,
    onSeccionClick: (String) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "CORPALIV",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Inclusión que transforma vidas",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            "Conoce nuestra labor y descubre cómo puedes colaborar."
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Nuestro propósito",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "Promovemos la autonomía y la inclusión social y " +
                            "laboral de personas con discapacidad múltiple."
                )
            }
        }

        Text(
            "Descubre CORPALIV",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedButton(
            onClick = { onSeccionClick("institucion") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Quiénes somos")
        }

        OutlinedButton(
            onClick = { onSeccionClick("programas") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Programas de inclusión")
        }

        OutlinedButton(
            onClick = { onSeccionClick("catalogo") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Productos y servicios")
        }

        OutlinedButton(
            onClick = { onSeccionClick("historias") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Historias de impacto")
        }

        Button(
            onClick = onColaborarClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Quiero colaborar")
        }

        OutlinedButton(
            onClick = {
                val compartir = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_SUBJECT,
                        "Conoce CORPALIV"
                    )
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Conoce CORPALIV y su labor de inclusión social " +
                                "y laboral de personas con discapacidad múltiple. " +
                                "Infórmate sobre sus programas y descubre cómo colaborar."
                    )
                }

                context.startActivity(
                    Intent.createChooser(
                        compartir,
                        "Compartir CORPALIV"
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Compartir CORPALIV")
        }

        OutlinedButton(
            onClick = onRegistroClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear una cuenta")
        }

        OutlinedButton(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }
    }
}
