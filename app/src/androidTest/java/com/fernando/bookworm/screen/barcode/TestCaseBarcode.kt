package com.fernando.bookworm.screen.barcode

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.fernando.bookworm.ui.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TestCaseBarcode {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
    }

    @After
    fun finish() {
    }

    @Test
    fun test1_checkIfIsMainActivityScreen() {
        RobotBarcode()
            .verifyIfIsMainActivity()

    }

    @Test
    fun test2_checkIfIsBarcodeFragmentScreen() {
        RobotBarcode()
            .swipeLeft()
            .verifyIfIsFragmentBarcode()

    }

    @Test
    fun test3_checkSwipeBetweenTabs() {
        RobotBarcode()
            .swipeLeft()
            .verifyIfIsFragmentBarcode()
            .swipeRight()
            .verifyIfIsMainActivity()

    }

}
