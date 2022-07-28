package com.assignment.weatherapp.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.assignment.weatherapp.R

object LoadingScreen {

    private lateinit var dialog: Dialog

    /// shows the loader with text
    fun displayLoadingWithText(
        context: Context?,
        text: String,
        cancelable: Boolean,
    ) {
        context?.let {
            dialog = Dialog(it)
            with(dialog) {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.weather_loader)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCancelable(cancelable)
                findViewById<TextView>(R.id.message).text = text
                try {
                    show()
                } catch (e: Exception) {
                }
            }
        }
    }

    /// hides the loader
    fun hideLoading() {
        if (this::dialog.isInitialized) {
            dialog.dismiss()
        }
    }
}