package com.fernando.bookworm.adapter

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.fernando.bookworm.R
import com.fernando.bookworm.activity.BookDetailsActivity
import com.fernando.bookworm.extension.TAG
import com.fernando.bookworm.extension.inflate
import com.fernando.bookworm.model.BookModel
import kotlinx.android.synthetic.main.adapter_book.view.*
import javax.inject.Inject


class BookAdapter @Inject constructor(private val requestManager: RequestManager) : RecyclerView.Adapter<BookAdapter.MyViewHolder>() {

    private var bookList: MutableList<BookModel> = ArrayList()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = parent.inflate(R.layout.adapter_book)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            val book = bookList[position]

            holder.apply {

                //load image url
                requestManager.load(book.imageURL).into(image)

                title.text = book.title
                author.text = book.author
                rating.rating = book.averageRating.toFloat()
                ratingCount.text = "(${book.ratingCount})"


                //open book details in new activity
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, BookDetailsActivity::class.java)
                    intent.putExtra("book", book)

                    itemView.context.startActivity(intent)
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "onBindViewHolder: ${e.message}")
        }
    }

    fun setBooks(books: List<BookModel>?) {
        bookList.clear()

        if (books != null)
            bookList.addAll(books)

        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long = position.toLong()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.im_book

        val title: TextView = itemView.tv_book_title
        val author: TextView = itemView.tv_book_author
        val rating: RatingBar = itemView.rating_bar
        val ratingCount: TextView = itemView.tv_rating_count

    }
}



