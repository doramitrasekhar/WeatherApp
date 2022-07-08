package com.assignment.weatherapp.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.weatherapp.TestCoroutineRule
import com.assignment.weatherapp.entities.WeatherStatus
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class AppUtilsTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = TestCoroutineRule()

    @Test
    fun getCurrentDateTimeTest(){
        val expected = "Fri, 8 Jul 2022"
        val result = AppUtils.getCurrentDateTime(AppConstants.DATE_FORMAT)
        assertEquals(expected,result)
    }

    @Test
    fun getWeatherStatusFromTemperatureTest_One(){
        val expected = WeatherStatus.SUNNY
        val result = AppUtils.getWeatherStatusFromTemperature(20.0)
        assertEquals(expected,result)
    }

    @Test
    fun getWeatherStatusFromTemperatureTest_Two(){
        val expected = WeatherStatus.RAINY
        val result = AppUtils.getWeatherStatusFromTemperature(16.0)
        assertEquals(expected,result)
    }

    @Test
    fun getWeatherStatusFromTemperatureTest_Three(){
        val expected = WeatherStatus.SNOWY
        val result = AppUtils.getWeatherStatusFromTemperature(-2.0)
        assertEquals(expected,result)
    }

    @Test
    fun getNumberFromStringTest_One(){
        val expected = "20"
        val result = AppUtils.getNumberFromString("20 km/h")
        assertEquals(expected,result)
    }

    @Test
    fun getWeatherStatusTest_One(){
        val expected = WeatherStatus.SUNNY
        val result = AppUtils.getWeatherStatus("Sunny","20")
        assertEquals(expected,result)
    }

    @Test
    fun getWeatherStatusTest_Two(){
        val expected = WeatherStatus.RAINY
        val result = AppUtils.getWeatherStatus("Light rain shower","20")
        assertEquals(expected,result)
    }

    @Test
    fun getWeatherStatusTest_Three(){
        val expected = WeatherStatus.RAINY
        val result = AppUtils.getWeatherStatus("Light rain shower","20")
        assertEquals(expected,result)
    }

    @Test
    fun getWeatherStatusTest_Four(){
        val expected = WeatherStatus.CLOUDY
        val result = AppUtils.getWeatherStatus("Partly cloudy","20")
        assertEquals(expected,result)
    }

    @Test
    fun getWeatherStatusTest_Five(){
        val expected = WeatherStatus.SUNNY
        val result = AppUtils.getWeatherStatus("Clear","20")
        assertEquals(expected,result)
    }

    @Test
    fun getWeatherStatusTest_Seven(){
        val expected = WeatherStatus.RAINY
        val result = AppUtils.getWeatherStatus("rain at times","20")
        assertEquals(expected,result)
    }

    @Test
    fun getWeatherStatusTest_Eight(){
        val expected = WeatherStatus.SNOWY
        val result = AppUtils.getWeatherStatus("snow at times","20")
        assertEquals(expected,result)
    }

    @Test
    fun getWeatherStatusTest_Nine(){
        val expected = WeatherStatus.SUNNY
        val result = AppUtils.getWeatherStatus("mes","19")
        assertEquals(expected,result)
    }
}