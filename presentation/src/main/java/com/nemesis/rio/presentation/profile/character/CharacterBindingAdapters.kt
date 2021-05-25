package com.nemesis.rio.presentation.profile.character

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.nemesis.rio.domain.profile.character.attributes.Covenant
import com.nemesis.rio.presentation.profile.character.attributes.imageResID

@BindingAdapter("covenantImage")
fun ImageView.setCovenantImage(covenant: Covenant?) {
    covenant?.let { load(covenant.imageResID) }
}
