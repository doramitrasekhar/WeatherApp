package util

import com.assignment.domain.common.ErrorEntity
import com.assignment.domain.common.Result
import com.assignment.domain.entities.WeatherInfo
import com.google.gson.Gson

object MockResponse {
    const val countryName = "LONDON"

    const val error_message = "something went wrong"

    fun getSuccessResult(): Result<WeatherInfo> {
        return Result.Success(getWeatherResult())
    }

    fun getErrorResult(): Result.Error<WeatherInfo> {
        return Result.Error(
            message = "Wrong Input",
            errorEntity = ErrorEntity.Unknown
        )
    }

    fun getWeatherResult(): WeatherInfo {
        val jsonString =
            "{\"temperature\":\"14 °C\",\"wind\":\"7 km/h\",\"description\":\"Clear\",\"forecast\":[{\"day\":\"1\",\"temperature\":\"25 °C\",\"wind\":\"18 km/h\"},{\"day\":\"2\",\"temperature\":\" °C\",\"wind\":\" km/h\"},{\"day\":\"3\",\"temperature\":\"10 °C\",\"wind\":\" km/h\"}]}"
        return Gson().fromJson(jsonString, WeatherInfo::class.java)
    }
}