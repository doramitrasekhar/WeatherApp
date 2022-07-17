package usecase

import util.MockResponse.countryName
import util.MockResponse.error_message
import util.MockResponse.getErrorResult
import util.MockResponse.getSuccessResult
import com.assignment.domain.common.Result
import com.assignment.domain.repositories.WeatherRepository
import com.assignment.domain.usecases.WeatherInfoUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherInfoUseCaseUnitTest {

    @MockK
    private val weatherRepository = mockk<WeatherRepository>(relaxed = true)

    private lateinit var weatherInfoUseCase: WeatherInfoUseCase

    @Before
    fun setUp() {
        weatherInfoUseCase = WeatherInfoUseCase(
            weatherRepository
        )
    }

    @Test
    fun when_WeatherInfoUseCase_Expect_WeatherInfoDescription() = runTest {
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
    fun when_WeatherInfoUseCase_Expect_WeatherInfoTemperature() = runTest {
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
    fun when_WeatherInfoUseCase_Expect_ErrorInfo() = runTest {
        coEvery { weatherRepository.getRemoteWeatherInfo(countryName) } returns getErrorResult()
        when (val weatherInfoResult = weatherInfoUseCase.invoke(countryName)) {
            is Result.Error -> {
                assertEquals(weatherInfoResult.message, "Wrong Input")
            }
            else -> {
                assertTrue(error_message, true)
            }
        }
    }
}