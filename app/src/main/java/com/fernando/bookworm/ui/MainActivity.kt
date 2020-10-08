package com.fernando.bookworm.ui

import android.content.pm.PackageManager
import android.os.Bundle
import com.fernando.bookworm.BaseActivity
import com.fernando.bookworm.R
import com.fernando.bookworm.adapter.TabAdapter
import com.fernando.bookworm.databinding.ActivityMainBinding
import com.fernando.bookworm.extension.toastMessage
import com.fernando.bookworm.util.Constants
import com.fernando.bookworm.util.RxBus
import com.fernando.bookworm.util.RxEvent
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var barcodeDisposable: Disposable
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var tabAdapter: TabAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val sectionsPagerAdapter = TabAdapter(supportFragmentManager)
        binding.viewPager.adapter = tabAdapter

        binding.tabs.setupWithViewPager(binding.viewPager)


        // Listener when code scanner find the code, switch to tab Search
        barcodeDisposable = RxBus.listen(RxEvent.EventSearchByBarcode::class.java).subscribe {
            switchTab(0)
        }
    }

    // Switch tab
    private fun switchTab(position: Int) {
        binding.viewPager.setCurrentItem(position, true)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Constants.REQUEST_CAMERA_PERMISSION && grantResults.isNotEmpty())

        // If not granted, switch tab and display message
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                toastMessage(R.string.permission_required, isWarning = true)

                switchTab(0)
            }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Destroy listener so wont leak memory
        if (!barcodeDisposable.isDisposed)
            barcodeDisposable.dispose()
    }

}