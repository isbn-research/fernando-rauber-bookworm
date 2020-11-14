package com.fernando.bookworm.screen.search

import com.fernando.bookworm.R
import com.fernando.bookworm.common.ScreenRobot

class RobotSearch : ScreenRobot<RobotSearch>() {

    companion object {
        private const val FIELD_SEARCH = R.id.et_search
        private const val ID_BOOK_DETAILS_ACTIVITY = R.id.bt_book_link
        private const val BT_SEARCH = R.id.bt_search
        private const val RECYCLER_BOOK = R.id.recycler_book_result
        private const val PROGRESSBAR_LOADING = R.id.progress_bar_loading

        private const val MSG_FIELD_REQUIRED = R.string.text_required
        private const val MSG_NO_BOOK_FOUND = R.string.book_not_found

        private const val TEXT_SUCCESS = "Harry Potter"
        private const val TEXT_FAIL = "-----"
    }

    fun verifyIfIsMainActivity(): RobotSearch {
        checkIsDisplayed(RECYCLER_BOOK)
        return this
    }

    fun verifyIfIsFragmentSearch(): RobotSearch {
        checkIsDisplayed(FIELD_SEARCH)
        return this
    }

    fun verifyIfIsBookDetailsSearch(): RobotSearch {
        checkIsDisplayed(ID_BOOK_DETAILS_ACTIVITY)
        return this
    }

    fun checkLoadingIsDisplayed(): RobotSearch {
        checkIsDisplayed(PROGRESSBAR_LOADING)
        return this
    }

    fun checkRequiredFieldMessage(): RobotSearch {
        checkMessageDisplayed(MSG_FIELD_REQUIRED)
        return this
    }

    fun checkNoBookFoundMessage(): RobotSearch {
        checkMessageDisplayed(MSG_NO_BOOK_FOUND)
        return this
    }

    fun typeTextSuccess(): RobotSearch {
        enterTextIntoView(FIELD_SEARCH, TEXT_SUCCESS)
        return this
    }

    fun typeTextFail(): RobotSearch {
        enterTextIntoView(FIELD_SEARCH, TEXT_FAIL)
        return this
    }

    fun clickBtSearch(): RobotSearch {
        clickOnView(BT_SEARCH)
        return this
    }

    fun clickRecyclerContainsRows(): RobotSearch {
        clickRecyclerAtPosition(RECYCLER_BOOK, 1)
        return this
    }

}

