package com.fernando.bookworm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.fernando.bookworm.R
import com.fernando.bookworm.extension.googleConvertToBookModel
import com.fernando.bookworm.model.BookModel
import com.fernando.bookworm.model.google.ResultGoogle
import com.fernando.bookworm.repository.BookRepository
import com.fernando.bookworm.util.BookResource
import com.fernando.bookworm.util.Constants
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var authRepository: BookRepository

    var searchText: String = ""
    var searchByRef: Int = 0

    private var searchResult = MediatorLiveData<BookResource<List<BookModel>>>()

    fun searchResultObserver(): LiveData<BookResource<List<BookModel>>> = searchResult

    fun searchBook() {
        //field cant be empty
        if (searchText.isBlank()) {
            searchResult.value = BookResource.error(R.string.text_required)
            return
        }

        //show loading popup on the UI
        searchResult.value = BookResource.loading()


        //set search by Title, Author or ISBN
        searchText = when (searchByRef) {
            R.string.title -> Constants.SEARCH_BY_TITLE + searchText
            R.string.author -> Constants.SEARCH_BY_AUTHOR + searchText
            else -> Constants.SEARCH_BY_ISBN + searchText
        }

        transformToLiveData(authRepository.searchBookOnGoogle(searchText))
    }

    private fun transformToLiveData(value: Flowable<ResultGoogle>) {
        val source = LiveDataReactiveStreams.fromPublisher(value
            .onErrorReturn {
                ResultGoogle(-1, null)
            }
            .map { result ->

                if (result.totalItems <= 0)
                    BookResource.notFound()
                else
                    BookResource.success(googleConvertToBookModel(result.items!!))

            }
            .subscribeOn(Schedulers.io()))

        searchResult.addSource(source) { listResource ->
            searchResult.value = listResource
            searchResult.removeSource(source)
        }
    }

}