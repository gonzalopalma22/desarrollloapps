package com.example.booksyfinal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.booksyfinal.ui.screens.HomeScreen
import com.example.booksyfinal.ui.screens.LoginScreen
import com.example.booksyfinal.ui.screens.ProfileScreen
import com.example.booksyfinal.ui.screens.RegisterScreen
import com.example.booksyfinal.viewmodel.UserViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val isLogged = userViewModel.isLogged.collectAsState(initial = false).value

    NavHost(
        navController = navController,
        startDestination = if (isLogged) "home" else "login"
    ) {
        // 🔹 Pantalla Login
        composable(route = "login") {
            LoginScreen(
                userViewModel = userViewModel,
                onRegisterClick = { navController.navigate("register") },
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // 🔹 Pantalla Registro
        composable(route = "register") {
            RegisterScreen(
                userViewModel = userViewModel,
                onLoginClick = { navController.popBackStack() }
            )
        }

        // 🔹 Pantalla Home
        composable(route = "home") {
            HomeScreen(
                userViewModel = userViewModel,
                onProfileClick = { navController.navigate("profile") },
                onLogoutClick = {
                    userViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }

        // 🔹 Pantalla Perfil (cámara + galería)
        composable(route = "profile") {
            ProfileScreen()
        }
    }
}
