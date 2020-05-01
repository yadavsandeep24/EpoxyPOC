package com.zeptolearn.epoxypoc.ui.main.models

import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.zeptolearn.epoxypoc.R
import com.zeptolearn.epoxypoc.ui.main.helpers.KotlinEpoxyHolder
import com.zeptolearn.epoxypoc.ui.main.loadImage

@EpoxyModelClass(layout = R.layout.carousel_item_layout)
abstract class CustomCarouselItem
    : EpoxyModelWithHolder<CustomCarouselItem.Holder>() {

    @EpoxyAttribute lateinit var listener: () -> Unit
    @EpoxyAttribute lateinit var title: String
    @EpoxyAttribute  lateinit var titleColor: String
    @EpoxyAttribute lateinit var imageUrl: String

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.textTitle.text = title
        holder.imageView.setOnClickListener { listener() }
        holder.textTitle.setTextColor(Color.parseColor(titleColor))
        holder.glide.loadImage(imageUrl).into(holder.imageView)
    }
    class Holder : KotlinEpoxyHolder() {
        val textTitle: TextView by bind(R.id.textTitle)
        val imageView by bind<AppCompatImageView>(R.id.iv)
        val glide by lazy { Glide.with(imageView.context) }
    }
}