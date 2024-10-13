package com.umesh.medicineassignment.network

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.umesh.medicineassignment.model.MedicineDb

@Dao
interface DbService {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: MedicineDb)

    @Delete
    suspend fun deleteMedicine(medicine: MedicineDb)

    @Query("SELECT * FROM `table`")
    suspend fun getAllMedicines(): List<MedicineDb>

    @Query("SELECT * FROM `table` WHERE name = :medicineName LIMIT 1")
    suspend fun getMedicineByName(medicineName: String): MedicineDb?

    @Query("SELECT COUNT(*) FROM `table` WHERE name = :medicineName")
    suspend fun medicineExists(medicineName: String): Int // Returns count
}