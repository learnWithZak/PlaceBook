package com.zak.placebook.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.zak.placebook.model.Bookmark
import com.zak.placebook.repository.BookmarkRepo
import com.zak.placebook.util.ImageUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BookmarkDetailsViewModel(application: Application): AndroidViewModel(application) {

    private val bookmarkRepo = BookmarkRepo(getApplication())
    private var bookmarkDetailsView: LiveData<BookmarkDetailsView>? = null

    data class BookmarkDetailsView(
        var id: Long? = null,
        var name: String = "",
        var phone: String = "",
        var address: String = "",
        var notes: String = ""
    ) {
        fun getImage(context: Context) = id?.let {
            ImageUtils.loadBitmapFromFile(context, Bookmark.generateImageFilename(it))
        }
    }

    private fun bookmarkToBookmarkView(bookmark: Bookmark): BookmarkDetailsView {
        return BookmarkDetailsView(
            id = bookmark.id,
            name = bookmark.name,
            phone = bookmark.phone,
            address = bookmark.address,
            notes = bookmark.notes
        )
    }

    private fun mapBookmarkToBookmarkView(bookmarkId: Long) {
        val bookmark = bookmarkRepo.getLiveBookmark(bookmarkId)
        bookmarkDetailsView = Transformations.map(bookmark) {
            bookmarkToBookmarkView(it)
        }
    }

    fun getBookmark(bookmarkId: Long): LiveData<BookmarkDetailsView>? {
        if (bookmarkDetailsView == null) {
            mapBookmarkToBookmarkView(bookmarkId)
        }
        return bookmarkDetailsView
    }

    private fun bookmarkViewToBookmark(bookmarkView: BookmarkDetailsView): Bookmark? {
        val bookmark = bookmarkView.id?.let {
            bookmarkRepo.getBookmark(it)
        }
        if (bookmark != null) {
            bookmark.id = bookmarkView.id
            bookmark.name = bookmarkView.name
            bookmark.phone = bookmarkView.phone
            bookmark.address = bookmarkView.address
            bookmark.notes = bookmarkView.notes
        }
        return bookmark
    }

    fun updateBookmark(bookmarkView: BookmarkDetailsView) {
        GlobalScope.launch {
            val bookmark = bookmarkViewToBookmark(bookmarkView)
            bookmark?.let { bookmarkRepo.updateBookmark(it) }
        }
    }

}