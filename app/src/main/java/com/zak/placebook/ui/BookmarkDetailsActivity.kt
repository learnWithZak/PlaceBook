package com.zak.placebook.ui

import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zak.placebook.R
import com.zak.placebook.databinding.ActivityBookmarkDetailsBinding
import com.zak.placebook.viewmodel.BookmarkDetailsViewModel

class BookmarkDetailsActivity : AppCompatActivity() {
    private lateinit var databinding: ActivityBookmarkDetailsBinding
    private val bookmarkDetailsViewModel by viewModels<BookmarkDetailsViewModel>()
    private var bookmarkDetailsView: BookmarkDetailsViewModel.BookmarkDetailsView? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_bookmark_details)
        setupToolbar()
        getIntentData()
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.menu_bookmark_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_save -> {
            saveChanges()
            true
        }
        else -> super.onOptionsItemSelected(item)
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

    private fun getIntentData() {
        val bookmarkId = intent.getLongExtra(MapsActivity.EXTRA_BOOKMARK_ID, 0)
        bookmarkDetailsViewModel.getBookmark(bookmarkId)?.observe(this) {
            it?.let {
                bookmarkDetailsView = it
                databinding.bookmarkDetailsView = it
                populateImageView()
            }
        }
    }

    private fun saveChanges() {
        val name = databinding.editTextName.text.toString()
        if (name.isEmpty()) {
            return
        }
        bookmarkDetailsView?.let { bookmarkView ->
            bookmarkView.name = databinding.editTextName.text.toString()
            bookmarkView.notes = databinding.editTextNotes.text.toString()
            bookmarkView.address = databinding.editTextAddress.text.toString()
            bookmarkView.phone = databinding.editTextPhone.text.toString()
            bookmarkDetailsViewModel.updateBookmark(bookmarkView)
        }
        finish()
    }
}
