package com.fernando.bookworm.screen.bookdetails

import com.fernando.bookworm.R
import com.fernando.bookworm.common.ScreenRobot

class RobotBookDetails : ScreenRobot<RobotBookDetails>() {

    companion object {
        private const val TITLE_ACTIVITY = R.string.book_details
        private const val BT_BOOK_LINK = R.id.bt_book_link

        private const val MSG_BOOK_ERROR = R.string.book_error
    }

    fun verifyIfIsBookDetailsActivity(): RobotBookDetails {
        checkMessageDisplayed(TITLE_ACTIVITY)
        return this
    }

    fun clickBtBookLink(): RobotBookDetails {
        clickOnView(BT_BOOK_LINK)
        return this
    }

    fun checkBookErrorMessage(): RobotBookDetails {
        checkMessageDisplayed(MSG_BOOK_ERROR)
        return this
    }

}

