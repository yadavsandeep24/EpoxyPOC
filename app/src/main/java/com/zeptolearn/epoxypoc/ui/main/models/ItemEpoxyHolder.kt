package com.zeptolearn.epoxypoc.ui.main.models

import android.graphics.Color
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.zeptolearn.epoxypoc.R
import com.zeptolearn.epoxypoc.ui.main.helpers.KotlinEpoxyHolder
import com.zeptolearn.epoxypoc.ui.main.loadImage

// This more traditional style uses an Epoxy view holder pattern.
// The KotlinHolder is used to cache the view look ups, but uses property delegates to simplify it.
// The annotations allow for code generation of a subclass, which has equals/hashcode, and some other
// helpers. An extension function is also generated to make it easier to use this in an EpoxyController.
@EpoxyModelClass(layout = R.layout.list_item_layout)
abstract class ItemEpoxyHolder : EpoxyModelWithHolder<Holder>() {

    @EpoxyAttribute lateinit var listener: () -> Unit
    @EpoxyAttribute lateinit var title: String
    @EpoxyAttribute lateinit var imageUrl: String
    @EpoxyAttribute  lateinit var titleColor: String

    override fun bind(holder: Holder) {
        holder.button.text = title
        holder.button.setTextColor(Color.parseColor(titleColor))
        holder.button.setOnClickListener { listener() }
        holder.glide.loadImage(imageUrl).into(holder.imageView)
    }
}

class Holder : KotlinEpoxyHolder() {
    val button by bind<Button>(R.id.button)
    val imageView by bind<AppCompatImageView>(R.id.iv)
    val glide by lazy { Glide.with(imageView.context) }
}
