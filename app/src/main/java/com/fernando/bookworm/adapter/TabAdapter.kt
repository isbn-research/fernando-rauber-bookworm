package com.fernando.bookworm.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fernando.bookworm.activity.CodeReaderFragment
import com.fernando.bookworm.activity.SearchFragment
import com.fernando.bookworm.util.Constants
import javax.inject.Inject


class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @Inject
    lateinit var fragmentSearch: SearchFragment

    @Inject
    lateinit var fragmentCode: CodeReaderFragment

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = SearchFragment()
            }
            1 -> {
                fragment = CodeReaderFragment()
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