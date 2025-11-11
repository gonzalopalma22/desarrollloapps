package com.example.booksyfinal.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {

    // URI de la imagen (galería o cámara)
    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    // Actualizar imagen desde galería
    fun setImageFromGallery(uri: Uri?) {
        _imageUri.value = uri
    }

    // Actualizar imagen desde cámara
    fun setImageFromCamera(uri: Uri?) {
        _imageUri.value = uri
    }
}
