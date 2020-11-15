package com.fernando.bookworm.screen.bookdetails


import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.fernando.bookworm.R
import com.fernando.bookworm.model.BookModel
import com.fernando.bookworm.model.ISBNModel
import com.fernando.bookworm.ui.BookDetailsActivity
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
@LargeTest
class TestCaseBookDetails {

    val book = BookModel("title").apply {
        author = "Auhthor"
        averageRating = 4.0
        ratingCount = 20
        imageURL = ""
        ISBN.add(ISBNModel("ISBN 10", "21212121"))
        ISBN.add(ISBNModel("ISBN 13", "21212121"))
        publisher = "Amazon"
        bookLink = "https://en.wikipedia.org/wiki/Book"
        published = "I dont know"
        pageNumber = 400
        description = "Description about the book"
    }

    @Rule
    @JvmField
    var activityRule = ActivityScenarioRule(BookDetailsActivity::class.java)


    @Before
    fun setup() {

        activityRule.scenario.onActivity {
            it.setBook(book)
        }

    }

    @After
    fun finish() {
    }

    @Test
    fun test1_checkIfIsBookDetailsActivityScreen() {

        RobotBookDetails()
            .verifyIfIsBookDetailsActivity()

    }

    @Test
    fun test2_checkBookErrorMessage() {

        activityRule.scenario.onActivity {
            it.setBook(null)
        }

        RobotBookDetails()
            .checkBookErrorMessage()
    }

    @Test
    fun test3_checkIfOpenBrowser(){
        Intents.init()
        val expectedIntent: Matcher<Intent> = allOf(hasAction(Intent.ACTION_VIEW), hasData(book.bookLink))
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))

        RobotBookDetails()
            .verifyIfIsBookDetailsActivity()
            .clickBtBookLink()

        intended(expectedIntent)
        Intents.release()
    }

}
