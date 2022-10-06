package com.zak.placebook.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.zak.placebook.db.PlaceBookDatabase
import com.zak.placebook.model.Bookmark

class BookmarkRepo(context: Context) {

    private val db = PlaceBookDatabase.getInstance(context)
    private val bookmarkDao = db.bookmarkDao()

    fun addBookmark(bookmark: Bookmark): Long? {
        val newId = bookmarkDao.insertBookmark(bookmark)
        bookmark.id = newId
        return newId
    }

    fun createBookmark(): Bookmark {
        return Bookmark()
    }

    val allBookmarks: LiveData<List<Bookmark>>
        get() {
            return bookmarkDao.loadAll()
        }
}