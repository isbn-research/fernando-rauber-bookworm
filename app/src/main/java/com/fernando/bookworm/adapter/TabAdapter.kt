package com.fernando.bookworm.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fernando.bookworm.ui.BarcodeScannerFragment
import com.fernando.bookworm.ui.SearchFragment
import com.fernando.bookworm.util.Constants
import javax.inject.Inject


class TabAdapter @Inject constructor(val fragmentSearch: SearchFragment, private val fragmentBarcode: BarcodeScannerFragment, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = fragmentSearch
            }
            1 -> {
                fragment = fragmentBarcode
            }
        }
        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return Constants.TAB_SEARCH

            1 -> return Constants.TAB_BARCODE
        }

        return ""
    }

    override fun getCount(): Int {
        return 2
    }
}