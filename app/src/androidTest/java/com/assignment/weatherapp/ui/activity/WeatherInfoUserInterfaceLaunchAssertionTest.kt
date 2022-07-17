package com.assignment.weatherapp.ui.activity


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.assignment.weatherapp.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class WeatherInfoUserInterfaceLaunchAssertionTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun weatherInfoUserInterfaceLaunchAssertionTest() {
        Thread.sleep(2000)
        val textView = onView(
            allOf(
                withId(R.id.text_label_search_for_city), withText("Search for a city"),
                withParent(
                    allOf(
                        withId(R.id.weather_info_dashboard),
                        withParent(withId(R.id.nav_host_fragment_content_main))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Search for a city")))
    }
}
