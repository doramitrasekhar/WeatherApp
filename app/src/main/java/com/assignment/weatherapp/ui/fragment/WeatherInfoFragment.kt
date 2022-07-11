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
import com.assignment.weatherapp.entities.WeatherStatus
import com.assignment.weatherapp.ui.adapters.ForecastAdapter
import com.assignment.weatherapp.util.AppConstants
import com.assignment.weatherapp.util.AppConstants.COLON
import com.assignment.weatherapp.util.AppConstants.SERVICE_UNAVAILABLE
import com.assignment.weatherapp.util.AppConstants.SPACE
import com.assignment.weatherapp.util.AppConstants.WIND_TITLE
import com.assignment.weatherapp.util.AppUtils
import com.assignment.weatherapp.util.LoadingScreen
import com.assignment.weatherapp.util.Status
import com.assignment.weatherapp.viewmodel.WeatherInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherInfoFragment : Fragment() {

    private var _binding: WeatherInfoBinding? = null
    private val binding get() = _binding!!
    private val weatherInfoViewModel: WeatherInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WeatherInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()
        @Suppress("NAME_SHADOWING")
        binding.inputFindCityWeather.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                weatherInfoViewModel.getWeatherInfo((view as EditText).text.toString())
            }
            false
        }
        binding.userInfo.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        weatherInfoViewModel.weatherInfo.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    LoadingScreen.hideLoading()
                    it?.data?.let {
                        /// update views
                        binding.textLabelSearchForCity.visibility = View.GONE
                        binding.imageCity.visibility = View.GONE
                        binding.constraintLayoutShowingTemp.visibility = View.VISIBLE
                        binding.recyclerViewSearchedCityTemperature.visibility = View.VISIBLE
                        /// sets the adapter
                        binding.recyclerViewSearchedCityTemperature.adapter =
                            ForecastAdapter(it.forecast)
                        /// sets text to views
                        binding.textTodaysDate.text =
                            AppUtils.getCurrentDateTime(AppConstants.DATE_FORMAT)
                        binding.textTemperature.text = AppUtils.getNumberFromString(it.temperature)
                        binding.textCityName.text =
                            binding.inputFindCityWeather.text.toString().uppercase()
                        binding.weatherDescription.text = it.description
                        binding.windInfo.text = WIND_TITLE + COLON + SPACE + it.wind
                        when (AppUtils.getWeatherStatus(it.description, it.temperature)) {
                            WeatherStatus.SUNNY -> {
                                binding.imageWeatherHumanReaction.setImageResource(R.drawable.sunny_day)
                                binding.imageWeatherSymbol.setImageResource(R.drawable.ic_sunny)
                            }
                            WeatherStatus.RAINY -> {
                                binding.imageWeatherHumanReaction.setImageResource(R.drawable.raining)
                                binding.imageWeatherSymbol.setImageResource(R.drawable.ic_rainy)
                            }
                            WeatherStatus.SNOWY -> {
                                binding.imageWeatherHumanReaction.setImageResource(R.drawable.raining)
                                binding.imageWeatherSymbol.setImageResource(R.drawable.ic_rainy)
                            }
                            WeatherStatus.CLOUDY -> {
                                binding.imageWeatherHumanReaction.setImageResource(R.drawable.logo)
                                binding.imageWeatherSymbol.setImageResource(R.drawable.ic_cloudy)
                            }
                        }
                        binding.inputFindCityWeather.text?.clear()
                    } ?: run {
                        Toast.makeText(context, getString(R.string.wrong_input), Toast.LENGTH_LONG)
                            .show()
                    }
                }
                Status.LOADING -> {
                    LoadingScreen.displayLoadingWithText(
                        context,
                        getString(R.string.fetching_data),
                        false
                    )
                }
                Status.ERROR -> {
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
        }
    }

    private fun initializeRecyclerView() {
        val mLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.recyclerViewSearchedCityTemperature.apply {
            layoutManager = mLayoutManager
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}