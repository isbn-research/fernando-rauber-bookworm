package com.fernando.bookworm.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.fernando.bookworm.BaseActivity
import com.fernando.bookworm.R
import com.fernando.bookworm.databinding.ActivityBookDetailsBinding
import com.fernando.bookworm.extension.toastMessage
import com.fernando.bookworm.model.BookModel
import com.fernando.bookworm.util.Constants
import com.fernando.bookworm.viewmodels.BookDetailsViewModel
import com.fernando.bookworm.viewmodels.ViewModelProviderFactory
import javax.inject.Inject


class BookDetailsActivity : BaseActivity() {

    private lateinit var viewModel: BookDetailsViewModel
    private lateinit var binding: ActivityBookDetailsBinding

    @Inject
    lateinit var requestManager: RequestManager

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // View binding
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this, providerFactory).get(BookDetailsViewModel::class.java)

        setBook()
        observers()
    }

    // In case book has some error of formation, it will finish the activity
    fun setBook(book: BookModel? = null) {
        if (book != null) {
            viewModel.setBookModel(book)
            return
        }

        try {

            val extra = intent.getSerializableExtra(Constants.BOOK_KEY) as BookModel
            viewModel.setBookModel(extra)

        } catch (e: Exception) {
            toastMessage(R.string.book_error, isWarning = true)
        }
    }

    // Observer
    private fun observers() {
        viewModel.bookObserver().observe(this, { book ->

            binding.apply {

                requestManager.load(book.imageURL).into(imBookCover)

                tvTitle.text = book.title
                tvAuthor.text = book.author

                // Books can have ISBN 10, ISBN 13 and OTHERS
                book.ISBN.forEach { isbn ->

                    when (isbn.type) {

                        Constants.ISBN_OTHER -> {
                            groupISBNOther.visibility = View.VISIBLE
                            tvIsbnOther.text = isbn.identifier
                        }
                        Constants.ISBN_10 -> tvIsbn10.text = isbn.identifier
                        Constants.ISBN_13 -> tvIsbn13.text = isbn.identifier
                    }
                }

                tvPublisher.text = book.publisher
                tvPublishedDate.text = book.published
                tvPageNumber.text = if (book.pageNumber == 0) Constants.NOT_AVAILABLE else "${book.pageNumber}"
                tvDescription.text = book.description

                btBookLink.setOnClickListener {
                    if (book.bookLink.isBlank())
                        toastMessage(R.string.link_book_not_available)
                    else
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(book.bookLink)))
                }
            }
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