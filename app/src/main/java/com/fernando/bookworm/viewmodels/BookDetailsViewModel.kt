package com.fernando.bookworm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fernando.bookworm.model.BookModel
import javax.inject.Inject

class BookDetailsViewModel @Inject constructor() : ViewModel() {

    private var _bookData = MutableLiveData<BookModel>()

    fun bookObserver(): LiveData<BookModel> = _bookData

    fun setBookModel(book: BookModel?) {
        if (book != null)
            _bookData.value = book
    }

}