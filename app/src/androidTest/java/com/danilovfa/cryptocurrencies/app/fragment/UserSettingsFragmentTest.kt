package com.danilovfa.cryptocurrencies.app.fragment

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.danilovfa.cryptocurrencies.app.MainActivity
import org.junit.Rule
import org.junit.Test
import com.danilovfa.cryptocurrencies.R

class UserSettingsFragmentTest {

    @JvmField
    @Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun shouldDisplaySnackbarIfIncorrectValueIsEntered() {
        val testFirstName = "John"
        val testLastName = ""

        val scenario = launchFragmentInContainer(Bundle(), R.style.Base_Theme_Cryptocurrencies) {
            UserSettingsFragment()
        }

        onView(withId(R.id.firstNameEditText))
            .perform(typeText(testFirstName))
            .perform(closeSoftKeyboard())
        onView(withId(R.id.lastNameEditText))
            .perform(typeText(testLastName))
            .perform(closeSoftKeyboard())

        scenario.onFragment { fragment ->
            fragment.saveUser()
        }

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.save_user_error)))
    }
}