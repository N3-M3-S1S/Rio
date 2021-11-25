package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.itemOption
import com.nemesis.rio.presentation.mplus.scores.MythicPlusScoresType
import com.nemesis.rio.presentation.mplus.scores.stringResId
import com.nemesis.rio.presentation.utils.extensions.getEnum
import com.nemesis.rio.presentation.view.fragment.BaseOptionSelectDialogFragment

class CharacterMythicPlusScoresTypeSelectDialogFragment : BaseOptionSelectDialogFragment() {

    companion object {
        const val REQUEST_KEY = "select_scores_type"
        const val SELECTED_SCORES_TYPE_KEY = "selected_scores_type"

        fun create(selectedScoresType: MythicPlusScoresType) =
            CharacterMythicPlusScoresTypeSelectDialogFragment().apply {
                arguments = bundleOf(
                    SELECTED_SCORES_TYPE_KEY to selectedScoresType
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.optionSelectToolbar.setTitle(R.string.character_mplus_scores_type)
        setupScoreTypeSelectList()
    }

    private fun setupScoreTypeSelectList() {
        viewBinding.optionSelectRecyclerview.withModels {
            val scoreTypes = MythicPlusScoresType.values()
            val selectedScoresType = requireArguments()
                .getEnum<MythicPlusScoresType>(SELECTED_SCORES_TYPE_KEY)

            scoreTypes.forEach { scoreType ->
                itemOption {
                    id(scoreType.hashCode())
                    stringResId(scoreType.stringResId)
                    isSelected(scoreType == selectedScoresType)
                    onClick { onScoresTypeSelected(scoreType) }
                }
            }
        }
    }

    private fun onScoresTypeSelected(selectedScoresType: MythicPlusScoresType) {
        parentFragmentManager.setFragmentResult(
            REQUEST_KEY,
            bundleOf(SELECTED_SCORES_TYPE_KEY to selectedScoresType)
        )
        dismiss()
    }
}
