package com.zak.placebook.adapter

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import com.zak.placebook.databinding.ContentBookmarkInfoBinding
import com.zak.placebook.ui.MapsActivity

class BookmarkInfoWindowAdapter(context: Activity): InfoWindowAdapter {

    private val binding = ContentBookmarkInfoBinding.inflate(context.layoutInflater)

    override fun getInfoContents(p0: Marker): View? {
        binding.title.text = p0.title ?: "UNKNOWN"
        binding.phone.text = p0.snippet ?: "UNKNOWN"
        val imageView = binding.photo
        imageView.setImageBitmap((p0.tag as MapsActivity.PlaceInfo).image)
        return binding.root
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }
}