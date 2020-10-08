package com.fernando.bookworm.extension


import android.util.Log
import com.fernando.bookworm.model.BookModel
import com.fernando.bookworm.model.ISBNModel
import com.fernando.bookworm.model.google.GoogleModel
import com.fernando.bookworm.util.Constants

fun googleConvertToBookModel(googleBook: List<GoogleModel>): List<BookModel> {
    val bookList = ArrayList<BookModel>()

    for (book in googleBook) {
        if (book.volumeInfo == null)
            break

        try {
            val bookModel = BookModel(title = book.volumeInfo.title)

            bookModel.apply {

                // Some publications doesnt have authors
                author = if (book.volumeInfo.authors != null)
                    book.volumeInfo.authors.toString().replace("[", "").replace("]", "")
                else
                    Constants.NOT_AVAILABLE

                // If exist ISBN, will get the ISBN 10, ISBN 13 or OTHER
                if (book.volumeInfo.isbn != null && book.volumeInfo.isbn.isNotEmpty()) {

                    // Some books can have other codes
                    if (book.volumeInfo.isbn[0].type == Constants.ISBN_OTHER)
                        ISBN.add(ISBNModel(Constants.ISBN_OTHER, book.volumeInfo.isbn[0].identifier))
                    else
                        ISBN.add(ISBNModel(Constants.ISBN_10, book.volumeInfo.isbn[0].identifier))

                    if (book.volumeInfo.isbn.size > 1)
                        ISBN.add(ISBNModel(Constants.ISBN_13, book.volumeInfo.isbn[1].identifier))
                }

                bookModel.publisher = book.volumeInfo.publisher ?: Constants.NOT_AVAILABLE
                bookModel.bookLink = book.volumeInfo.bookLink ?: ""
                bookModel.description = book.volumeInfo.description ?: Constants.NOT_AVAILABLE

                ratingCount = book.volumeInfo.ratingsCount
                averageRating = book.volumeInfo.averageRating

                if (book.volumeInfo.imageLinks != null)
                    imageURL = book.volumeInfo.imageLinks.thumbnail

                published = book.volumeInfo.publishedDate ?: Constants.NOT_AVAILABLE
                pageNumber = book.volumeInfo.pageCount

            }

            bookList.add(bookModel)

        } catch (e: Exception) {
            Log.e("googleConvertToBookModel", e.message, e)
        }
    }

    // Put the top rating on the top
    bookList.sortByDescending { it.ratingCount }

    return bookList
}

