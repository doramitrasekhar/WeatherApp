package com.assignment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.assignment.data.entities.WeatherInfoEntity

@Database(entities = [WeatherInfoEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherInfoDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}