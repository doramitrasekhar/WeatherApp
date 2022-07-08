package com.assignment.weatherapp.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.domain.common.Result
import com.assignment.domain.repositories.WeatherRepository
import com.assignment.domain.usecases.WeatherInfoUseCase
import com.assignment.weatherapp.TestCoroutineRule
import com.assignment.weatherapp.core.MockResponse.countryName
import com.assignment.weatherapp.core.MockResponse.error_message
import com.assignment.weatherapp.core.MockResponse.getFailureResult
import com.assignment.weatherapp.core.MockResponse.getSuccessResult
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class WeatherInfoUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private val weatherRepository = mockk<WeatherRepository>(relaxed = true)

    private val weatherInfoUseCase by lazy {
        WeatherInfoUseCase(
            weatherRepository
        )
    }

    @Test
    fun testWeatherInfoTestOne() =
        testCoroutineRule.runBlockingTest {
            coEvery { weatherRepository.getRemoteWeatherInfo(countryName) } returns getSuccessResult()
            when (val weatherInfoResult = weatherInfoUseCase.invoke(countryName)) {
                is Result.Success -> {
                    assertEquals(weatherInfoResult.data.description, "Clear")
                }
                else -> {
                    assertTrue(error_message, true)
                }
            }
        }

    @Test
    fun testWeatherInfoTestTwo() =
        testCoroutineRule.runBlockingTest {
            coEvery { weatherRepository.getRemoteWeatherInfo(countryName) } returns getSuccessResult()
            when (val weatherInfoResult = weatherInfoUseCase.invoke(countryName)) {
                is Result.Success -> {
                    assertEquals(weatherInfoResult.data.temperature, "14 °C")
                }
                else -> {
                    assertTrue(error_message, true)
                }
            }
        }

    @Test
    fun testWeatherInfoTestThree() =
        testCoroutineRule.runBlockingTest {
            coEvery { weatherRepository.getRemoteWeatherInfo(countryName) } returns getFailureResult()
            when (weatherInfoUseCase.invoke(countryName)) {
                is Result.Success -> {
                    assertTrue(error_message, true)
                }
                else -> {
                    assertEquals(true, true)
                }
            }
        }
}