package com.fernando.bookworm.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fernando.bookworm.R
import com.fernando.bookworm.adapter.BookAdapter
import com.fernando.bookworm.extension.createLoadingPopup
import com.fernando.bookworm.extension.hideKeyboard
import com.fernando.bookworm.extension.toastMessage
import com.fernando.bookworm.util.BookResource.AuthStatus.*
import com.fernando.bookworm.util.RxBus
import com.fernando.bookworm.util.RxEvent
import com.fernando.bookworm.viewmodels.SearchViewModel
import com.fernando.bookworm.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class SearchFragment : DaggerFragment() {

    private lateinit var viewModel: SearchViewModel

    private lateinit var recyclerBook: RecyclerView
    private lateinit var radioGroup: RadioGroup
    private lateinit var etSearch: EditText
    private lateinit var btSearch: Button

    private lateinit var barcodeDisposable: Disposable
    private lateinit var loadingDialog: AlertDialog

    @Inject
    lateinit var adapter: BookAdapter

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVariables(view)

        //viewModel
        viewModel = ViewModelProvider(this, providerFactory).get(SearchViewModel::class.java)

        variableAction()
        observers()
    }

    //initialize all variable
    private fun initVariables(view: View) {
        recyclerBook = view.findViewById(R.id.recycler_book_result)
        radioGroup = view.findViewById(R.id.rg_search_options)
        etSearch = view.findViewById(R.id.et_search)
        btSearch = view.findViewById(R.id.bt_search)

        //create loading popup
        loadingDialog = requireActivity().createLoadingPopup()

        //init the recycler
        recyclerBook.layoutManager = LinearLayoutManager(activity)
        recyclerBook.adapter = adapter
    }

    private fun variableAction() {

        //action when click in search button
        btSearch.setOnClickListener {
            searchBook()
        }

        //change the hint
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_title ->
                    etSearch.setHint(R.string.title_hint)

                R.id.rb_author ->
                    etSearch.setHint(R.string.author_hint)

                R.id.rb_isbn ->
                    etSearch.setHint(R.string.isbn_hint)
            }
        }
    }

    private fun searchBook() {
        //check which radio button is checked(title, author or ISBN)
        when (radioGroup.checkedRadioButtonId) {

            R.id.rb_title -> viewModel.searchByRef = R.string.title
            R.id.rb_author -> viewModel.searchByRef = R.string.author
            R.id.rb_isbn -> viewModel.searchByRef = R.string.isbn
        }

        viewModel.searchText = etSearch.text.toString()
        viewModel.searchBook()
    }

    override fun onDestroy() {
        super.onDestroy()

        //dispose if fragment destroyed
        if (!barcodeDisposable.isDisposed)
            barcodeDisposable.dispose()
    }

    //listener and observer
    private fun observers() {
        //listener for when the user scan a code, will be redirect to search tab and search the book
        barcodeDisposable = RxBus.listen(RxEvent.EventSearchByBarcode::class.java).subscribe {

            //barcode can be from 10 to 13 length
            if (it.barcode.length >= 10) {
                //set the barcode into edit text and search
                radioGroup.check(R.id.rb_isbn)
                etSearch.setText(it.barcode)
                searchBook()
            }
        }

        viewModel.searchResultObserver().removeObservers(viewLifecycleOwner);
        viewModel.searchResultObserver().observe(viewLifecycleOwner, { bookResource ->

            if (bookResource != null)
                when (bookResource.status) {
                    LOADING -> {
                        hideKeyboard()
                        loadingDialog.show()
                    }

                    SUCCESS -> {
                        loadingDialog.dismiss()
                        //update adapter
                        adapter.setBooks(bookResource.data)
                    }

                    ERROR -> {
                        loadingDialog.dismiss()
                        adapter.setBooks(null)
                        requireActivity().toastMessage(bookResource.message, isWarning = true)
                    }

                    //NOT FOUND
                    else -> {
                        loadingDialog.dismiss()
                        adapter.setBooks(null)
                        requireActivity().toastMessage(R.string.book_not_found, isWarning = true)
                    }
                }
        })
    }

}