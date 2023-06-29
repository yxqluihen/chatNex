package com.edit.chatnex.utils

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import java.io.InputStream
import java.net.URL

class HtmlImageGetter(private val textView: TextView) : Html.ImageGetter {

    override fun getDrawable(source: String): Drawable {
        val drawable = getDrawableFromUrl(source)
        drawable?.setBounds(100, 100, drawable.intrinsicWidth, drawable.intrinsicHeight)
        return drawable ?: ColorDrawable(Color.TRANSPARENT)
    }

    private fun getDrawableFromUrl(url: String): Drawable? {
        return try {
            val input = URL(url).content as InputStream
            val drawable = Drawable.createFromStream(input, null)
            input.close()
            drawable
        } catch (e: Exception) {
            null
        }
    }

//    override fun getDrawable(source: String?): Drawable {
//        val drawable = BitmapDrawable(textView.resources, BitmapFactory.decodeFile(source))
//        drawable.setBounds(5, 5, drawable.intrinsicWidth, drawable.intrinsicHeight)
//        return drawable
//    }
}