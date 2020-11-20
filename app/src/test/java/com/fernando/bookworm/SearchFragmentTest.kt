package com.fernando.bookworm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fernando.bookworm.model.BookModel
import com.fernando.bookworm.repository.BookRepository
import com.fernando.bookworm.util.BookResource
import com.fernando.bookworm.viewmodels.SearchViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchFragmentTest {

    @MockK
    lateinit var repository: BookRepository

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()


    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = SearchViewModel()
        viewModel.bookRepository = repository
    }

    @Test
    fun `given error state for empty field, when searchBook called`() {

        // Given
        val mockedObserver = createObserver()
        viewModel.searchResultObserver().observeForever(mockedObserver)


        val message = R.string.text_required

        // When
        viewModel.searchBook()

        // Then
        val slot = slot<BookResource<List<BookModel>>>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat((slot.captured as BookResource.Error).msg).isEqualTo(message)
    }

    @Test
    fun `given loading state, when searchBook called, then Loading return true`() {

        // Given
        val mockedObserver = createObserver()
        viewModel.searchResultObserver().observeForever(mockedObserver)

        every { repository.searchBookOnGoogle(any()) } returns
                Observable.just(BookResource.Loading)

        // When
        viewModel.searchText = "Anything"
        viewModel.searchBook()

        // Then
        val slot = slot<BookResource<List<BookModel>>>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured is BookResource.Loading).isTrue()

        verify { repository.searchBookOnGoogle(any()) }
    }

    @Test
    fun `given error state, when searchBook called, then update live data for error status`() {
        // Given
        val mockedObserver = createObserver()
        viewModel.searchResultObserver().observeForever(mockedObserver)

        val message = R.string.error
        val resource = BookResource.Error(message)

        every { repository.searchBookOnGoogle(any()) } returns
                Observable.just(resource)

        // When
        viewModel.searchText = "Anything"
        viewModel.searchBook()

        // Then
        val slot = slot<BookResource<List<BookModel>>>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat((slot.captured as BookResource.Error).msg).isEqualTo(message)
        assertThat(slot.captured is BookResource.Loading).isFalse()

        verify { repository.searchBookOnGoogle(any()) }
    }

    @Test
    fun `given not found state, when searchBook called, then update live data for not found status`() {
        // Given
        val mockedObserver = createObserver()
        viewModel.searchResultObserver().observeForever(mockedObserver)

        val resource = BookResource.NotFound

        every { repository.searchBookOnGoogle(any()) } returns
                Observable.just(resource)

        // When
        viewModel.searchText = "Anything"
        viewModel.searchBook()

        // Then
        val slot = slot<BookResource<List<BookModel>>>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat((slot.captured is BookResource.NotFound)).isTrue()
        assertThat(slot.captured is BookResource.Loading).isFalse()

        verify { repository.searchBookOnGoogle(any()) }
    }

    @Test
    fun `given success state, when searchBookOnGoogle called, then Loading return true`() {
        // Given
        val mockedObserver = createObserver()
        viewModel.searchResultObserver().observeForever(mockedObserver)

        val resource = BookResource.Success(listOf<BookModel>())

        every { repository.searchBookOnGoogle(any()) } returns
                Observable.just(resource)

        // When
        viewModel.searchText = "Harry Potter"
        viewModel.searchBook()


        // Then
        val slot = slot<BookResource<List<BookModel>>>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat((slot.captured as BookResource.Success).data).isEqualTo(resource.data)
        assertThat(slot.captured is BookResource.Loading).isFalse()

        verify { repository.searchBookOnGoogle(any()) }
    }

    private fun createObserver(): Observer<BookResource<List<BookModel>>> =
        spyk(Observer { })

}