package com.fernando.bookworm.di

import com.fernando.bookworm.activity.BookDetailsActivity
import com.fernando.bookworm.activity.MainActivity
import com.fernando.bookworm.di.main.MainFragmentModule
import com.fernando.bookworm.di.main.MainModule
import com.fernando.bookworm.di.main.MainScope
import com.fernando.bookworm.di.main.ViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @MainScope
    @ContributesAndroidInjector(modules = [MainFragmentModule::class, ViewModelModule::class, MainModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @MainScope
    @ContributesAndroidInjector(modules = [ViewModelModule::class, MainModule::class])
    abstract fun contributeBookDetailsActivity(): BookDetailsActivity
}