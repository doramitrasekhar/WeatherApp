package com.assignment.weatherapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.assignment.data.repositories.WeatherRepositoryImpl
import com.assignment.domain.common.Result
import com.assignment.domain.usecases.WeatherInfoUseCase
import com.assignment.weatherapp.TestCoroutineRule
import com.assignment.weatherapp.core.MockResponse.countryName
import com.assignment.weatherapp.core.MockResponse.error_message
import com.assignment.weatherapp.core.MockResponse.getErrorResource
import com.assignment.weatherapp.core.MockResponse.getSuccessResource
import com.assignment.weatherapp.core.MockResponse.getWeatherResult
import com.assignment.weatherapp.entities.WeatherInfoResult
import com.assignment.weatherapp.mappers.WeatherInfoResultMapper
import com.assignment.weatherapp.service.ServiceLocator
import com.assignment.weatherapp.util.AppConstants
import com.assignment.weatherapp.util.Resource
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

@ExperimentalCoroutinesApi
internal class WeatherInfoVMTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = TestCoroutineRule()

    @MockK
    private val serviceLocator = mockk<ServiceLocator>(relaxed = true)

    @MockK
    private val weatherRepositoryImpl = mockk<WeatherRepositoryImpl>(relaxed = true)

    @MockK
    private var apiUsersObserver = mockk<Observer<Resource<WeatherInfoResult>>>(relaxed = true)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }


    @Test
    fun getWeatherInfoTestOne() {
        coroutinesTestRule.runBlockingTest {
            val viewModel = WeatherInfoViewModel(serviceLocator)
            every { serviceLocator.provideWeatherRepository() } returns weatherRepositoryImpl
            val weatherInfoUseCase = WeatherInfoUseCase(weatherRepositoryImpl)
            coEvery { weatherInfoUseCase.invoke(countryName) } returns Result.Success(
                getWeatherResult()
            )
            viewModel.getWeatherInfo(countryName)
            Assert.assertEquals(viewModel.weatherInfo.value, getSuccessResource(getWeatherResult()))
        }
    }

    @Test
    fun getWeatherInfoTestLiveData() {
        coroutinesTestRule.runBlockingTest {
            val viewModel = WeatherInfoViewModel(serviceLocator)
            every { serviceLocator.provideWeatherRepository() } returns weatherRepositoryImpl
            val weatherInfoUseCase = WeatherInfoUseCase(weatherRepositoryImpl)
            coEvery { weatherInfoUseCase.invoke(countryName) } returns Result.Error(
                Exception(
                    error_message
                )
            )
            viewModel.getWeatherInfo(countryName)
            viewModel.weatherInfo
                .observeForever(apiUsersObserver)
            coVerify {weatherInfoUseCase.invoke(countryName)}
            verify { apiUsersObserver.onChanged(Resource.error(AppConstants.SOMETHING_WENT_WRONG, null))}
            viewModel.weatherInfo.removeObserver(apiUsersObserver)
        }
    }

    @Test
    fun getWeatherInfoTestTwo() {
        coroutinesTestRule.runBlockingTest {
            val viewModel = WeatherInfoViewModel(serviceLocator)
            every { serviceLocator.provideWeatherRepository() } returns weatherRepositoryImpl
            val weatherInfoUseCase = WeatherInfoUseCase(weatherRepositoryImpl)
            coEvery { weatherInfoUseCase.invoke(countryName) } returns Result.Error(
                Exception(
                    error_message
                )
            )
            viewModel.getWeatherInfo(countryName)
            Assert.assertEquals(viewModel.weatherInfo.value, getErrorResource())
        }
    }
}