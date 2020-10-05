package com.fernando.bookworm.di.main


import com.fernando.bookworm.activity.CodeReaderFragment
import com.fernando.bookworm.activity.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeCodeReaderFragment(): CodeReaderFragment
}