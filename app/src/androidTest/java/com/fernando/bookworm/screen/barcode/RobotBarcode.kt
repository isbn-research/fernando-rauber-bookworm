package com.fernando.bookworm.screen.barcode

import com.fernando.bookworm.R
import com.fernando.bookworm.common.ScreenRobot

class RobotBarcode : ScreenRobot<RobotBarcode>() {

    companion object {
        private const val VIEW_PAGER = R.id.view_pager
        private const val RECYCLER_BOOK = R.id.recycler_book_result

        private const val MSG_CAMERA_MSG = R.string.camera_msg
    }

    fun verifyIfIsMainActivity(): RobotBarcode {
        checkIsDisplayed(RECYCLER_BOOK)
        return this
    }

    fun swipeLeft(): RobotBarcode {
        swipeLeftOnView(VIEW_PAGER)
        return this
    }

    fun swipeRight(): RobotBarcode {
        swipeRightOnView(VIEW_PAGER)
        return this
    }

    fun verifyIfIsFragmentBarcode(): RobotBarcode {
        checkMessageDisplayed(MSG_CAMERA_MSG)
        return this
    }

}

