package com.nemesis.rio.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.databinding.FragmentOptionSelectBinding
import splitties.views.inflate

abstract class BaseOptionSelectDialogFragment : BottomSheetDialogFragment() {
    protected val viewBinding: FragmentOptionSelectBinding by viewBinding(R.id.option_select_root)

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_option_select)
}
