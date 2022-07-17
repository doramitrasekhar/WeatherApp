package com.assignment.weatherapp.util

import com.assignment.weatherapp.entities.WeatherStatus
import junit.framework.Assert.assertEquals
import org.junit.Test

class AppUtilsUnitTest {
    @Test
    fun when_GetCurrentDateTime_Expect_CurrentDateTime(){
        val expected = "Sun, 17 Jul 2022"
        val result = AppUtils.getCurrentDateTime(AppConstants.DATE_FORMAT)
        assertEquals(expected,result)
    }

    @Test
    fun when_GetWeatherStatusFromTemperature_Expect_Sunny(){
        val expected = WeatherStatus.SUNNY
        val result = AppUtils.getWeatherStatusFromTemperature(20.0)
        assertEquals(expected,result)
    }

    @Test
    fun when_GetWeatherStatusFromTemperature_Expect_Rainy(){
        val expected = WeatherStatus.RAINY
        val result = AppUtils.getWeatherStatusFromTemperature(16.0)
        assertEquals(expected,result)
    }

    @Test
    fun when_GetWeatherStatusFromTemperature_Expect_Snowy(){
        val expected = WeatherStatus.SNOWY
        val result = AppUtils.getWeatherStatusFromTemperature(-2.0)
        assertEquals(expected,result)
    }

    @Test
    fun when_GetNumberFromString_Expect_Number(){
        val expected = "20"
        val result = AppUtils.getNumberFromString("20 km/h")
        assertEquals(expected,result)
    }

    @Test
    fun when_GetWeatherStatus_Expect_SunnyWeather(){
        val expected = WeatherStatus.SUNNY
        val result = AppUtils.getWeatherStatus("Sunny","20")
        assertEquals(expected,result)
    }

    @Test
    fun when_GetWeatherStatus_Expect_LightRain(){
        val expected = WeatherStatus.RAINY
        val result = AppUtils.getWeatherStatus("Light rain shower","20")
        assertEquals(expected,result)
    }

    @Test
    fun when_GetWeatherStatus_Expect_PartlyCloudy(){
        val expected = WeatherStatus.CLOUDY
        val result = AppUtils.getWeatherStatus("Partly cloudy","20")
        assertEquals(expected,result)
    }

    @Test
    fun when_GetWeatherStatus_Expect_Clear(){
        val expected = WeatherStatus.SUNNY
        val result = AppUtils.getWeatherStatus("Clear","20")
        assertEquals(expected,result)
    }

    @Test
    fun when_GetWeatherStatus_Expect_RainAtTimes(){
        val expected = WeatherStatus.RAINY
        val result = AppUtils.getWeatherStatus("rain at times","20")
        assertEquals(expected,result)
    }

    @Test
    fun when_GetWeatherStatus_Expect_SnowAtTimes(){
        val expected = WeatherStatus.SNOWY
        val result = AppUtils.getWeatherStatus("snow at times","20")
        assertEquals(expected,result)
    }

    @Test
    fun when_GetWeatherStatus_Expect_Mes(){
        val expected = WeatherStatus.SUNNY
        val result = AppUtils.getWeatherStatus("mes","19")
        assertEquals(expected,result)
    }
}