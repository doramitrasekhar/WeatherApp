package com.assignment.weatherapp.ui.activity


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.assignment.weatherapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class WeatherInfoUserInterfaceFlowTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun weatherInfoUserInterfaceFlowTest() {
        Thread.sleep(2000)
        val textInputEditText = onView(
            allOf(
                withId(R.id.input_find_city_weather),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withId(R.id.app_bar_layout),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("london"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.input_find_city_weather), withText("london"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withId(R.id.app_bar_layout),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(pressImeActionButton())
        Thread.sleep(3000)

        val appCompatImageView = onView(
            allOf(
                withId(R.id.user_info),
                childAtPosition(
                    allOf(
                        withId(R.id.constraint_layout_showing_temp),
                        childAtPosition(
                            withClassName(`is`("androidx.core.widget.NestedScrollView")),
                            0
                        )
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())
        Thread.sleep(2000)
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
