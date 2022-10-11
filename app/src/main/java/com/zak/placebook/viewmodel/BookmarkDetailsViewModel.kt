package com.zak.placebook.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.zak.placebook.model.Bookmark
import com.zak.placebook.repository.BookmarkRepo
import com.zak.placebook.util.ImageUtils

class BookmarkDetailsViewModel(application: Application): AndroidViewModel(application) {
    private val bookmarkRepo = BookmarkRepo(getApplication())

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
}