package com.assignment.weatherapp.mappers

import android.content.Context
import com.assignment.domain.common.ErrorEntity
import com.assignment.weatherapp.R
import com.assignment.weatherapp.entities.ErrorUIModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WeatherInfoErrorViewMapper @Inject constructor(@ApplicationContext private val context: Context) :
    Mapper<ErrorUIModel, ErrorEntity?> {

    override fun mapToView(input: ErrorEntity?): ErrorUIModel {
        return ErrorUIModel(
            message = getMessage(input)
        )
    }

    private fun getMessage(errorEntity: ErrorEntity?): String {
        return when (errorEntity) {
            ErrorEntity.Network -> context.getString(R.string.network_error)
            ErrorEntity.Unknown -> context.getString(R.string.unknown_error)
            ErrorEntity.ServiceUnavailable -> context.getString(R.string.service_error)
            else -> context.getString(R.string.unknown_error)
        }
    }
}