package com.example.booksyfinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.booksyfinal.viewmodel.UserViewModel

@Composable
fun HomeScreen(
    userViewModel: UserViewModel,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val name = userViewModel.name.collectAsState(initial = "").value
    val email = userViewModel.email.collectAsState(initial = "").value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Bienvenido a Booksy 📚", style = MaterialTheme.typography.headlineSmall)
            Text(text = "Usuario: $name")
            Text(text = "Correo: $email")

            Button(onClick = onProfileClick) {
                Text("Ver Perfil")
            }

            Button(
                onClick = onLogoutClick,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}
