package com.fernando.bookworm.network

import com.fernando.bookworm.model.google.ResultGoogle
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBookApi {

    @GET("volumes")
    fun searchBook(@Query("q") title: String,
                   @Query("printType") type: String,
                   @Query("key") key: String): Flowable<ResultGoogle>


}