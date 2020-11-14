package com.fernando.bookworm.screen.search

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
class TestCaseSearch {

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
        RobotSearch()
            .verifyIfIsMainActivity()

    }

    @Test
    fun test2_checkIfIsSearchFragmentScreen() {
        RobotSearch()
            .verifyIfIsFragmentSearch()

    }

    @Test
    fun test3_checkRequiredFieldMessage() {
        RobotSearch()
            .clickBtSearch()
            .checkRequiredFieldMessage()

    }

    @Test
    fun test4_checkLoadingMessage() {
        RobotSearch()
            .typeTextSuccess()
            .clickBtSearch()
            .checkLoadingIsDisplayed()

    }

    @Test
    fun test5_checkRecyclerViewContainsElements() {
        RobotSearch()
            .typeTextSuccess()
            .clickBtSearch()
            .sleep(5)
            .clickRecyclerContainsRows()
            .verifyIfIsBookDetailsSearch()

    }

    @Test
    fun test6_checkServerReturnsEmpty() {
        RobotSearch()
            .typeTextFail()
            .clickBtSearch()
            .sleep(5)
            .checkNoBookFoundMessage()
    }

}
