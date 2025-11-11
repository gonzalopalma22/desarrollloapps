package com.example.booksyfinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booksyfinal.viewmodel.UserViewModel

@Composable
fun RegisterScreen(
    userViewModel: UserViewModel = viewModel(),
    onLoginClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registro de Usuario", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(20.dp))

        // 🧠 Campo: Nombre completo
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre completo") },
            singleLine = true
        )

        // ✉️ Campo: Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            singleLine = true,
            placeholder = { Text("ejemplo@correo.com") }
        )

        // 🔒 Campo: Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        // 🔒 Campo: Confirmar contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))

        // ✅ Botón de registro
        Button(onClick = {
            when {
                name.isBlank() || email.isBlank() || password.isBlank() -> {
                    error = "Completa todos los campos"
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    error = "Correo electrónico inválido"
                }
                password != confirmPassword -> {
                    error = "Las contraseñas no coinciden"
                }
                password.length < 6 -> {
                    error = "La contraseña debe tener al menos 6 caracteres"
                }
                else -> {
                    userViewModel.register(name, email, password)
                    error = "Cuenta creada correctamente ✅"
                }
            }
        }) {
            Text("Registrarse")
        }

        Spacer(Modifier.height(8.dp))

        // 🔁 Ir al login
        TextButton(onClick = onLoginClick) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }

        // ⚠️ Mostrar errores
        if (error.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            Text(error, color = MaterialTheme.colorScheme.error)
        }
    }
}
