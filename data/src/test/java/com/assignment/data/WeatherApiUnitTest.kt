package com.assignment.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.data.api.WeatherApi
import com.assignment.data.error.GeneralErrorHandlerImpl
import com.assignment.data.mappers.WeatherInfoResponseMapper
import com.assignment.data.repositories.WeatherInfoRemoteDataSourceImpl
import com.assignment.data.util.Constants
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class WeatherApiUnitTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val server = MockWebServer()
    private var mapper = WeatherInfoResponseMapper()
    private var errorHandler = GeneralErrorHandlerImpl()
    private var dispatcher = IO

    @Before
    fun init() {
        server.start(8000)
    }

    @Test
    fun when_GetWeatherInfo_Expect_WeatherInfoDetails() = runTest {
        val BASE_URL = server.url("/").toString()
        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(WeatherApi::class.java)
        val jsonString =
            "{\"temperature\":\"14 °C\",\"wind\":\"7 km/h\",\"description\":\"Clear\",\"forecast\":[{\"day\":\"1\",\"temperature\":\"25 °C\",\"wind\":\"18 km/h\"},{\"day\":\"2\",\"temperature\":\" °C\",\"wind\":\" km/h\"},{\"day\":\"3\",\"temperature\":\"10 °C\",\"wind\":\" km/h\"}]}"
        server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
        val repository = WeatherInfoRemoteDataSourceImpl(service, dispatcher, mapper, errorHandler)
        val resp = repository.getWeatherInfo("london")
        assertEquals(resp.data?.description, "Clear")
    }

    @Test
    fun when_GetWeatherInfo_Expect_WeatherErrorInfo() = runTest {
        val BASE_URL = server.url("/").toString()
        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(WeatherApi::class.java)
        server.enqueue(MockResponse().setResponseCode(400))
        val repository = WeatherInfoRemoteDataSourceImpl(service, dispatcher, mapper, errorHandler)
        val resp = repository.getWeatherInfo("london")
        assertEquals(resp.message, Constants.WRONG_INPUT)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}