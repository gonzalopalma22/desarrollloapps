package com.example.booksyfinal.ui.screens

import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.booksyfinal.ui.components.ImagenInteligente
import com.example.booksyfinal.viewmodel.ProfileViewModel
import java.io.File

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val imageUri by viewModel.imageUri.collectAsState()

    // 📸 Archivo temporal para la cámara (crear solo cuando se use)
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    // 👉 Lanzador para galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.setImageFromGallery(uri)
    }

    // 👉 Lanzador para cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            viewModel.setImageFromCamera(cameraImageUri)
        }
    }

    // 🧱 UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ImagenInteligente(imageUri)

            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text("📁 Seleccionar desde galería")
            }

            Button(onClick = {
                // Crear archivo temporal SOLO al usar la cámara
                val photoFile = File.createTempFile(
                    "booksy_photo_", ".jpg",
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                )
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    photoFile
                )
                cameraImageUri = uri
                cameraLauncher.launch(uri)
            }) {
                Text("📷 Tomar foto con cámara")
            }
        }
    }
}
