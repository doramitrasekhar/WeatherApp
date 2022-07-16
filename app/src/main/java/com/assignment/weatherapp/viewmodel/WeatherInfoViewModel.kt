package com.assignment.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.domain.common.Result
import com.assignment.domain.entities.WeatherInfo
import com.assignment.domain.usecases.SaveWeatherInfoUseCase
import com.assignment.domain.usecases.WeatherInfoUseCase
import com.assignment.weatherapp.mappers.WeatherInfoErrorViewMapper
import com.assignment.weatherapp.mappers.WeatherInfoResultMapper
import com.assignment.weatherapp.util.WeatherInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private val weatherInfoUseCase: WeatherInfoUseCase,
    private val saveWeatherInfoUseCase: SaveWeatherInfoUseCase,
    private val weatherInfoErrorViewMapper: WeatherInfoErrorViewMapper,
    private val weatherInfoResultMapper: WeatherInfoResultMapper
) : ViewModel() {

    private val _weatherInfo = MutableLiveData<WeatherInfoState>()
    val weatherInfo: LiveData<WeatherInfoState>
        get() = _weatherInfo

    fun getWeatherInfo(countryName: String) {
        viewModelScope.launch {
            _weatherInfo.postValue(WeatherInfoState(isLoading = true))
            when (val weatherInfoResult = weatherInfoUseCase(countryName)) {
                is Result.Success -> {
                    val weatherInfo = weatherInfoResult.data
                    weatherInfo?.let {
                        /// update the weather Info to UI
                        updateWeatherInfoToUI(weatherInfo)
                        /// update the result to Local Database
                        updateResultToLocalDatabase(countryName, weatherInfo)
                    }
                }
                is Result.Error -> {
                    updateErrorResult(weatherInfoResult)
                }
            }
        }
    }

    /// updates the error result
    private fun updateErrorResult(weatherInfoResult: Result<WeatherInfo>) {
        _weatherInfo.postValue(
            WeatherInfoState(
                error = weatherInfoErrorViewMapper.mapToView(
                    weatherInfoResult.errorEntity
                )
            )
        )
    }

    /// update the result to local database
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

    /// Update the data to the fragment
    private fun updateWeatherInfoToUI(weatherInfo: WeatherInfo) {
        weatherInfo.also {
            _weatherInfo.postValue(
                WeatherInfoState(
                    data = weatherInfoResultMapper.mapToView(
                        weatherInfo
                    )
                )
            )
        }
    }
}