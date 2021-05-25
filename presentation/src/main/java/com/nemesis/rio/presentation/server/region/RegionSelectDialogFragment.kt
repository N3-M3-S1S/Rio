package com.nemesis.rio.presentation.server.region

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.itemOption
import com.nemesis.rio.presentation.utils.extensions.getEnum
import com.nemesis.rio.presentation.view.fragment.BaseOptionSelectDialogFragment

class RegionSelectDialogFragment : BaseOptionSelectDialogFragment() {

    companion object {
        const val REQUEST_KEY = "region_select"
        const val SELECTED_REGION_KEY = "selected_region"
        fun create(selectedRegion: Region) = RegionSelectDialogFragment().apply {
            arguments = bundleOf(SELECTED_REGION_KEY to selectedRegion)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.optionSelectToolbar.setTitle(R.string.region_title)
        setupRegionSelectList()
    }

    private fun setupRegionSelectList() {
        viewBinding.optionSelectRecyclerview.withModels {
            val regions = enumValues<Region>()
            val selectedRegion = requireArguments().getEnum<Region>(SELECTED_REGION_KEY)
            regions.forEach { region ->
                itemOption {
                    id(region.hashCode())
                    stringResId(region.stringResId)
                    isSelected(region == selectedRegion)
                    onClick { onRegionSelected(region) }
                }
            }
        }
    }

    private fun onRegionSelected(region: Region) {
        parentFragmentManager.setFragmentResult(
            REQUEST_KEY,
            bundleOf(SELECTED_REGION_KEY to region)
        )
        dismiss()
    }
}
