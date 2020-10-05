package com.fernando.bookworm.di

import com.fernando.bookworm.repository.BookRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideBookRepository(): BookRepository = BookRepository()
}