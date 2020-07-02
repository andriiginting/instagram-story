package com.andriiginting.instagramstories

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val target = object : Target {
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            //no op
        }

        override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
            //no op
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            ivImageStories.apply {
                tag = this
                setImageBitmap(bitmap)
            }
            Log.d("image-picasso", "bitmaps $bitmap")
            bitmap?.let(::renderImageBackground)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        renderImage()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun renderImage() {
        Picasso.get()
            .load("https://lastfm.freetls.fastly.net/i/u/770x0/3390466076624a4a9dfbc4e7519714b5.webp#3390466076624a4a9dfbc4e7519714b5")
            .resize(600, 600)
            .centerCrop()
            .into(target)
    }

    private fun renderImageBackground(bitmap: Bitmap) {
        Palette.from(bitmap)
            .generate { palette ->
                val swatch = palette?.darkMutedSwatch
                if (swatch == null) {
                    Log.d("bg-color", "swatch is null")
                }

//                swatch?.rgb?.let(storiesContainer::setBackgroundColor)
                renderGradientBackground(swatch?.rgb, palette?.darkVibrantSwatch?.rgb)
            }
    }

    /*top indicate top color
    bottom indicate bottom color for gradient background
     */
    private fun renderGradientBackground(top: Int?, bottom: Int?) {
        val gradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(top!!, bottom!!)
        ).apply { cornerRadius = 0f }
        storiesContainer.setBackgroundDrawable(gradient)
    }
}
