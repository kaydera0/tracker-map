package com.example.task7

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.task7.presentation.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TestsWithEspresso {

    @Rule @JvmField
    val activityScenarioRule = ActivityScenarioRule(
        MainActivity::class.java
    )
    @Test
    fun testLoginIn() {
        Espresso.onView(withId(R.id.userNameText)).perform(ViewActions.clearText())
            .perform(ViewActions.typeText("test@gmail.com"), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.passwordText)).perform(ViewActions.clearText())
            .perform(ViewActions.typeText("test123"), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.sign_in_btn))
            .check(matches(isEnabled()))
        Espresso.onView(withId(R.id.sign_in_btn)).perform(ViewActions.click())
    }
    @Test
    fun testLoginInAndMap() {
        Espresso.onView(withId(R.id.userNameText)).perform(ViewActions.clearText())
            .perform(ViewActions.typeText("test@gmail.com"), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.passwordText)).perform(ViewActions.clearText())
            .perform(ViewActions.typeText("test123"), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.sign_in_btn))
            .check(matches(isEnabled()))
        Espresso.onView(withId(R.id.sign_in_btn)).perform(ViewActions.click())
        Thread.sleep(500)
        Espresso.onView(withId(R.id.time_btn)).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.three_days)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.three_days)).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.onView(withId(R.id.map2)).check(matches(isDisplayed()))

    }
    @Test
    fun registrationTest() {

        val newLogin = ""                   ///Text new user name
        val newPassword = ""                ///Text new password

        Espresso.onView(withId(R.id.sign_up_txt)).perform(ViewActions.click())
        Thread.sleep(500)
        Espresso.onView(withId(R.id.userNameTextRegistration)).perform(ViewActions.clearText())
            .perform(ViewActions.typeText(newLogin), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.passwordTextRegistration)).perform(ViewActions.clearText())
            .perform(ViewActions.typeText(newPassword), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.passwordText2Registration)).perform(ViewActions.clearText())
            .perform(ViewActions.typeText(newPassword), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.sign_up_btn)).perform(ViewActions.click())
        Thread.sleep(500)
        Espresso.onView(withId(R.id.userNameText)).perform(ViewActions.clearText())
            .perform(ViewActions.typeText(newLogin), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.passwordText)).perform(ViewActions.clearText())
            .perform(ViewActions.typeText(newPassword), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.sign_in_btn)).perform(ViewActions.click())
        Thread.sleep(500)
    }
}