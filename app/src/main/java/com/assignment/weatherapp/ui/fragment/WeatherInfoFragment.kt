package com.assignment.weatherapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.weatherapp.R
import com.assignment.weatherapp.databinding.WeatherInfoBinding
import com.assignment.weatherapp.entities.WeatherInfoResult
import com.assignment.weatherapp.entities.WeatherStatus
import com.assignment.weatherapp.ui.adapters.ForecastAdapter
import com.assignment.weatherapp.util.*
import com.assignment.weatherapp.util.AppConstants.COLON
import com.assignment.weatherapp.util.AppConstants.SERVICE_UNAVAILABLE
import com.assignment.weatherapp.util.AppConstants.SPACE
import com.assignment.weatherapp.util.AppConstants.WIND_TITLE
import com.assignment.weatherapp.viewmodel.WeatherInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class WeatherInfoFragment : Fragment() {

    private lateinit var _binding: WeatherInfoBinding
    private val weatherInfoViewModel: WeatherInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WeatherInfoBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(_binding){
            /// initialises the recycler view
            initializeRecyclerView(this)
            @Suppress("NAME_SHADOWING")
            inputFindCityWeather.setOnEditorActionListener { view, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if ((view as EditText).text.toString().isNotEmpty()) {
                        weatherInfoViewModel.getWeatherInfo(view.text.toString())
                    }
                }
                false
            }
            /// user info icon click listener
            userInfo.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
            /// listen to weatherInfo LiveData
            weatherInfoViewModel.weatherInfo.observe(viewLifecycleOwner) { weatherInfoState ->
                /// handle view loding state
                if (weatherInfoState.isLoading) {
                    LoadingScreen.displayLoadingWithText(
                        context,
                        getString(R.string.fetching_data),
                        false
                    )
                }
                /// handle error state
                weatherInfoState.error?.let {
                    LoadingScreen.hideLoading()
                    if (it.message == SERVICE_UNAVAILABLE) {
                        Toast.makeText(
                            context,
                            getString(R.string.service_unavailable),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else {
                        Toast.makeText(context, getString(R.string.wrong_input), Toast.LENGTH_LONG)
                            .show()
                    }
                }
                /// handle success state
                weatherInfoState.data?.let {
                    LoadingScreen.hideLoading()
                    /// update views
                    updateViewVisibility(this)
                    /// sets the adapter
                    recyclerViewSearchedCityTemperature.adapter =
                        ForecastAdapter(it.forecast)
                    /// sets text to views
                    setTextDetailsToView(this, it)
                }
            }
        }
    }

    /// sets the text details to view
    private fun setTextDetailsToView(binding: WeatherInfoBinding, weatherInfoResult: WeatherInfoResult) {
        binding.apply {
            textTodaysDate.text =
                AppUtils.getCurrentDateTime(AppConstants.DATE_FORMAT)
            textTemperature.text = AppUtils.getNumberFromString(weatherInfoResult.temperature)
            textCityName.text =
                inputFindCityWeather.text.toString().uppercase()
            weatherDescription.text = weatherInfoResult.description
            windInfo.text = WIND_TITLE + COLON + SPACE + weatherInfoResult.wind
            updateHumanReactionAndSymbol(
                this,
                weatherInfoResult.description,
                weatherInfoResult.temperature
            )
            inputFindCityWeather.text?.clear()
        }
    }

    /// updates the human reaction and symbol
    private fun updateHumanReactionAndSymbol(
        binding: WeatherInfoBinding,
        description: String,
        temperature: String
    ) {
        binding.apply {
            when (AppUtils.getWeatherStatus(
                description,
                temperature
            )) {
                WeatherStatus.SUNNY -> {
                    imageWeatherHumanReaction.setImageResource(R.drawable.sunny_day)
                    imageWeatherSymbol.setImageResource(R.drawable.ic_sunny)
                }
                WeatherStatus.RAINY -> {
                    imageWeatherHumanReaction.setImageResource(R.drawable.raining)
                    imageWeatherSymbol.setImageResource(R.drawable.ic_rainy)
                }
                WeatherStatus.SNOWY -> {
                    imageWeatherHumanReaction.setImageResource(R.drawable.raining)
                    imageWeatherSymbol.setImageResource(R.drawable.ic_rainy)
                }
                WeatherStatus.CLOUDY -> {
                    imageWeatherHumanReaction.setImageResource(R.drawable.logo)
                    imageWeatherSymbol.setImageResource(R.drawable.ic_cloudy)
                }
            }
        }
    }

    /// update the view visibility
    private fun updateViewVisibility(binding: WeatherInfoBinding) {
        binding.apply {
            textLabelSearchForCity.visibility = View.GONE
            imageCity.visibility = View.GONE
            constraintLayoutShowingTemp.visibility = View.VISIBLE
            recyclerViewSearchedCityTemperature.visibility = View.VISIBLE
        }
    }

    /// initialising the recycler view
    private fun initializeRecyclerView(binding: WeatherInfoBinding) {
        val mLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.apply {
            recyclerViewSearchedCityTemperature.apply {
                layoutManager = mLayoutManager
                itemAnimator = DefaultItemAnimator()
            }
        }
    }
}