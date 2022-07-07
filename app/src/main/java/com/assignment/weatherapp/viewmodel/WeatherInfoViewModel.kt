package com.assignment.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.domain.common.Result
import com.assignment.domain.usecases.WeatherInfoUseCase
import com.assignment.weatherapp.entities.WeatherInfoResult
import com.assignment.weatherapp.mappers.WeatherInfoResultMapper
import com.assignment.weatherapp.service.ServiceLocator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private val serviceLocator: ServiceLocator
) : ViewModel() {

    private val _dataLoading = MutableLiveData(false)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _weatherInfo = MutableLiveData<WeatherInfoResult>()
    val weatherInfo: LiveData<WeatherInfoResult>
        get() = _weatherInfo

    val getWeatherInfoUseCase: WeatherInfoUseCase
        get() = WeatherInfoUseCase(serviceLocator.provideWeatherRepository())

    fun getWeatherInfo(countryName: String) {
        viewModelScope.launch {
            _dataLoading.postValue(true)
            when (val weatherInfoResult = getWeatherInfoUseCase.invoke(countryName)) {
                is Result.Success -> {
                    _dataLoading.postValue(false)
                    _weatherInfo.postValue(WeatherInfoResultMapper().toWeatherInfo(weatherInfoResult.data))
                }
                is Result.Error -> {
                    _dataLoading.postValue(false)
                    _weatherInfo.postValue(null)
                }
            }
        }
    }
}