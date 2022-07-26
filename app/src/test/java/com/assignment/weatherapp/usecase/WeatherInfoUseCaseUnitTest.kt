package com.assignment.weatherapp.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.data.util.Constants
import com.assignment.domain.common.Result
import com.assignment.domain.repositories.WeatherRepository
import com.assignment.domain.usecases.GetWeatherInfoUseCase
import com.assignment.weatherapp.core.MockResponse.countryName
import com.assignment.weatherapp.core.MockResponse.error_message
import com.assignment.weatherapp.core.MockResponse.getErrorResult
import com.assignment.weatherapp.core.MockResponse.getSuccessResult
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
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
class WeatherInfoUseCaseUnitTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @MockK
    private val weatherRepository = mockk<WeatherRepository>(relaxed = true)

    private lateinit var weatherInfoUseCase: GetWeatherInfoUseCase

    @Before
    fun setUp() {
        weatherInfoUseCase = GetWeatherInfoUseCase(
            weatherRepository
        )
    }

    @Test
    fun `GIVEN GetWeatherInfoUseCase When called Then Should return WeatherInfoDescription `() = runTest {
        coEvery { weatherRepository.getRemoteWeatherInfo(countryName) } returns getSuccessResult()
        when (val weatherInfoResult = weatherInfoUseCase.invoke(countryName)) {
            is Result.Success -> {
                assertEquals(weatherInfoResult.data?.description, "Clear")
            }
            else -> {
                assertTrue(error_message, true)
            }
        }
    }

    @Test
    fun `GIVEN GetWeatherInfoUseCase When called Then Should return WeatherInfoTemperature`() = runTest {
        coEvery { weatherRepository.getRemoteWeatherInfo(countryName) } returns getSuccessResult()
        when (val weatherInfoResult = weatherInfoUseCase.invoke(countryName)) {
            is Result.Success -> {
                assertEquals(weatherInfoResult.data?.temperature, "14 °C")
            }
            else -> {
                assertTrue(error_message, true)
            }
        }
    }

    @Test
    fun `GIVEN GetWeatherInfoUseCase When called Then Should return Error`() = runTest {
        coEvery { weatherRepository.getRemoteWeatherInfo(countryName) } returns getErrorResult()
        when (val weatherInfoResult = weatherInfoUseCase.invoke(countryName)) {
            is Result.Error -> {
                assertEquals(weatherInfoResult.message, Constants.WRONG_INPUT)
            }
            else -> {
                assertTrue(error_message, true)
            }
        }
    }
}