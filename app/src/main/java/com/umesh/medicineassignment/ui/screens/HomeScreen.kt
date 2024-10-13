package com.umesh.medicineassignment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.umesh.medicineassignment.model.Medicine
import com.umesh.medicineassignment.ui.ViewModel

@Composable
fun HomeScreen(username: String, navController: NavController) {
    val defaultUsername = "User" // Default username
    val displayName = username ?: defaultUsername // Use default if username is null or empty

    val greeting = when {
        java.time.LocalTime.now().hour < 12 -> "Good morning"
        java.time.LocalTime.now().hour < 18 -> "Good afternoon"
        else -> "Good evening"
    }
    val viewModel = hiltViewModel<ViewModel>()
    val medicines by viewModel.medications.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState() // Collect loading state


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "$greeting, $displayName!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            CircularProgressIndicator() // Show loading indicator
        } else {
            if (medicines.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(), // Fill the entire screen
                    contentAlignment = Alignment.Center // Center the content
                ) {
                    Text(
                        text = "No medicines available.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(medicines) { medicine ->
                        MedicineCard(medicine) { selectedMedicine ->
                            navController.navigate("medicineDetail/${selectedMedicine.name}/${selectedMedicine.dose}/${selectedMedicine.strength}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MedicineCard(medicine: Medicine, onClick: (Medicine) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = medicine.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Dose: ${medicine.dose}", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Strength: ${medicine.strength}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onClick(medicine) }) {
                Text("Details")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(username = "John Doe", navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun PreviewMedicineCard() {
    val sampleMedicine = Medicine(name = "Aspirin", dose = "500mg", strength = "100 tablets")
    MedicineCard(medicine = sampleMedicine, onClick = {})
}
