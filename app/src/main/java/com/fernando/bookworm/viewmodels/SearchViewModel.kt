package com.fernando.bookworm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fernando.bookworm.R
import com.fernando.bookworm.model.BookModel
import com.fernando.bookworm.repository.BookRepository
import com.fernando.bookworm.util.BookResource
import com.fernando.bookworm.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SearchViewModel @Inject constructor() : ReactiveViewModel() {

    @Inject
    lateinit var bookRepository: BookRepository

    var searchText: String = ""
    var searchByRef: Int = 0

    private var _searchResult = MutableLiveData<BookResource<List<BookModel>>>()

    fun searchResultObserver(): LiveData<BookResource<List<BookModel>>> = _searchResult

    fun searchBook() {
        // Field cant be empty
        if (searchText.isBlank()) {
            _searchResult.value = BookResource.Error(R.string.text_required)
            return
        }

        // Set search by Title, Author or ISBN
        val searchParameter = when (searchByRef) {
            R.string.title -> Constants.SEARCH_BY_TITLE + searchText
            R.string.author -> Constants.SEARCH_BY_AUTHOR + searchText
            else -> Constants.SEARCH_BY_ISBN + searchText
        }

        bookRepository
            .searchBookOnGoogle(searchParameter)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                _searchResult.value = it

            }, {})
            .also { disposable == it }
    }

}