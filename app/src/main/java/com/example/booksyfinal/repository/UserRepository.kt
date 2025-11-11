package com.example.booksyfinal.repository

import com.example.booksyfinal.data.datastore.SessionManager
import kotlinx.coroutines.flow.Flow

class UserRepository(private val session: SessionManager) {

    // Flujos de datos persistentes en DataStore
    val name: Flow<String> = session.name
    val email: Flow<String> = session.email
    val image: Flow<String> = session.image
    val isLogged: Flow<Boolean> = session.isLogged

    // Simula un usuario local temporal (email, password)
    private var localUser: Pair<String, String>? = null

    // Registrar usuario
    suspend fun register(name: String, email: String, password: String): Boolean {
        return if (email.isNotBlank() && password.isNotBlank()) {
            localUser = Pair(email, password)
            session.saveUser(name, email, password)
            true
        } else false
    }

    // Iniciar sesión validando con los datos guardados
    suspend fun login(emailInput: String, passwordInput: String): Boolean {
        val storedEmail = localUser?.first
        val storedPass = localUser?.second
        return if (storedEmail == emailInput && storedPass == passwordInput) {
            session.saveUser("Usuario", emailInput, passwordInput)
            true
        } else false
    }

    // Guardar imagen de perfil
    suspend fun saveImage(uri: String) {
        session.saveImage(uri)
    }

    // Cerrar sesión
    suspend fun logout() {
        session.logout()
    }
}
