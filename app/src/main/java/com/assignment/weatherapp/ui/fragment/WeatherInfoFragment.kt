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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.weatherapp.R
import com.assignment.weatherapp.databinding.WeatherInfoBinding
import com.assignment.weatherapp.entities.ErrorUIModel
import com.assignment.weatherapp.entities.WeatherInfoResult
import com.assignment.weatherapp.entities.WeatherStatus
import com.assignment.weatherapp.ui.adapters.ForecastAdapter
import com.assignment.weatherapp.util.*
import com.assignment.weatherapp.util.AppConstants.COLON
import com.assignment.weatherapp.util.AppConstants.SERVICE_UNAVAILABLE
import com.assignment.weatherapp.util.AppConstants.SPACE
import com.assignment.weatherapp.util.AppConstants.WIND_TITLE
import com.assignment.weatherapp.util.WeatherInfoState.*
import com.assignment.weatherapp.viewmodel.WeatherInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherInfoFragment : Fragment() {

    private lateinit var _binding: WeatherInfoBinding
    private val weatherInfoViewModel: WeatherInfoViewModel by viewModels()
    private lateinit var forecastAdapter: ForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WeatherInfoBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(_binding) {
            /// initialises the recycler view
            initializeRecyclerView(this)
            inputFindCityWeather.setOnEditorActionListener { view, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE && (view as EditText).text.isNotEmpty()) {
                    weatherInfoViewModel.getWeatherInfo(view.text.toString())
                }
                false
            }
            /// user info icon click listener
            userInfo.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
            // listen for weather info state flow
            observeResultState()
        }
    }

    /**
     * Observe on Weather Info Result State
     */
    private fun observeResultState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherInfoViewModel.weatherInfo.collectLatest { weatherInfoState ->
                    when (weatherInfoState) {
                        is Loading -> handleLoadingState(isLoading = weatherInfoState.isLoading)
                        is Success -> handleSuccessState(weatherInfoResult = weatherInfoState.data)
                        is Error -> handleErrorState(errorUIModel = weatherInfoState.error)
                    }
                }
            }
        }
    }

    /**
     * Handles the loading State
     */
    private fun handleLoadingState(isLoading: Boolean) {
        if (isLoading) {
            LoadingScreen.displayLoadingWithText(
                context,
                getString(R.string.fetching_data),
                false
            )
        }
    }

    /**
     * Handles the Error State
     */
    private fun handleErrorState(errorUIModel: ErrorUIModel) {
        errorUIModel.let {
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
    }

    /**
     * Handles the success state
     */
    private fun handleSuccessState(
        weatherInfoResult: WeatherInfoResult
    ) {
        weatherInfoResult.let {
            LoadingScreen.hideLoading()
            /// update views
            updateViewVisibility(_binding)
            /// update the recycler view adapter
            forecastAdapter.updateForecastItems(it.forecast)
            /// sets text to views
            setTextDetailsToView(_binding, it)
        }
    }

    /**
     * sets the text details to view
     */
    private fun setTextDetailsToView(
        binding: WeatherInfoBinding,
        weatherInfoResult: WeatherInfoResult
    ) {
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

    /**
     * updates the human reaction and symbol
     */
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

    /**
     *  update the view visibility
     */
    private fun updateViewVisibility(binding: WeatherInfoBinding) {
        binding.apply {
            textLabelSearchForCity.visibility = View.GONE
            imageCity.visibility = View.GONE
            constraintLayoutShowingTemp.visibility = View.VISIBLE
            recyclerViewSearchedCityTemperature.visibility = View.VISIBLE
        }
    }

    /**
     * Initialising the recycler view
     */
    private fun initializeRecyclerView(binding: WeatherInfoBinding) {
        forecastAdapter = ForecastAdapter()
        binding.apply {
            recyclerViewSearchedCityTemperature.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                itemAnimator = DefaultItemAnimator()
                adapter = forecastAdapter
            }
        }
    }
}