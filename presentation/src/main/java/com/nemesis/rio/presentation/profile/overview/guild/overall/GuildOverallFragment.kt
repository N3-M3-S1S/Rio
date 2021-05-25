package com.nemesis.rio.presentation.profile.overview.guild.overall

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.databinding.FragmentGuildOverallBinding
import com.nemesis.rio.presentation.ranks.RanksSpan
import org.koin.androidx.viewmodel.ext.android.getViewModel

class GuildOverallFragment : Fragment(R.layout.fragment_guild_overall) {
    private lateinit var viewModel: GuildOverallViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = requireParentFragment().getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentGuildOverallBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@GuildOverallFragment.viewModel
            guildOverallRaidRanksSpan.setOnClickListener(::showSelectRanksSpanPopupMenu)
        }
    }

    private fun showSelectRanksSpanPopupMenu(popupMenuAnchorView: View) {
        PopupMenu(requireContext(), popupMenuAnchorView).apply {
            inflate(R.menu.fragment_guild_overall_rank_span_menu)
            setOnMenuItemClickListener {
                handleSelectedRankSpanOption(it.itemId)
                true
            }
            show()
        }
    }

    private fun handleSelectedRankSpanOption(@IdRes selectedOptionId: Int) {
        val selectedRankSpan = when (selectedOptionId) {
            R.id.rank_span_world -> RanksSpan.WORLD
            R.id.rank_span_region -> RanksSpan.REGION
            R.id.rank_span_realm -> RanksSpan.REALM
            else -> error("Unknown selected rank span option id: $selectedOptionId")
        }
        viewModel.rankSpanChanged(selectedRankSpan)
    }
}
