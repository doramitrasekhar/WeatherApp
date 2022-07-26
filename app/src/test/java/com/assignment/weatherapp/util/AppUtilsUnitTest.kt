package com.assignment.weatherapp.util

import com.assignment.weatherapp.entities.WeatherStatus
import junit.framework.Assert.assertEquals
import org.junit.Test

class AppUtilsUnitTest {
    @Test
    fun `GIVEN GetCurrentDateTime When called Then Should retuen Current date time`(){
        val expected = "Tue, 26 Jul 2022"
        val result = AppUtils.getCurrentDateTime(AppConstants.DATE_FORMAT)
        assertEquals(expected,result)
    }

    @Test
    fun `GIVEN GetWeatherStatusFromTemperature When called Then Should return Sunny`(){
        val expected = WeatherStatus.SUNNY
        val result = AppUtils.getWeatherStatusFromTemperature(20.0)
        assertEquals(expected,result)
    }

    @Test
    fun `GIVEN GetWeatherStatusFromTemperature When called Then Should return Rainy`(){
        val expected = WeatherStatus.RAINY
        val result = AppUtils.getWeatherStatusFromTemperature(16.0)
        assertEquals(expected,result)
    }

    @Test
    fun `GIVEN GetWeatherStatusFromTemperature When called Then Should return Snowy`(){
        val expected = WeatherStatus.SNOWY
        val result = AppUtils.getWeatherStatusFromTemperature(-2.0)
        assertEquals(expected,result)
    }

    @Test
    fun `GIVEN GetNumberFromString When called Then Should return Number`(){
        val expected = "20"
        val result = AppUtils.getNumberFromString("20 km/h")
        assertEquals(expected,result)
    }

    @Test
    fun `GIVEN GetWeatherStatus When called Then Should return Sunny`(){
        val expected = WeatherStatus.SUNNY
        val result = AppUtils.getWeatherStatus("Sunny","20")
        assertEquals(expected,result)
    }

    @Test
    fun `GIVEN GetWeatherStatus When called Then Should return RainShower`(){
        val expected = WeatherStatus.RAINY
        val result = AppUtils.getWeatherStatus("Light rain shower","20")
        assertEquals(expected,result)
    }

    @Test
    fun `GIVEN GetWeatherStatus When called Then Should return Cloudy`(){
        val expected = WeatherStatus.CLOUDY
        val result = AppUtils.getWeatherStatus("Partly cloudy","20")
        assertEquals(expected,result)
    }

    @Test
    fun `GIVEN GetWeatherStatus When called Then Should return Clear`(){
        val expected = WeatherStatus.SUNNY
        val result = AppUtils.getWeatherStatus("Clear","20")
        assertEquals(expected,result)
    }

    @Test
    fun `GIVEN GetWeatherStatus When called Then Should return Rain`(){
        val expected = WeatherStatus.RAINY
        val result = AppUtils.getWeatherStatus("rain at times","20")
        assertEquals(expected,result)
    }

    @Test
    fun `GIVEN GetWeatherStatus When called Then Should return SnowAtTimes`(){
        val expected = WeatherStatus.SNOWY
        val result = AppUtils.getWeatherStatus("snow at times","20")
        assertEquals(expected,result)
    }

    @Test
    fun `GIVEN GetWeatherStatus When called Then Should return SunnyAtTimes`(){
        val expected = WeatherStatus.SUNNY
        val result = AppUtils.getWeatherStatus("mes","19")
        assertEquals(expected,result)
    }
}