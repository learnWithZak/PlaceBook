package com.zak.placebook.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.zak.placebook.model.Bookmark
import com.zak.placebook.repository.BookmarkRepo
import com.zak.placebook.util.ImageUtils

class MapsViewModel(application: Application): AndroidViewModel(application) {

    private val TAG = MapsViewModel::class.simpleName
    private val bookmarkRepo = BookmarkRepo(getApplication())
    private var bookmarks: LiveData<List<BookmarkView>>? = null

    fun addBookmarkFromPlace(place: Place, image: Bitmap?) {
        val bookmark = bookmarkRepo.createBookmark()
        bookmark.placeId = place.id
        bookmark.name = place.name.toString()
        bookmark.longitude = place.latLng?.longitude ?: 0.0
        bookmark.latitude = place.latLng?.latitude ?: 0.0
        bookmark.phone = place.phoneNumber?.toString() ?: ""
        bookmark.address = place.address?.toString() ?: ""
        bookmark.category = getPlaceCategory(place)

        val newId = bookmarkRepo.addBookmark(bookmark)
        image?.let { bookmark.setImage(it, getApplication()) }
        Log.i(TAG, "New bookmark $newId added to the database")
    }

    data class BookmarkView(
        var id: Long? = null,
        var location: LatLng = LatLng(0.0, 0.0),
        var name: String = "",
        var phone: String = "",
        val categoryResourceId: Int? = null
    ) {
        fun getImage(context: Context) = id?.let {
            ImageUtils.loadBitmapFromFile(context, Bookmark.generateImageFilename(it))
        }
}

    private fun bookmarkToBookmarkView(bookmark: Bookmark) = BookmarkView(
        id = bookmark.id,
        location = LatLng(bookmark.latitude, bookmark.longitude),
        name = bookmark.name,
        phone = bookmark.phone,
        categoryResourceId = bookmarkRepo.getCategoryResourceId(bookmark.category)
    )

    private fun mapBookmarksToBookmarkView() {
        bookmarks = Transformations.map(bookmarkRepo.allBookmarks) {
            repoBookmarks -> repoBookmarks.map {
                bookmarkToBookmarkView(it)
        }
        }
    }

    fun getBookmarkViews(): LiveData<List<BookmarkView>>? {
        if (bookmarks == null) {
            mapBookmarksToBookmarkView()
        }
        return bookmarks
    }

    private fun getPlaceCategory(place: Place): String {
        var category = "Other"
        val types = place.types
        types?.let { placeTypes ->
            if(placeTypes.size > 0) {
                val placeType = placeTypes[0]
                category = bookmarkRepo.placeTypeToCategory(placeType)
            }
        }
        return category
    }
}