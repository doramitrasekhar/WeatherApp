package com.assignment.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.domain.common.Result
import com.assignment.domain.entities.WeatherInfo
import com.assignment.domain.usecases.GetWeatherInfoUseCase
import com.assignment.domain.usecases.SaveWeatherInfoUseCase
import com.assignment.weatherapp.mappers.WeatherInfoErrorViewMapper
import com.assignment.weatherapp.mappers.WeatherInfoResultMapper
import com.assignment.weatherapp.util.EspressoIdlingResource
import com.assignment.weatherapp.util.WeatherInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase,
    private val saveWeatherInfoUseCase: SaveWeatherInfoUseCase,
    private val weatherInfoErrorViewMapper: WeatherInfoErrorViewMapper,
    private val weatherInfoResultMapper: WeatherInfoResultMapper
) : ViewModel() {

    private val _weatherInfo: MutableStateFlow<WeatherInfoState> = MutableStateFlow(WeatherInfoState.Loading(isLoading = false))
    val weatherInfo: StateFlow<WeatherInfoState> = _weatherInfo

    /**
     * Fetches the weather Info
     */
    fun getWeatherInfo(countryName: String) {
        viewModelScope.launch {
            EspressoIdlingResource.increment()
            _weatherInfo.value = WeatherInfoState.Loading(isLoading = true)
            when (val weatherInfoResult = getWeatherInfoUseCase(countryName)) {
                is Result.Success -> {
                    EspressoIdlingResource.decrement()
                    val weatherInfo = weatherInfoResult.data
                    weatherInfo?.let {
                        /// update the weather Info to UI
                        updateWeatherInfoToUI(weatherInfo)
                        /// update the result to Local Database
                        updateResultToLocalDatabase(countryName, weatherInfo)
                    }
                }
                is Result.Error -> {
                    EspressoIdlingResource.decrement()
                    updateErrorResult(weatherInfoResult)
                }
            }
        }
    }

    /**
     * updates the error result
     */
    private fun updateErrorResult(weatherInfoResult: Result<WeatherInfo>) {
        _weatherInfo.value = (
            WeatherInfoState.Error(
                weatherInfoErrorViewMapper.mapToView(
                    weatherInfoResult.errorEntity
                )
            )
        )
    }

    /**
     * update the result to local database
     */
    private suspend fun updateResultToLocalDatabase(
        countryName: String,
        weatherInfo: WeatherInfo
    ) {
        saveWeatherInfoUseCase(
            weatherInfoResultMapper.mapToWeatherEntityInfo(
                countryName = countryName,
                input = weatherInfo
            )
        )
    }

    /**
     * Update the data to the fragment
     */
    private fun updateWeatherInfoToUI(weatherInfo: WeatherInfo) {
        _weatherInfo.value = (
            WeatherInfoState.Success(
                weatherInfoResultMapper.mapToView(
                    weatherInfo
                )
            )
        )
    }
}