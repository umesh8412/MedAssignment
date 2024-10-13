package com.umesh.medicineassignment.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.umesh.medicineassignment.model.MedicineDb
import com.umesh.medicineassignment.ui.ViewModel

@Composable
fun MedicineDetailScreen(
    name: String,
    dose: String,
    strength: String,
    viewModel: ViewModel = hiltViewModel(),

) {
    var medicineExists by remember { mutableStateOf(false) }
    val medicine = MedicineDb(name = name, dose = dose, strength = strength)

    LaunchedEffect(medicine) {
        medicineExists = viewModel.checkMedicineExists(medicine.name) > 0 // Check existence
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Medicine Name: $name",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Dose: $dose",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Strength: $strength",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (medicineExists) {
                    // Delete the medicine from the Room database
                    viewModel.deleteMedicine(medicine)
                    medicineExists = false // Update state
                } else {
                    // Add the medicine to the Room database
                    viewModel.insertMedicine(medicine)
                    medicineExists = true // Update state
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (medicineExists) Color.Red else Color.Blue
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (medicineExists) "Delete from Favorites" else "Add to Favorites",
                color = Color.White
            )
        }
    }
}