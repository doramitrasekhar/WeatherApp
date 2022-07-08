package com.assignment.weatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.assignment.weatherapp.R
import com.assignment.weatherapp.entities.ForecastResult
import com.assignment.weatherapp.entities.WeatherStatus
import com.assignment.weatherapp.util.AppConstants.COLON
import com.assignment.weatherapp.util.AppConstants.DAY_TITLE
import com.assignment.weatherapp.util.AppConstants.NOT_AVAILABLE_TITLE
import com.assignment.weatherapp.util.AppConstants.SPACE
import com.assignment.weatherapp.util.AppConstants.WIND_TITLE
import com.assignment.weatherapp.util.AppUtils

class ForecastAdapter(private val forecastItems: List<ForecastResult>) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecastItem = forecastItems[position]
        holder.dayType.text = DAY_TITLE + SPACE + forecastItem.day
        holder.windVal.text = WIND_TITLE + COLON + SPACE + forecastItem.wind
        holder.labelDegree.visibility = View.VISIBLE
        val temp: String = AppUtils.getNumberFromString(forecastItem.temperature)
        if (temp.isDigitsOnly() && temp.isNotEmpty()) {
            holder.temperature.text = temp
            when (AppUtils.getWeatherStatusFromTemperature(temp.toDouble())) {
                WeatherStatus.SUNNY -> holder.weatherSymbols.setImageResource(R.drawable.ic_sunny)
                WeatherStatus.RAINY -> holder.weatherSymbols.setImageResource(R.drawable.ic_rainy)
                WeatherStatus.SNOWY -> holder.weatherSymbols.setImageResource(R.drawable.ic_rainy)
                WeatherStatus.CLOUDY -> holder.weatherSymbols.setImageResource(R.drawable.ic_sunny)
            }
        } else {
            holder.temperature.text = NOT_AVAILABLE_TITLE
            holder.labelDegree.visibility = View.GONE
            holder.weatherSymbols.setImageResource(R.drawable.ic_sunny)
        }
    }

    override fun getItemCount(): Int {
        return forecastItems.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val dayType: TextView = itemView.findViewById(R.id.text_day_type)
        val windVal: TextView = itemView.findViewById(R.id.text_wind_value)
        val temperature: TextView = itemView.findViewById(R.id.text_temperature)
        val weatherSymbols: ImageView = itemView.findViewById(R.id.image_weather_symbol)
        val labelDegree: TextView = itemView.findViewById(R.id.text_label_degree)
    }
}