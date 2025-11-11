package com.example.booksyfinal.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
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
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    // Lanzador para pedir permiso de cámara
    val requestCameraPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Permiso de cámara concedido ✅", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permiso de cámara denegado ❌", Toast.LENGTH_SHORT).show()
            }
        }

    // Lanzador de galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.setImageFromGallery(it) }
    }

    // Lanzador de cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success && cameraImageUri != null) {
            viewModel.setImageFromCamera(cameraImageUri)
        } else {
            Toast.makeText(context, "No se tomó ninguna foto", Toast.LENGTH_SHORT).show()
        }
    }

    // UI principal
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

            // 📁 Botón galería
            Button(onClick = {
                galleryLauncher.launch("image/*")
            }) {
                Text("📁 Seleccionar desde galería")
            }

            // Botón cámara
            Button(onClick = {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED

                if (!hasPermission) {
                    // Solicita el permiso directamente
                    requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                } else {
                    // Ya tiene permiso → abrir cámara
                    try {
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
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "No se pudo abrir la cámara", Toast.LENGTH_SHORT).show()
                    }
                }
            }) {
                Text("📷 Tomar foto con cámara")
            }
        }
    }
}
