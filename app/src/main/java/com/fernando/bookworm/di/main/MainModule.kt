package com.fernando.bookworm.di.main

import com.bumptech.glide.RequestManager
import com.fernando.bookworm.adapter.BookAdapter
import com.fernando.bookworm.network.GoogleBookApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideGoogleApi(retrofit: Retrofit): GoogleBookApi {
        return retrofit.create(GoogleBookApi::class.java)
    }

    @MainScope
    @Provides
    fun provideBookAdapter(requestManager: RequestManager): BookAdapter = BookAdapter(requestManager)

}