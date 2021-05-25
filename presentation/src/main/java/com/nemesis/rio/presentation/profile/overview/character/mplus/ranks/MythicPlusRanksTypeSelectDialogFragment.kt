package com.nemesis.rio.presentation.profile.overview.character.mplus.ranks

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.itemOption
import com.nemesis.rio.presentation.mplus.ranks.MythicPlusRanksType
import com.nemesis.rio.presentation.mplus.ranks.stringResID
import com.nemesis.rio.presentation.utils.extensions.getEnum
import com.nemesis.rio.presentation.view.fragment.BaseOptionSelectDialogFragment

class MythicPlusRanksTypeSelectDialogFragment : BaseOptionSelectDialogFragment() {

    companion object {
        const val REQUEST_KEY = "ranks_type_select"
        const val SELECTED_RANKS_TYPE_KEY = "selected_ranks_type"

        fun create(selectedRanksType: MythicPlusRanksType) =
            MythicPlusRanksTypeSelectDialogFragment().apply {
                arguments = bundleOf(SELECTED_RANKS_TYPE_KEY to selectedRanksType)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.optionSelectToolbar.setTitle(R.string.character_mplus_ranks_type_select_title)
        setupRanksTypeList()
    }

    private fun setupRanksTypeList() {
        viewBinding.optionSelectRecyclerview.withModels {
            val ranksTypes = enumValues<MythicPlusRanksType>()
            val selectedRanksType =
                requireArguments().getEnum<MythicPlusRanksType>(SELECTED_RANKS_TYPE_KEY)

            ranksTypes.forEach { ranksType ->
                itemOption {
                    id(ranksType.hashCode())
                    stringResId(ranksType.stringResID)
                    isSelected(selectedRanksType == ranksType)
                    onClick { onRanksTypeSelected(ranksType) }
                }
            }
        }
    }

    private fun onRanksTypeSelected(ranksType: MythicPlusRanksType) {
        parentFragmentManager.setFragmentResult(
            REQUEST_KEY,
            bundleOf(SELECTED_RANKS_TYPE_KEY to ranksType)
        )
        dismiss()
    }
}
