package com.fernando.bookworm.activity

import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.RequestManager
import com.fernando.bookworm.BaseActivity
import com.fernando.bookworm.R
import com.fernando.bookworm.model.BookModel
import com.fernando.bookworm.util.Constants
import kotlinx.android.synthetic.main.activity_book_details.*
import javax.inject.Inject

class BookDetailsActivity : BaseActivity() {

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        supportActionBar?.title = "Book Details"

        val book = intent.getSerializableExtra("book") as BookModel

        requestManager.load(book.imageURL).into(im_book_cover)

        tv_title.text = book.title
        tv_author.text = book.author
        tv_isbn_10.text = book.ISBN10
        tv_isbn_13.text = book.ISBN13
        tv_publisher.text = book.publisher
        tv_published_date.text = book.published
        tv_page_number.text = if (book.pageNumber == 0) Constants.NOT_AVAILABLE else "${book.pageNumber}"
        tv_description.text = book.description

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}