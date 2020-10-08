package com.fernando.bookworm.di.main


import com.fernando.bookworm.ui.BarcodeScannerFragment
import com.fernando.bookworm.ui.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeBarcodeScannerFragment(): BarcodeScannerFragment
}