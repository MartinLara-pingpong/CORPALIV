
package com.example.corpaliv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.corpaliv.ui.viewmodel.UsuarioViewModel

private val LoginAzul = Color(0xFF102C50)
private val LoginVerde = Color(0xFF35A879)
private val LoginFondo = Color(0xFFF3F6FA)
private val LoginGris = Color(0xFF63738A)
private val LoginError = Color(0xFFB3261E)

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegistroClick: () -> Unit,
    onVolverInicio: () -> Unit,
    usuarioViewModel: UsuarioViewModel
) {
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf<String?>(null) }
    var cargando by rememberSaveable { mutableStateOf(false) }

    val errorCorreo = error != null
    val errorClave = error != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LoginFondo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .size(66.dp)
                    .background(LoginAzul, RoundedCornerShape(20.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
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
                text = "Qué bueno verte",
                color = LoginAzul,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Accede a tu cuenta de CORPALIV",
                color = LoginGris,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(24.dp))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = "Iniciar sesión",
                color = LoginAzul,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Introduce tus datos para continuar.",
                color = LoginGris,
                fontSize = 13.sp
            )

            OutlinedTextField(
                value = correo,
                onValueChange = {
                    correo = it
                    error = null
                },
                label = { Text("Correo electrónico") },
                placeholder = { Text("nombre@correo.com") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                isError = errorCorreo,
                trailingIcon = {
                    if (errorCorreo) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Error de acceso",
                            tint = LoginError
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = clave,
                onValueChange = {
                    clave = it
                    error = null
                },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                isError = errorClave,
                trailingIcon = {
                    if (errorClave) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Error de acceso",
                            tint = LoginError
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            if (error != null) {
                Text(
                    text = error!!,
                    color = LoginError,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            Button(
                onClick = {
                    if (correo.isBlank() || clave.isBlank()) {
                        error = "Completa el correo y la contraseña."
                    } else {
                        cargando = true
                        error = null

                        usuarioViewModel.iniciarSesion(
                            correo.trim(),
                            clave
                        ) { valido ->
                            cargando = false

                            if (valido) {
                                onLoginSuccess()
                            } else {
                                error = "Correo o contraseña incorrectos."
                            }
                        }
                    }
                },
                enabled = !cargando,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LoginVerde,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (cargando) "Comprobando..." else "Entrar",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            TextButton(
                onClick = onRegistroClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    color = LoginAzul
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onVolverInicio) {
            Text(
                text = "← Volver al inicio",
                color = LoginAzul,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}
