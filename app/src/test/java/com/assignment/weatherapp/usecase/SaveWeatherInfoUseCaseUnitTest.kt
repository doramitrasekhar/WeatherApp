package com.assignment.weatherapp.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.domain.entities.WeatherEntityInfo
import com.assignment.domain.repositories.WeatherRepository
import com.assignment.domain.usecases.SaveWeatherInfoUseCase
import com.assignment.weatherapp.core.MockResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class SaveWeatherInfoUseCaseUnitTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @MockK
    private val weatherRepository = mockk<WeatherRepository>(relaxed = true)

    private lateinit var saveWeatherInfoUseCase: SaveWeatherInfoUseCase

    @Before
    fun setUp() {
        saveWeatherInfoUseCase = SaveWeatherInfoUseCase(
            weatherRepository
        )
    }

    @Test
    fun when_SaveWeatherInfoUseCase_Expect_Unit() = runTest {
        coEvery {
            weatherRepository.saveWeatherInfoInDB(
                weatherEntityInfo = WeatherEntityInfo(
                    countryName = MockResponse.countryName,
                    weatherInfo = MockResponse.getWeatherResult()
                )
            )
        } returns Unit
        weatherRepository.saveWeatherInfoInDB( weatherEntityInfo = WeatherEntityInfo(
            countryName = MockResponse.countryName,
            weatherInfo = MockResponse.getWeatherResult()
        ))
        coVerify {
            saveWeatherInfoUseCase.invoke(
                weatherEntityInfo = WeatherEntityInfo(
                    countryName = MockResponse.countryName,
                    weatherInfo = MockResponse.getWeatherResult()
                )
            )
        }
    }
}