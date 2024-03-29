package usecase

import util.MockResponse
import com.assignment.domain.entities.WeatherEntityInfo
import com.assignment.domain.repositories.WeatherRepository
import com.assignment.domain.usecases.SaveWeatherInfoUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SaveWeatherInfoUseCaseUnitTest {
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
    fun `GIVEN SaveWeatherInfoUseCase WHEN called THEN should return Unit`() = runTest {
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