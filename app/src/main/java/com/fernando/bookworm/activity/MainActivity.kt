package com.fernando.bookworm.activity

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.fernando.bookworm.BaseActivity
import com.fernando.bookworm.R
import com.fernando.bookworm.adapter.TabAdapter
import com.fernando.bookworm.util.RxBus
import com.fernando.bookworm.util.RxEvent
import com.google.android.material.tabs.TabLayout
import io.reactivex.disposables.Disposable

class MainActivity : BaseActivity() {

    private lateinit var barcodeDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val sectionsPagerAdapter = TabAdapter(supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        //listener when code scanner find the code, tab to "search"
        barcodeDisposable = RxBus.listen(RxEvent.EventSearchByBarcode::class.java).subscribe {
            if (it.barcode.length >= 10)
                viewPager.setCurrentItem(0, true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        //destroy listener so wont leak memory
        if (!barcodeDisposable.isDisposed)
            barcodeDisposable.dispose()
    }

}