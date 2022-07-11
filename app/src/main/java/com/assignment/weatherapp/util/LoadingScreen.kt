package com.assignment.weatherapp.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.assignment.weatherapp.R

object LoadingScreen {
    var dialog: Dialog? = null

    /// shows the loader with text
    fun displayLoadingWithText(
        context: Context?,
        text: String?,
        cancelable: Boolean
    ) {
        dialog = Dialog(context!!)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.weather_loader)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(cancelable)
        val textView = dialog!!.findViewById<TextView>(R.id.message)
        textView.text = text
        try {
            dialog!!.show()
        } catch (e: Exception) {
        }
    }

    /// hides the loader
    fun hideLoading() {
        try {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
        }
    }
}