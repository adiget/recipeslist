package com.ags.annada.recipes.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.ags.annada.recipes.R
import com.squareup.picasso.Picasso

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && visibility != null) {
        visibility.observe(
            parentActivity,
            Observer { value -> view.visibility = value ?: View.VISIBLE })
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: LiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
    }
}

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, url: LiveData<String>?) {
    val context: Context? = view.context
    val parentActivity: AppCompatActivity? = view.getParentActivity()

    if (parentActivity != null && context != null && url != null) {
        url.observe(parentActivity, Observer { value -> Picasso.with(context)
            .load(value)
            .placeholder(context.resources.getDrawable(R.drawable.ic_image_white_24dp))
            .transform(PaletteTransformation.instance())
            .fit().centerCrop()
            .into(view, object : PaletteTransformation.PaletteCallback(view){
                override fun onSuccess(palette: Palette) {
                    val color = palette.getLightVibrantColor(
                        context.resources.getColor(R.color.light_gray))
                    view.setBackgroundColor(color)
                }

                override fun onError() {
                }
            })
        })
    }
}