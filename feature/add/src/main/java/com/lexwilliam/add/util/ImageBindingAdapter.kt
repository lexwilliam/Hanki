package com.lexwilliam.add.util

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.RestrictTo
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

@RestrictTo(RestrictTo.Scope.LIBRARY)
@BindingMethods(
    *[BindingMethod(
        type = ImageView::class,
        attribute = "android:tint",
        method = "setImageTintList"
    ), BindingMethod(
        type = ImageView::class,
        attribute = "android:tintMode",
        method = "setImageTintMode"
    )]
)
object ImageViewBindingAdapter {
    @BindingAdapter("android:src")
    fun setImageUri(view: ImageView, imageUri: String?) {
        if (imageUri == null) {
            view.setImageURI(null)
        } else {
            view.setImageURI(Uri.parse(imageUri))
        }
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageUri(view: ImageView, imageUri: Uri?) {
        view.setImageURI(imageUri)
    }

    @BindingAdapter("android:src")
    fun setImageDrawable(view: ImageView, drawable: Drawable?) {
        view.setImageDrawable(drawable)
    }
}