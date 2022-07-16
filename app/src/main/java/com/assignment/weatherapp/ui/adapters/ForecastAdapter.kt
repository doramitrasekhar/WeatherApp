package com.assignment.weatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.assignment.weatherapp.R
import com.assignment.weatherapp.databinding.ForecastItemBinding
import com.assignment.weatherapp.entities.ForecastResult
import com.assignment.weatherapp.entities.WeatherStatus
import com.assignment.weatherapp.util.AppConstants.COLON
import com.assignment.weatherapp.util.AppConstants.DAY_TITLE
import com.assignment.weatherapp.util.AppConstants.NOT_AVAILABLE_TITLE
import com.assignment.weatherapp.util.AppConstants.SPACE
import com.assignment.weatherapp.util.AppConstants.WIND_TITLE
import com.assignment.weatherapp.util.AppUtils

class ForecastAdapter(private val forecastItems: List<ForecastResult>) :
    RecyclerView.Adapter<ForecastAdapter.ForeCastItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForeCastItemViewHolder {
        val binding =
            ForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForeCastItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForeCastItemViewHolder, position: Int) {
        val forecastItem = forecastItems[position]
        holder.bind(forecastItem)
    }

    override fun getItemCount(): Int {
        return forecastItems.size
    }

    inner class ForeCastItemViewHolder(private val viewHolder: ForecastItemBinding) :
        RecyclerView.ViewHolder(viewHolder.root) {

        fun bind(forecastItem: ForecastResult) {
            with(viewHolder) {
                textDayType.text = DAY_TITLE + SPACE + forecastItem.day
                textWindValue.text = WIND_TITLE + COLON + SPACE + forecastItem.wind
                textLabelDegree.visibility = View.VISIBLE
                val temp: String = AppUtils.getNumberFromString(forecastItem.temperature)
                when {
                    temp.isDigitsOnly() && temp.isNotEmpty() -> {
                        textTemperature.text = temp
                        updateImageWeatherSymbol(temp)
                    }
                    else -> {
                        textTemperature.text = NOT_AVAILABLE_TITLE
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
}