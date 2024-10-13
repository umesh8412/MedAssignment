package com.umesh.medicineassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.umesh.medicineassignment.model.ApiResponse
import com.umesh.medicineassignment.model.Medicine
import com.umesh.medicineassignment.model.MedicineDb
import com.umesh.medicineassignment.model.Problem
import com.umesh.medicineassignment.network.ApiService
import com.umesh.medicineassignment.network.DbService
import com.umesh.medicineassignment.ui.ViewModel
import com.umesh.medicineassignment.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.lifecycle.Observer
import kotlinx.coroutines.flow.first
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ExampleUnitTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ViewModel
    private val apiService: ApiService = mock(ApiService::class.java)
    private val medicineDao: DbService = mock(DbService::class.java)
    private val logger: Logger = mock(Logger::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = ViewModel(apiService, medicineDao, logger)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `fetchMedicines should update medications when API call is successful`() = runTest {
        // Arrange
        val mockResponse = ApiResponse(problems = listOf(
            Problem(
                condition = "Headache",
                medications = listOf(
                    Medicine(name = "Test Medicine", dose = "500mg", strength = "Pain Reliever")
                ),
                labs = listOf()
            )
        ))
        whenever(apiService.getMedicines()).thenReturn(mockResponse)

        // Act
        viewModel.fetchMedicines()

        // Assert
        val medicines = viewModel.medications.first() // Collect the first emitted value
        assertNotNull(medicines) // Ensure it's not null
        assertEquals(1, medicines.size)
        assertEquals("Test Medicine", medicines[0].name)
    }
    @Test
    fun `fetchMedicines should not update medications when API call fails`() = runTest {
        `when`(apiService.getMedicines()).thenThrow(RuntimeException("Network Error"))
        viewModel.fetchMedicines()
        assertTrue(viewModel.medications.value.isEmpty())
    }

    @Test
    fun `insertMedicine should call Dao insert method and check existence`() = runTest {
        val medicine = MedicineDb(name = "Test Medicine", dose = "500mg", strength = "Pain Reliever")
        `when`(medicineDao.medicineExists(medicine.name)).thenReturn(1)
        viewModel.insertMedicine(medicine)
        verify(medicineDao).insertMedicine(medicine)
        assertEquals(1, viewModel.checkMedicineExists(medicine.name))
    }

    @Test
    fun `deleteMedicine should call Dao delete method and check existence`() = runTest {
        val medicine = MedicineDb(name = "Test Medicine", dose = "500mg", strength = "Pain Reliever")
        `when`(medicineDao.medicineExists(medicine.name)).thenReturn(0)
        viewModel.deleteMedicine(medicine)
        verify(medicineDao).deleteMedicine(medicine)
        assertEquals(0, viewModel.checkMedicineExists(medicine.name))
    }

    @Test
    fun `checkMedicineExists should return correct existence state`() = runTest {
        val medicineName = "Test Medicine"
        `when`(medicineDao.medicineExists(medicineName)).thenReturn(1)
        val exists = viewModel.checkMedicineExists(medicineName)
        assertEquals(1, exists)
    }
}