package com.nemesis.rio.presentation.profile.overview.character.mplus.scores

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.nemesis.rio.domain.game.Expansion
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.game.stringResId
import com.nemesis.rio.presentation.itemOption
import com.nemesis.rio.presentation.utils.extensions.getEnum
import com.nemesis.rio.presentation.view.fragment.BaseOptionSelectDialogFragment

class CharacterMythicPlusScoresExpansionSelectDialogFragment : BaseOptionSelectDialogFragment() {
    private val expansionsKey = "expansions"

    companion object {
        const val REQUEST_KEY = "scores_expansion"
        const val SELECTED_EXPANSION_KEY = "selected_expansion"

        fun create(expansions: List<Expansion>, selectedExpansion: Expansion) =
            CharacterMythicPlusScoresExpansionSelectDialogFragment().apply {
                arguments = bundleOf(
                    expansionsKey to expansions,
                    SELECTED_EXPANSION_KEY to selectedExpansion
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.optionSelectToolbar.setTitle(R.string.expansion_title)
        setupExpansionsList()
    }

    private fun setupExpansionsList() {
        viewBinding.optionSelectRecyclerview.withModels {
            @Suppress("UNCHECKED_CAST") val expansions =
                requireArguments().get(expansionsKey) as List<Expansion>
            val selectedExpansion = requireArguments().getEnum<Expansion>(SELECTED_EXPANSION_KEY)

            expansions.forEach { expansion ->
                itemOption {
                    id(expansion.hashCode())
                    stringResId(expansion.stringResId)
                    isSelected(expansion == selectedExpansion)
                    onClick { onExpansionSelected(expansion) }
                }
            }
        }
    }

    private fun onExpansionSelected(expansion: Expansion) {
        parentFragmentManager
            .setFragmentResult(REQUEST_KEY, bundleOf(SELECTED_EXPANSION_KEY to expansion))
        dismiss()
    }
}
