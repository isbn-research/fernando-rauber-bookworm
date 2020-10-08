package com.fernando.bookworm.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.fernando.bookworm.databinding.AdapterBookBinding
import com.fernando.bookworm.extension.TAG
import com.fernando.bookworm.model.BookModel
import com.fernando.bookworm.ui.BookDetailsActivity
import com.fernando.bookworm.util.Constants
import javax.inject.Inject


class BookAdapter @Inject constructor(private val requestManager: RequestManager) : RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    private var bookList: MutableList<BookModel> = ArrayList()
    private lateinit var context: Context
    private var lastPosition: Int = -1

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AdapterBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(bookList[position])

        // Set animation
        setAnimation(holder.itemView, position)
    }

    private fun setAnimation(itemView: View, position: Int) {

        // When position is greater than last position, Initialize animation
        if (position > lastPosition) {

            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)

            itemView.animation = animation
            // Set current position into last
            lastPosition = position
        }
    }

    fun setBooks(books: List<BookModel>?) {
        bookList.clear()

        if (books != null)
            bookList.addAll(books)

        lastPosition = -1
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long = position.toLong()

    inner class MyViewHolder(private val binding: AdapterBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: BookModel) {
            try {
                // Load image url
                if (!book.imageURL.isBlank())
                    requestManager.load(book.imageURL).into(binding.imBook)

                binding.tvBookTitle.text = book.title
                binding.tvBookAuthor.text = book.author
                binding.ratingBar.rating = book.averageRating.toFloat()
                binding.tvRatingCount.text = "(${book.ratingCount})"


                //open book details in new activity
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, BookDetailsActivity::class.java)
                    intent.putExtra(Constants.BOOK_KEY, book)

                    itemView.context.startActivity(intent)
                }


            } catch (e: Exception) {
                Log.e(TAG, "MyViewHolder:bind ${e.message}")
            }

        }

    }
}



