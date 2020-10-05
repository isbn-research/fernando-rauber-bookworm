package com.fernando.bookworm.repository

import com.fernando.bookworm.model.google.ResultGoogle
import com.fernando.bookworm.network.GoogleBookApi
import com.fernando.bookworm.util.Constants
import io.reactivex.Flowable
import javax.inject.Inject


class BookRepository @Inject constructor() {

    @Inject
    lateinit var googleApi: GoogleBookApi

    fun searchBookOnGoogle(searchText: String): Flowable<ResultGoogle> {
        return googleApi.searchBook(searchText, Constants.SEARCH_TYPE, Constants.GOOGLE_KEY)
    }

}