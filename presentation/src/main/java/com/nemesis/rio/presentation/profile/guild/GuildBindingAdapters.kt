package com.nemesis.rio.presentation.profile.guild

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nemesis.rio.presentation.R
import splitties.resources.str

@BindingAdapter("guildName")
fun TextView.setGuildName(guildName: String?) {
    guildName?.let {
        text = str(R.string.profile_guild_name_format, it)
    }
}
