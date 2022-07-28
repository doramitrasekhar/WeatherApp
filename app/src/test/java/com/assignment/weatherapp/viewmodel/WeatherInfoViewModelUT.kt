package com.assignment.weatherapp.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.data.util.Constants
import com.assignment.domain.entities.WeatherEntityInfo
import com.assignment.domain.usecases.GetWeatherInfoUseCase
import com.assignment.domain.usecases.SaveWeatherInfoUseCase
import com.assignment.weatherapp.R
import com.assignment.weatherapp.core.MockResponse.countryName
import com.assignment.weatherapp.core.MockResponse.getErrorResult
import com.assignment.weatherapp.core.MockResponse.getSuccessResult
import com.assignment.weatherapp.core.MockResponse.getWeatherResult
import com.assignment.weatherapp.core.TestCoroutineRule
import com.assignment.weatherapp.mappers.WeatherInfoErrorViewMapper
import com.assignment.weatherapp.mappers.WeatherInfoResultMapper
import com.assignment.weatherapp.util.WeatherInfoState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
internal class WeatherInfoViewModelUT {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = TestCoroutineRule()

    private lateinit var weatherInfoViewModel: WeatherInfoViewModel
    private val weatherInfoUseCase = mockk<GetWeatherInfoUseCase>()
    private val saveWeatherInfoUseCase = mockk<SaveWeatherInfoUseCase>()
    private val weatherInfoResultMapper = WeatherInfoResultMapper()
    private val mContextMock = mockk<Context>(relaxed = true)
    private val weatherInfoErrorViewMapper = WeatherInfoErrorViewMapper(mContextMock)

    @Before
    fun setUp() {
        weatherInfoViewModel = WeatherInfoViewModel(
            weatherInfoUseCase, saveWeatherInfoUseCase,
            weatherInfoErrorViewMapper, weatherInfoResultMapper
        )
    }

    @Test
    fun `GIVEN weatherInfoUseCase WHEN called THEN should return weather info response`() = runTest {
        coEvery { weatherInfoUseCase.invoke(countryName) } returns getSuccessResult()
        coEvery {
            saveWeatherInfoUseCase.invoke(
                weatherEntityInfo = WeatherEntityInfo(
                    countryName = countryName,
                    weatherInfo = getWeatherResult()
                )
            )
        } returns Unit

        weatherInfoViewModel.getWeatherInfo(countryName)

        val result = weatherInfoViewModel.weatherInfo.value is WeatherInfoState.Success
        assertTrue(result)
    }

    @Test
    fun `GIVEN weatherInfoUseCase WHEN called THEN should return error info response`() = runTest {
        every { mContextMock.getString(R.string.unknown_error) } returns Constants.UNKNOWN
        coEvery { weatherInfoUseCase.invoke(countryName) } returns getErrorResult()

        weatherInfoViewModel.getWeatherInfo(countryName)

        val data = weatherInfoViewModel.weatherInfo.value as WeatherInfoState.Error
        assertEquals(
            data.error.message, Constants.UNKNOWN)
    }
}