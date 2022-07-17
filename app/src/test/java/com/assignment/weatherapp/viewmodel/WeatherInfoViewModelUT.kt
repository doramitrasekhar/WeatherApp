package com.assignment.weatherapp.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.assignment.data.util.Constants
import com.assignment.domain.entities.WeatherEntityInfo
import com.assignment.domain.usecases.SaveWeatherInfoUseCase
import com.assignment.domain.usecases.WeatherInfoUseCase
import com.assignment.weatherapp.R
import com.assignment.weatherapp.core.TestCoroutineRule
import com.assignment.weatherapp.core.MockResponse.countryName
import com.assignment.weatherapp.core.MockResponse.getErrorResult
import com.assignment.weatherapp.core.MockResponse.getLiveDataErrorResult
import com.assignment.weatherapp.core.MockResponse.getLiveDataSuccessResult
import com.assignment.weatherapp.core.MockResponse.getSuccessResult
import com.assignment.weatherapp.core.MockResponse.getWeatherResult
import com.assignment.weatherapp.mappers.WeatherInfoErrorViewMapper
import com.assignment.weatherapp.mappers.WeatherInfoResultMapper
import com.assignment.weatherapp.util.WeatherInfoState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
    private val weatherInfoUseCase = mockk<WeatherInfoUseCase>()
    private val saveWeatherInfoUseCase = mockk<SaveWeatherInfoUseCase>()
    private val weatherInfoResultMapper = WeatherInfoResultMapper()
    private val mContextMock = mockk<Context>(relaxed = true)
    private val weatherInfoErrorViewMapper = WeatherInfoErrorViewMapper(mContextMock)

    @MockK
    private var apiUsersObserver = mockk<Observer<WeatherInfoState>>(relaxed = true)

    @Before
    fun setUp() {
        weatherInfoViewModel = WeatherInfoViewModel(
            weatherInfoUseCase, saveWeatherInfoUseCase,
            weatherInfoErrorViewMapper, weatherInfoResultMapper
        )
    }

    @Test
    fun when_GetWeatherInfo_Expect_WeatherInfoDetails() = runTest {
        //GIVEN
        coEvery { weatherInfoUseCase.invoke(countryName) } returns getSuccessResult()
        coEvery {
            saveWeatherInfoUseCase.invoke(
                weatherEntityInfo = WeatherEntityInfo(
                    countryName = countryName,
                    weatherInfo = getWeatherResult()
                )
            )
        } returns Unit
        // WHEN
        weatherInfoViewModel.getWeatherInfo(countryName)
        // THEN
        assertEquals(
            weatherInfoViewModel.weatherInfo.value, WeatherInfoState(
                data = weatherInfoResultMapper.mapToView(
                    getWeatherResult()
                )
            )
        )
    }

    @Test
    fun when_GetWeatherInfo_Expect_ErrorInfoDetails() = runTest {
        //GIVEN
        every { mContextMock.getString(R.string.unknown_error) } returns Constants.UNKNOWN
        coEvery { weatherInfoUseCase.invoke(countryName) } returns getErrorResult()
        // WHEN
        weatherInfoViewModel.getWeatherInfo(countryName)
        // THEN
        assertEquals(
            weatherInfoViewModel.weatherInfo.value?.error?.message, Constants.UNKNOWN
        )
    }

    @Test
    fun when_GetWeatherInfo_Expect_SuccessInLiveData() = runTest {
        //GIVEN
        coEvery { weatherInfoUseCase.invoke(countryName) } returns getSuccessResult()
        coEvery {
            saveWeatherInfoUseCase.invoke(
                weatherEntityInfo = WeatherEntityInfo(
                    countryName = countryName,
                    weatherInfo = getWeatherResult()
                )
            )
        } returns Unit
        // WHEN
        weatherInfoViewModel.getWeatherInfo(countryName)
        weatherInfoViewModel.weatherInfo
            .observeForever(apiUsersObserver)
        verify { apiUsersObserver.onChanged(getLiveDataSuccessResult()) }
        // THEN
        weatherInfoViewModel.weatherInfo.removeObserver(apiUsersObserver)
    }


    @Test
    fun when_GetWeatherInfo_Expect_ErrorInLiveData() = runTest {
        //GIVEN
        coEvery { weatherInfoUseCase.invoke(countryName) } returns getErrorResult()
        // WHEN
        weatherInfoViewModel.getWeatherInfo(countryName)
        weatherInfoViewModel.weatherInfo
            .observeForever(apiUsersObserver)
        verify { apiUsersObserver.onChanged(getLiveDataErrorResult(mContextMock)) }
        // THEN
        weatherInfoViewModel.weatherInfo.removeObserver(apiUsersObserver)
    }
}