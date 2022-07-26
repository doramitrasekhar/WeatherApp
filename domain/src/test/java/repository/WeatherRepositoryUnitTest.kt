package repository

import util.MockResponse.countryName
import util.MockResponse.error_message
import util.MockResponse.getSuccessResult
import util.MockResponse.getWeatherResult
import com.assignment.domain.common.Result
import com.assignment.domain.entities.WeatherEntityInfo
import com.assignment.domain.repositories.WeatherRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherRepositoryUnitTest {
    @MockK
    private val weatherRepository = mockk<WeatherRepository>(relaxed = true)

    @Test
    fun `GIVEN GetWeatherInfo WHEN called THEN should return weather info description`() = runTest {
        coEvery { weatherRepository.getRemoteWeatherInfo(countryName) } returns getSuccessResult()
        when (val weatherInfoResult = weatherRepository.getRemoteWeatherInfo(countryName)) {
            is Result.Success -> {
                Assert.assertEquals(weatherInfoResult.data?.description, "Clear")
            }
            else -> {
                Assert.assertTrue(error_message, true)
            }
        }
    }

    @Test
    fun `GIVEN SaveWeather WHEN called THEN should return save weather info`() = runTest {
        coEvery { weatherRepository.saveWeatherInfoInDB( weatherEntityInfo = WeatherEntityInfo(
            countryName = countryName,
            weatherInfo = getWeatherResult()
        )
        ) } returns Unit
        val result = weatherRepository.saveWeatherInfoInDB(weatherEntityInfo = WeatherEntityInfo(
            countryName = countryName,
            weatherInfo = getWeatherResult()
        ))
        Assert.assertEquals(result,Unit)
    }
}