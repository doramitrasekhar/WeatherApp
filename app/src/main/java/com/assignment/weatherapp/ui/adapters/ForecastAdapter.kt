package com.assignment.weatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.weatherapp.databinding.ForecastItemBinding
import com.assignment.weatherapp.entities.ForecastResult
import com.assignment.weatherapp.ui.viewholder.ForeCastItemViewHolder
import com.assignment.weatherapp.ui.diffutil.ForecastAdapterDiffUtil

class ForecastAdapter :
    RecyclerView.Adapter<ForeCastItemViewHolder>() {

    private var forecastItems = ArrayList<ForecastResult>()

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

    fun updateForecastItems(newForecastItems: List<ForecastResult>) {
        val diffCallback = ForecastAdapterDiffUtil(forecastItems, newForecastItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        with(forecastItems) {
            clear()
            addAll(newForecastItems)
        }
        diffResult.dispatchUpdatesTo(this)
    }
}