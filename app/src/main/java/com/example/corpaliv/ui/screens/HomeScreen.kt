
package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onRegistroClick: () -> Unit,
    modifier: Modifier = Modifier
    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "CORPALIV",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Inclusión que transforma vidas",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Conoce nuestra labor y descubre cómo puedes colaborar " +
                    "para construir una sociedad más inclusiva."
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "¿Quiénes somos?",
            style = MaterialTheme.typography.headlineSmall
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Nuestro propósito",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Promovemos la autonomía y la inclusión social " +
                            "y laboral de personas con discapacidad."
                )
            }
        }

        Text(
            text = "¿Cómo puedes participar?",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Descubre nuestros programas, conoce nuestros productos " +
                    "y encuentra oportunidades para colaborar con CORPALIV."
        )

        Button(
            onClick = onRegistroClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear una cuenta")
        }

        OutlinedButton(
            onClick = onRegistroClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Quiero colaborar")
        }
    }
}
