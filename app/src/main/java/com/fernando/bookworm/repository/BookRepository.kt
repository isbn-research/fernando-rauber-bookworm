package com.fernando.bookworm.repository

import com.fernando.bookworm.extension.googleConvertToBookModel
import com.fernando.bookworm.model.BookModel
import com.fernando.bookworm.model.google.ResultGoogle
import com.fernando.bookworm.network.GoogleBookApi
import com.fernando.bookworm.util.BookResource
import com.fernando.bookworm.util.Constants
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class BookRepository @Inject constructor() {

    @Inject
    lateinit var googleApi: GoogleBookApi

    fun searchBookOnGoogle(searchText: String): Observable<BookResource<List<BookModel>>> {
        return googleApi.searchBook(searchText, Constants.SEARCH_TYPE, Constants.GOOGLE_KEY)
            .onErrorReturn {

                ResultGoogle(-1, null)
            }
            .map { resource ->

                if (resource.totalItems <= 0)
                    BookResource.NotFound
                else
                    BookResource.Success(googleConvertToBookModel(resource.items!!))

            }.subscribeOn(Schedulers.io())
            .startWith(BookResource.Loading)

    }

}