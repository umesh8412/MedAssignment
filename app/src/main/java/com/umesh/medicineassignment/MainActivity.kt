package com.umesh.medicineassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.umesh.medicineassignment.ui.screens.HomeScreen
import com.umesh.medicineassignment.ui.screens.LoginScreen
import com.umesh.medicineassignment.ui.screens.MedicineDetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppNavigation()

        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "loginScreen") {
        composable("loginScreen") { LoginScreen(navController) }
        composable("homeScreen/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            HomeScreen(username, navController)
        }
        composable(
            "medicineDetail/{name}/{dose}/{strength}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("dose") { type = NavType.StringType },
                navArgument("strength") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val dose = backStackEntry.arguments?.getString("dose") ?: ""
            val strength = backStackEntry.arguments?.getString("strength") ?: ""

            MedicineDetailScreen(name, dose, strength)
        }

    }
}


