package com.assignment.data.di

import android.util.Log
import com.assignment.data.BuildConfig
import com.assignment.data.api.WeatherApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val READ_TIMEOUT = 30
        private const val WRITE_TIMEOUT = 30
        private const val CONNECTION_TIMEOUT = 10
    }

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder().apply {
        baseUrl(BuildConfig.BASE_URL)
        client(client)
        addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().create())
        )
    }.build()

    @Provides
    @Singleton
    fun provideOkHttpClient(headerInterceptor: Interceptor): OkHttpClient = OkHttpClient().newBuilder().apply {
        readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        addInterceptor(headerInterceptor)
    }.build()

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor = ErrorInterceptor()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)
}

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            200 -> {
                Log.d(NETWORK_TAG, response.toString())
            }
            400 -> {
                Log.d(NETWORK_TAG, response.toString())
            }
            else -> {
                Log.d(NETWORK_TAG, response.toString())
            }
        }
        return response
    }

    companion object {
        private const val NETWORK_TAG = "NetworkModule"
    }
}