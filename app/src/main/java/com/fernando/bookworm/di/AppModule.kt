package com.fernando.bookworm.di

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.fernando.bookworm.R
import com.fernando.bookworm.util.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    // Retrofit
    @Singleton
    @Provides
    fun provideRetrofitGoogleInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.GOOGLE_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideRequestOptionsBookCover(): RequestOptions {
        return RequestOptions.placeholderOf(R.drawable.ic_no_book_cover).error(R.drawable.ic_no_book_cover)
    }

    @Singleton
    @Provides
    fun provideGlideInstance(application: Application, requestOptions: RequestOptions): RequestManager {
        return Glide.with(application).setDefaultRequestOptions(requestOptions)
    }


}
