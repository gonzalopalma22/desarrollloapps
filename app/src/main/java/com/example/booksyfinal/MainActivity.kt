package com.example.booksyfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.booksyfinal.ui.navigation.AppNavigation
import com.example.booksyfinal.ui.theme.BooksyfinalTheme
import com.example.booksyfinal.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksyfinalTheme {
                val navController = rememberNavController()
                AppNavigation(
                    navController = navController,
                    userViewModel = userViewModel
                )
            }
        }
    }
}
