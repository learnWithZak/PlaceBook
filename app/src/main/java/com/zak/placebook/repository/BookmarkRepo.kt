package com.zak.placebook.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.zak.placebook.db.PlaceBookDatabase
import com.zak.placebook.model.Bookmark

class BookmarkRepo(context: Context) {

    private val db = PlaceBookDatabase.getInstance(context)
    private val bookmarkDao = db.bookmarkDao()
    val allBookmarks: LiveData<List<Bookmark>>
        get() {
            return bookmarkDao.loadAll()
        }

    fun addBookmark(bookmark: Bookmark): Long? {
        val newId = bookmarkDao.insertBookmark(bookmark)
        bookmark.id = newId
        return newId
    }

    fun createBookmark(): Bookmark {
        return Bookmark()
    }

    fun getLiveBookmark(bookmarkId: Long): LiveData<Bookmark> = bookmarkDao.loadLiveBookmark(bookmarkId)

    fun updateBookmark(bookmark: Bookmark) {
        bookmarkDao.updateBookmark(bookmark)
    }

    fun getBookmark(bookmarkId: Long): Bookmark {
        return bookmarkDao.loadBookmark(bookmarkId)
    }
}