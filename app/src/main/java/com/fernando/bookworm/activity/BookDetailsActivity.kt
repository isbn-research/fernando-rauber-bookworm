package com.fernando.bookworm.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.fernando.bookworm.BaseActivity
import com.fernando.bookworm.R
import com.fernando.bookworm.extension.toastMessage
import com.fernando.bookworm.model.BookModel
import com.fernando.bookworm.util.Constants
import com.fernando.bookworm.viewmodels.BookDetailsViewModel
import com.fernando.bookworm.viewmodels.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_book_details.*
import javax.inject.Inject

class BookDetailsActivity : BaseActivity() {

    private lateinit var viewModel: BookDetailsViewModel

    @Inject
    lateinit var requestManager: RequestManager

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        //viewModel
        viewModel = ViewModelProvider(this, providerFactory).get(BookDetailsViewModel::class.java)

        retrieveBook()
        observers()
    }

    //in case book has some error of formation, it will finish the activity
    private fun retrieveBook() {
        try {

            val book = intent.getSerializableExtra("book") as BookModel
            viewModel.setBookModel(book)

        } catch (e: Exception) {
            toastMessage(R.string.book_error, isWarning = true)
            finish()
        }
    }

    //observer
    private fun observers() {
        viewModel.bookObserver().observe(this, { book ->

            requestManager.load(book.imageURL).into(im_book_cover)

            tv_title.text = book.title
            tv_author.text = book.author
            tv_isbn_10.text = book.ISBN10
            tv_isbn_13.text = book.ISBN13
            tv_publisher.text = book.publisher
            tv_published_date.text = book.published
            tv_page_number.text = if (book.pageNumber == 0) Constants.NOT_AVAILABLE else "${book.pageNumber}"
            tv_description.text = book.description

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}