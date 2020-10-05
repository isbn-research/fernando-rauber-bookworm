package com.fernando.bookworm.extension


import com.fernando.bookworm.model.BookModel
import com.fernando.bookworm.model.google.GoogleModel
import com.fernando.bookworm.util.Constants

fun googleConvertToBookModel(googleBook: List<GoogleModel>): List<BookModel> {
    val bookList = ArrayList<BookModel>()

    for (book in googleBook) {
        if (book.volumeInfo == null)
            break

//        try {
        val bookModel = BookModel(title = book.volumeInfo.title)

        bookModel.apply {

            //some publications doesnt have authors
            author = if (book.volumeInfo.authors != null)
                book.volumeInfo.authors.toString().replace("[", "").replace("]", "")
            else
                Constants.NOT_AVAILABLE

            //if exist ISBN, will get the ISBN 10 and ISBN 13
            if (book.volumeInfo.isbn != null) {
                if (book.volumeInfo.isbn.isNotEmpty())
                    ISBN10 = book.volumeInfo.isbn[0].identifier
                if (book.volumeInfo.isbn.size > 1)
                    ISBN13 = book.volumeInfo.isbn[1].identifier
            }

            bookModel.publisher = book.volumeInfo.publisher ?: Constants.NOT_AVAILABLE

            bookModel.description = book.volumeInfo.description ?: Constants.NOT_AVAILABLE

            ratingCount = book.volumeInfo.ratingsCount
            averageRating = book.volumeInfo.averageRating

            if (book.volumeInfo.imageLinks != null)
                imageURL = book.volumeInfo.imageLinks.thumbnail

            published = book.volumeInfo.publishedDate ?: Constants.NOT_AVAILABLE
            pageNumber = book.volumeInfo.pageCount


        }

        bookList.add(bookModel)

//        } catch (e: Exception) {
//            if error, it wont get into the list and cause issues in the next process
//            Log.e("ReturnConverterExtention", "googleConvertToBookModel: ${e.message}", e)
//        }
    }

    //put the top rating on the top
    bookList.sortByDescending { it.ratingCount }

    return bookList
}

