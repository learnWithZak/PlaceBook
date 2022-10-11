package com.zak.placebook.ui

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zak.placebook.R
import com.zak.placebook.databinding.ActivityBookmarkDetailsBinding
import com.zak.placebook.viewmodel.BookmarkDetailsViewModel

class BookmarkDetailsActivity : AppCompatActivity() {
    private lateinit var databinding: ActivityBookmarkDetailsBinding
    private val bookmarkDetailsViewModel by viewModels<BookmarkDetailsViewModel>()
    private val bookmarkDetailsView: BookmarkDetailsViewModel.BookmarkDetailsView? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_bookmark_details)
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(databinding.toolbar)
    }

    private fun populateImageView() {
        bookmarkDetailsView?.let { bookmarkView ->
            val placeImage = bookmarkView.getImage(this)
            placeImage?.let {
                databinding.imageViewPlace.setImageBitmap(placeImage)
            }
        }
    }
}
