package com.fernando.bookworm.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fernando.bookworm.R
import com.fernando.bookworm.adapter.BookAdapter
import com.fernando.bookworm.databinding.FragmentSearchBinding
import com.fernando.bookworm.extension.createLoadingPopup
import com.fernando.bookworm.extension.hideKeyboard
import com.fernando.bookworm.extension.isNetworkAvailable
import com.fernando.bookworm.extension.toastMessage
import com.fernando.bookworm.util.BookResource.*
import com.fernando.bookworm.util.RxBus
import com.fernando.bookworm.util.RxEvent
import com.fernando.bookworm.viewmodels.SearchViewModel
import com.fernando.bookworm.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class SearchFragment @Inject constructor() : DaggerFragment() {

    private lateinit var viewModel: SearchViewModel

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var barcodeDisposable: Disposable
    private lateinit var loadingDialog: AlertDialog

    @Inject
    lateinit var adapter: BookAdapter

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // View Binding
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(this, providerFactory).get(SearchViewModel::class.java)

        initVariables()
        variableAction()
        observers()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun searchBook() {

        // Verify if the user has connection with internet
        if (!requireActivity().isNetworkAvailable())
            return

        viewModel.searchBook()
    }

    // Initialize variables
    private fun initVariables() {
        // Create loading popup
        loadingDialog = requireActivity().createLoadingPopup()

        // Init the recycler
        binding.recyclerBookResult.layoutManager = LinearLayoutManager(activity)
        binding.recyclerBookResult.adapter = adapter
    }

    private fun variableAction() {

        // Action when click in search button
        binding.btSearch.setOnClickListener {
            searchBook()
        }
        // Set value into ViewModel
        binding.etSearch.doAfterTextChanged { text -> viewModel.searchText = text?.toString() ?: "" }

        viewModel.searchByRef = R.string.title

        // Change the hint
        binding.rgSearchOptions.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_title -> {
                    binding.etSearch.setHint(R.string.title_hint)
                    viewModel.searchByRef = R.string.title
                }
                R.id.rb_author -> {
                    binding.etSearch.setHint(R.string.author_hint)
                    viewModel.searchByRef = R.string.author
                }
                R.id.rb_isbn -> {
                    binding.etSearch.setHint(R.string.isbn_hint)
                    viewModel.searchByRef = R.string.isbn
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Dispose if fragment destroyed
        if (!barcodeDisposable.isDisposed)
            barcodeDisposable.dispose()
    }

    // Listener and observer
    private fun observers() {
        // Listener for when the user scan a code, will be redirect to search tab and search the book
        barcodeDisposable = RxBus.listen(RxEvent.EventSearchByBarcode::class.java).subscribe {

            // Barcode can be from 10 to 13 length
            if (it.barcode.length >= 10) {
                // Set the barcode into edit text and search
                binding.rgSearchOptions.check(R.id.rb_isbn)
                binding.etSearch.setText(it.barcode)
                searchBook()
            }
        }

        viewModel.searchResultObserver().removeObservers(viewLifecycleOwner)
        viewModel.searchResultObserver().observe(viewLifecycleOwner, { bookResource ->

            if (bookResource != null)
                when (bookResource) {
                   is Loading -> {
                        hideKeyboard()
                        loadingDialog.show()
                    }

                   is Success -> {
                        loadingDialog.dismiss()

                        // Scroll recycler view to the top
                        binding.recyclerBookResult.smoothScrollToPosition(0)
                        // Update adapter
                        adapter.setBooks(bookResource.data)
                    }

                   is Error -> {
                        loadingDialog.dismiss()
                        adapter.setBooks(null)
                        requireActivity().toastMessage(bookResource.msg, isWarning = true)
                    }

                    is NotFound -> {
                        loadingDialog.dismiss()
                        adapter.setBooks(null)
                        requireActivity().toastMessage(R.string.book_not_found, isWarning = true)
                    }
                }
        })
    }

}