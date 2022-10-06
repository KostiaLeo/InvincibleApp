package com.lyft.android.interviewapp.navigation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.accompanist.appcompattheme.AppCompatTheme
import com.lyft.android.interviewapp.HiltTestActivity
import com.lyft.android.interviewapp.data.repository.Repository
import com.lyft.android.interviewapp.ui.navigation.AppNavHost
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
class NavigationTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
    private val activity get() = composeTestRule.activity

    @Inject
    lateinit var repository: Repository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    private fun setContent() {
        composeTestRule.setContent {
            AppCompatTheme {
                AppNavHost()
            }
        }
    }

    @Test
    fun testNav() {
        setContent()

    }
}