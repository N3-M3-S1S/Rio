package com.nemesis.rio.presentation.profile.overview.character.mplus.ranks

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.profile.Faction
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.itemOption
import com.nemesis.rio.presentation.profile.stringResId
import com.nemesis.rio.presentation.utils.extensions.getEnum
import com.nemesis.rio.presentation.utils.extensions.getEnumOrNull
import com.nemesis.rio.presentation.view.fragment.BaseOptionSelectDialogFragment
import splitties.resources.appStr

class MythicPlusRanksScopeSelectDialogFragment : BaseOptionSelectDialogFragment() {
    private val stringToMythicPlusRankScope = mutableMapOf<String, MythicPlusRanksScope?>()
    private val factionKey = "faction"

    companion object {
        const val SELECTED_SCOPE_KEY = "scope"
        const val REQUEST_KEY = "ranks_scope_select"

        fun create(selectedScope: MythicPlusRanksScope?, faction: Faction) =
            MythicPlusRanksScopeSelectDialogFragment().apply {
                arguments = bundleOf(SELECTED_SCOPE_KEY to selectedScope, factionKey to faction)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.optionSelectToolbar.setTitle(R.string.character_mplus_ranks_scope_select_title)
        setupRanksScopeList()
    }

    private fun setupRanksScopeList() {
        viewBinding.optionSelectRecyclerview.withModels {
            initializeMythicPlusRanksScopeOptions()
            val selectedScope = requireArguments().getEnumOrNull<MythicPlusRanksScope>(SELECTED_SCOPE_KEY)

            stringToMythicPlusRankScope.forEach { (ranksScopeString, ranksScope) ->
                itemOption {
                    id(ranksScopeString)
                    string(ranksScopeString)
                    isSelected(ranksScope == selectedScope)
                    onClick { onRanksScopeSelected(ranksScope) }
                }
            }
        }
    }

    private fun initializeMythicPlusRanksScopeOptions() {
        val faction = requireArguments().getEnum<Faction>(factionKey)

        val allScopesString = appStr(R.string.ranks_scope_all)
        val globalScopeString = appStr(R.string.ranks_span_global)
        val factionScopeString = appStr(faction.stringResId)

        stringToMythicPlusRankScope[allScopesString] = null
        stringToMythicPlusRankScope[globalScopeString] = MythicPlusRanksScope.GLOBAL
        stringToMythicPlusRankScope[factionScopeString] = MythicPlusRanksScope.FACTION
    }

    private fun onRanksScopeSelected(ranksScope: MythicPlusRanksScope?) {
        parentFragmentManager.setFragmentResult(
            REQUEST_KEY,
            bundleOf(SELECTED_SCOPE_KEY to ranksScope)
        )
        dismiss()
    }
}
