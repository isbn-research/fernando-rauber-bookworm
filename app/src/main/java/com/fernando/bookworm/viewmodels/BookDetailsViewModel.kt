package com.fernando.bookworm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fernando.bookworm.model.BookModel
import javax.inject.Inject

class BookDetailsViewModel @Inject constructor() : ViewModel() {

    private var bookData = MutableLiveData<BookModel>()

    fun bookObserver(): LiveData<BookModel> = bookData

    fun setBookModel(book: BookModel?) {
        if (book != null)
            bookData.value = book
    }

}