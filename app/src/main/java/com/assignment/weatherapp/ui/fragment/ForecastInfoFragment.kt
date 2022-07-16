package com.assignment.weatherapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.assignment.weatherapp.databinding.UserInfoBinding


class ForecastInfoFragment : Fragment() {
    private lateinit var _binding: UserInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserInfoBinding.inflate(inflater, container, false)
        return _binding.root
    }
}