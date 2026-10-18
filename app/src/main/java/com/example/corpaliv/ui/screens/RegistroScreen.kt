
package com.example.corpaliv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.corpaliv.ui.viewmodel.UsuarioViewModel

private val RegistroAzul = Color(0xFF102C50)
private val RegistroVerde = Color(0xFF35A879)
private val RegistroFondo = Color(0xFFF3F6FA)
private val RegistroGris = Color(0xFF63738A)

@Composable
fun RegistroScreen(
    onRegistroClick: () -> Unit,
    onVolverInicio: () -> Unit,
    usuarioViewModel: UsuarioViewModel
) {
    val uiState by usuarioViewModel.uiState.collectAsState()
    val errores by usuarioViewModel.errores.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RegistroFondo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(66.dp)
                .background(RegistroAzul, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "C",
                color = Color.White,
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Únete a CORPALIV",
            color = RegistroAzul,
            fontSize = 27.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Crea tu cuenta y forma parte de una sociedad más inclusiva.",
            color = RegistroGris,
            fontSize = 14.sp,
            lineHeight = 21.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(24.dp))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Text(
                text = "Crear cuenta",
                color = RegistroAzul,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Completa tus datos para registrarte.",
                color = RegistroGris,
                fontSize = 13.sp
            )

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = usuarioViewModel::actualizarNombre,
                label = { Text("Nombre completo") },
                isError = errores.nombre != null,
                supportingText = {
                    errores.nombre?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.correo,
                onValueChange = usuarioViewModel::actualizarCorreo,
                label = { Text("Correo electrónico") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                isError = errores.correo != null,
                supportingText = {
                    errores.correo?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.clave,
                onValueChange = usuarioViewModel::actualizarClave,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                isError = errores.clave != null,
                supportingText = {
                    errores.clave?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.direccion,
                onValueChange = usuarioViewModel::actualizarDireccion,
                label = { Text("Dirección") },
                isError = errores.direccion != null,
                supportingText = {
                    errores.direccion?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    usuarioViewModel.registrarCuenta { registrada ->
                        if (registrada) {
                            onRegistroClick()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RegistroVerde,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Crear mi cuenta",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Text(
                text = "Tus datos se utilizarán para gestionar tu cuenta.",
                color = RegistroGris,
                fontSize = 11.sp,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = onVolverInicio
        ) {
            Text(
                text = "← Volver al inicio",
                color = RegistroAzul,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
