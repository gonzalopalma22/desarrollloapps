package com.example.booksyfinal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksyfinal.data.datastore.SessionManager
import com.example.booksyfinal.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val session = SessionManager(application.applicationContext)
    private val repository = UserRepository(session)

    val name = repository.name
    val email = repository.email
    val image = repository.image
    val isLogged = repository.isLogged

    private val _loggedIn = MutableStateFlow(false)
    val loggedIn: StateFlow<Boolean> = _loggedIn.asStateFlow()

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.register(name, email, password)
        }
    }

    fun login(email: String, password: String): Boolean {
        var success = false
        viewModelScope.launch {
            success = repository.login(email, password)
            _loggedIn.value = success
        }
        return success
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _loggedIn.value = false
        }
    }

    fun saveProfileImage(uri: String) {
        viewModelScope.launch {
            repository.saveImage(uri)
        }
    }
}
