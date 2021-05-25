package com.nemesis.rio.presentation.profile.overview.character.mplus.runs

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.nemesis.rio.domain.mplus.runs.sorting.MythicPlusRunsSortingOption
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.itemOption
import com.nemesis.rio.presentation.mplus.runs.stringResId
import com.nemesis.rio.presentation.utils.extensions.getEnum
import com.nemesis.rio.presentation.view.fragment.BaseOptionSelectDialogFragment

class CharacterMythicPlusRunsSortingOptionSelectDialogFragment : BaseOptionSelectDialogFragment() {

    companion object {
        const val REQUEST_KEY = "runs_sorting_option"
        const val SELECTED_OPTION_KEY = "runs_selected_sorting_option"

        fun create(selectedSortingOption: MythicPlusRunsSortingOption) =
            CharacterMythicPlusRunsSortingOptionSelectDialogFragment().apply {
                arguments = bundleOf(SELECTED_OPTION_KEY to selectedSortingOption)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.optionSelectToolbar
            .setTitle(R.string.sort_by_title)
        setupSortingOptionList()
    }

    private fun setupSortingOptionList() {
        viewBinding.optionSelectRecyclerview.withModels {
            val sortingOptions = enumValues<MythicPlusRunsSortingOption>()
            val selectedSortingOption = requireArguments()
                .getEnum<MythicPlusRunsSortingOption>(SELECTED_OPTION_KEY)

            sortingOptions.forEach { sortingOption ->
                itemOption {
                    id(sortingOption.hashCode())
                    stringResId(sortingOption.stringResId)
                    isSelected(sortingOption == selectedSortingOption)
                    onClick { onSortingOptionSelected(sortingOption) }
                }
            }
        }
    }

    private fun onSortingOptionSelected(sortingOption: MythicPlusRunsSortingOption) {
        parentFragmentManager.setFragmentResult(
            REQUEST_KEY,
            bundleOf(SELECTED_OPTION_KEY to sortingOption)
        )
        dismiss()
    }
}
