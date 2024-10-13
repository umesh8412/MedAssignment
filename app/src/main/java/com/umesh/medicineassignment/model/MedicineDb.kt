package com.umesh.medicineassignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table")
data class MedicineDb(
    @PrimaryKey val name: String,
    val dose: String,
    val strength: String
)
