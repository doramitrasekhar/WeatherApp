package com.assignment.data

import com.assignment.data.api.WeatherApiResponse
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherApiResponseUnitTest {
    @Test
    fun when_WeatherApiResponse_Expect_SameWeatherApiResponse() {
        val jsonString =
            "{\"temperature\":\"14 °C\",\"wind\":\"7 km/h\",\"description\":\"Clear\",\"forecast\":[{\"day\":\"1\",\"temperature\":\"25 °C\",\"wind\":\"18 km/h\"},{\"day\":\"2\",\"temperature\":\" °C\",\"wind\":\" km/h\"},{\"day\":\"3\",\"temperature\":\"10 °C\",\"wind\":\" km/h\"}]}"
        val response = Gson().fromJson(jsonString, WeatherApiResponse::class.java)
        val copyWeatherApiResponse = response.copy()
        assertEquals(response, copyWeatherApiResponse)
    }
}