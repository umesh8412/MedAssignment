package com.umesh.medicineassignment

import android.content.Context
import androidx.room.Room
import com.umesh.medicineassignment.database.MedicineDatabase
import com.umesh.medicineassignment.model.MedicineDb
import com.umesh.medicineassignment.network.ApiService
import com.umesh.medicineassignment.network.DbService
import com.umesh.medicineassignment.util.AndroidLogger
import com.umesh.medicineassignment.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val DATABASE_NAME = "medicine_database"
    private const val BASE_URL = "https://api.mocky.io/v3/"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MedicineDatabase {
        return Room.databaseBuilder(
            context,
            MedicineDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMedicineDao(database: MedicineDatabase): DbService {
        return database.medicineDao()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    @Provides
    fun provideLogger(): Logger {
        return AndroidLogger() // or your implementation of Logger
    }

}
