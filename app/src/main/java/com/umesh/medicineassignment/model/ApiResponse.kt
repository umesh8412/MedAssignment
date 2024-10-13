package com.umesh.medicineassignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ApiResponse(
    val problems: List<Problem> = emptyList()
)
data class Problem(
    val condition: String,
    val medications: List<Medicine> = emptyList(),
    val labs: List<Lab>
)

data class Medicine(
    val name: String,
    val dose: String,
    val strength: String
)

data class Lab(
    val missing_field: String
)

