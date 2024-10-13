package com.umesh.medicineassignment.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umesh.medicineassignment.model.Medicine
import com.umesh.medicineassignment.model.MedicineDb
import com.umesh.medicineassignment.network.ApiService
import com.umesh.medicineassignment.network.DbService
import com.umesh.medicineassignment.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val apiService: ApiService,
    private val medicineDao: DbService,
    private val logger: Logger
) : ViewModel() {
    private val _medications = MutableStateFlow<List<Medicine>>(emptyList())
    val medications: StateFlow<List<Medicine>> = _medications

    private val _isLoading = MutableStateFlow(true) // Loading state
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchMedicines()
    }
    fun fetchMedicines() {
        viewModelScope.launch {
            _isLoading.value = true // Start loading
            try {
                val apiResponse = apiService.getMedicines()
                _medications.value = apiResponse.problems.flatMap { it.medications }.ifEmpty {
                    Log.w("MedicineViewModel", "No medications found.")
                    emptyList() // Set to empty if no medications
                }
            } catch (e: Exception) {
                logger.e("ViewModel", "Error fetching medicines: ${e.message}")
                _medications.value = emptyList() // Set to empty on error
            } finally {
                _isLoading.value = false // Stop loading
            }
        }
    }



    // ViewModel function example
    suspend fun checkMedicineExists(name: String): Int {
        return medicineDao.medicineExists(name) // This should return an Int
    }

    fun insertMedicine(medicine: MedicineDb) {
        viewModelScope.launch {
            medicineDao.insertMedicine(medicine)
            checkMedicineExists(medicine.name) // Update the state
        }
    }

    fun deleteMedicine(medicine: MedicineDb) {
        viewModelScope.launch {
            medicineDao.deleteMedicine(medicine)
            checkMedicineExists(medicine.name) // Update the state
        }
    }
}
