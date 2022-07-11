package com.assignment.data.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.assignment.data.BuildConfig
import com.assignment.data.api.WeatherApi
import com.assignment.data.db.Converters
import com.assignment.data.db.WeatherDao
import com.assignment.data.db.WeatherInfoDatabase
import com.assignment.data.repositories.*
import com.assignment.data.util.Constants.WEATHER_INFO_DB_NAME
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    private val READ_TIMEOUT = 30
    private val WRITE_TIMEOUT = 30
    private val CONNECTION_TIMEOUT = 10
    private val CACHE_SIZE_BYTES = 10 * 1024 * 1024L

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(client).addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().create())
        ).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(headerInterceptor: Interceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return ErrorInterceptor()
    }

    @Provides
    @Singleton
    fun provideCache(context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        return Cache(httpCacheDirectory, CACHE_SIZE_BYTES)
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }


    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ): WeatherInfoDatabase {
        return Room.databaseBuilder(
            app,
            WeatherInfoDatabase::class.java,
            WEATHER_INFO_DB_NAME
        ).addTypeConverter(Converters()).build()
    }

    @Singleton
    @Provides
    fun provideYourDao(db: WeatherInfoDatabase): WeatherDao {
        return db.weatherDao()
    }

    @Provides
    @Singleton
    fun provideDataSourceImpl(weatherInfoRemoteSourceImpl: WeatherInfoRemoteDataSourceImpl): WeatherInfoRemoteDataSource =
        weatherInfoRemoteSourceImpl

    @Provides
    @Singleton
    fun provideLocalDataSourceImpl(weatherInfoLocalDataSourceImpl: WeatherInfoLocalDataSourceImpl): WeatherInfoLocalDataSource =
        weatherInfoLocalDataSourceImpl


    @Provides
    @Singleton
    fun provideWeatherRepoImpl(
        weatherInfoLocalDataSourceImpl: WeatherInfoLocalDataSourceImpl,
        weatherInfoRemoteSourceImpl: WeatherInfoRemoteDataSourceImpl
    ): WeatherRepositoryImpl {
        return WeatherRepositoryImpl(
            provideLocalDataSourceImpl(weatherInfoLocalDataSourceImpl),
            provideDataSourceImpl(weatherInfoRemoteSourceImpl)
        )
    }
}

class ErrorInterceptor : Interceptor {
    private val NETWORK_TAG = "NetworkModule"
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
}