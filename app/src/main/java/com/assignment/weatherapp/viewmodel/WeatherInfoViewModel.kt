package com.assignment.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.domain.common.Result
import com.assignment.domain.entities.WeatherInfo
import com.assignment.domain.usecases.SaveWeatherInfoUseCase
import com.assignment.domain.usecases.WeatherInfoUseCase
import com.assignment.weatherapp.entities.WeatherInfoResult
import com.assignment.weatherapp.mappers.WeatherInfoResultMapper
import com.assignment.weatherapp.service.ServiceLocator
import com.assignment.weatherapp.util.AppConstants.SOMETHING_WENT_WRONG
import com.assignment.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private val serviceLocator: ServiceLocator
) : ViewModel() {

    private val _weatherInfo = MutableLiveData<Resource<WeatherInfoResult>>()
    val weatherInfo: LiveData<Resource<WeatherInfoResult>>
        get() = _weatherInfo

    val getWeatherInfoUseCase: WeatherInfoUseCase
        get() = WeatherInfoUseCase(serviceLocator.provideWeatherRepository())

    val saveWeatherInfoUseCase: SaveWeatherInfoUseCase
        get() = SaveWeatherInfoUseCase(serviceLocator.provideWeatherRepository())

    fun getWeatherInfo(countryName: String) {
        viewModelScope.launch {
            _weatherInfo.postValue(Resource.loading(null))
            when (val weatherInfoResult: Result<WeatherInfo> = getWeatherInfoUseCase.invoke(countryName)) {
                is Result.Success -> {
                    val result = Resource.success(
                        WeatherInfoResultMapper().toWeatherInfo(
                            weatherInfoResult.data
                        )
                    )
                    /// save the weather info locally
                    saveWeatherInfoUseCase.invoke(countryName,weatherInfoResult.data)
                    /// post the data to livedata
                    _weatherInfo.postValue(result)
                    Timber.d(WeatherInfoViewModel::class.simpleName, result)
                }
                is Result.Error -> {
                    _weatherInfo.postValue(Resource.error(SOMETHING_WENT_WRONG, null))
                    Timber.d(WeatherInfoViewModel::class.simpleName, SOMETHING_WENT_WRONG)
                }
            }
        }
    }
}