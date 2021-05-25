package com.nemesis.rio.presentation.profile.search

import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.textfield.TextInputLayout
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.profile.ProfileType
import com.nemesis.rio.presentation.server.region.stringResId
import splitties.resources.appStr

@BindingAdapter("profileSearch_profileTypeHint")
fun TextInputLayout.setProfileTypeHint(profileType: ProfileType) {
    when (profileType) {
        ProfileType.CHARACTER -> R.string.search_character_name_hint
        ProfileType.GUILD -> R.string.search_guild_name_hint
    }.let(::setHint)
}

@BindingAdapter("profileSearch_profileType")
fun RadioGroup.setSelectedProfileType(profileType: ProfileType) {
    val profileTypeRadioButton: RadioButton = when (profileType) {
        ProfileType.CHARACTER -> R.id.profile_type_character
        ProfileType.GUILD -> R.id.profile_type_guild
    }.let(::findViewById)

    if (!profileTypeRadioButton.isChecked) {
        profileTypeRadioButton.isChecked = true
    }
}

@InverseBindingAdapter(attribute = "profileSearch_profileType")
fun RadioGroup.getSelectedProfileType() = when (checkedRadioButtonId) {
    R.id.profile_type_character -> ProfileType.CHARACTER
    R.id.profile_type_guild -> ProfileType.GUILD
    else -> error("Unknown button id: $checkedRadioButtonId")
}

@BindingAdapter("profileSearch_profileTypeAttrChanged")
fun RadioGroup.listener(attrChange: InverseBindingListener) {
    setOnCheckedChangeListener { _, _ -> attrChange.onChange() }
}

@BindingConversion
fun regionToString(region: Region) = appStr(region.stringResId)
