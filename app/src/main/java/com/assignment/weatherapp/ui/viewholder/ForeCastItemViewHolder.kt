package com.assignment.weatherapp.ui.viewholder

import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.assignment.weatherapp.R
import com.assignment.weatherapp.databinding.ForecastItemBinding
import com.assignment.weatherapp.entities.ForecastResult
import com.assignment.weatherapp.entities.WeatherStatus
import com.assignment.weatherapp.util.AppConstants
import com.assignment.weatherapp.util.AppUtils

class ForeCastItemViewHolder(private val viewHolder: ForecastItemBinding) :
    RecyclerView.ViewHolder(viewHolder.root) {

    fun bind(forecastItem: ForecastResult) {
        with(viewHolder) {
            textDayType.text = String.format("%s %s",AppConstants.DAY_TITLE,forecastItem.day)
            textWindValue.text = String.format("%s: %s",AppConstants.WIND_TITLE,forecastItem.wind)
            textLabelDegree.visibility = View.VISIBLE
            val temp: String = AppUtils.getNumberFromString(forecastItem.temperature)
            when {
                temp.isDigitsOnly() && temp.isNotEmpty() -> {
                    textTemperature.text = temp
                    updateImageWeatherSymbol(temp)
                }
                else -> {
                    textTemperature.text = AppConstants.NOT_AVAILABLE_TITLE
                    textLabelDegree.visibility = View.GONE
                    imageWeatherSymbol.setImageResource(R.drawable.ic_sunny)
                }
            }
        }
    }

    private fun ForecastItemBinding.updateImageWeatherSymbol(temp: String) {
        with(imageWeatherSymbol) {
            when (AppUtils.getWeatherStatusFromTemperature(temp.toDouble())) {
                WeatherStatus.SUNNY -> setImageResource(R.drawable.ic_sunny)
                WeatherStatus.RAINY -> setImageResource(R.drawable.ic_rainy)
                WeatherStatus.SNOWY -> setImageResource(R.drawable.ic_rainy)
                WeatherStatus.CLOUDY -> setImageResource(R.drawable.ic_sunny)
            }
        }
    }
}