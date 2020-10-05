package com.fernando.bookworm.di.main

import androidx.lifecycle.ViewModel
import com.fernando.bookworm.di.ViewModelKey
import com.fernando.bookworm.viewmodels.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

}