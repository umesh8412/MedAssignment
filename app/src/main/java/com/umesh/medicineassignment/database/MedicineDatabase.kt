package com.umesh.medicineassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.umesh.medicineassignment.model.MedicineDb
import com.umesh.medicineassignment.network.DbService


@Database(entities = [MedicineDb::class], version = 1, exportSchema = false)
abstract class MedicineDatabase : RoomDatabase() {
    abstract fun medicineDao(): DbService

    companion object {
        @Volatile
        private var INSTANCE: MedicineDatabase? = null

        fun getDatabase(context: Context): MedicineDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, MedicineDatabase::class.java, "medicine_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}