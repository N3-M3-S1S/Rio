package com.nemesis.rio.presentation.utils.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.observeFragmentResult(requestKey: String, onResult: (Bundle) -> Unit) {
    childFragmentManager.setFragmentResultListener(
        requestKey,
        viewLifecycleOwner
    ) { _, bundle ->
        onResult(bundle)
    }
}
