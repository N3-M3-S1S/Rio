package com.nemesis.rio.presentation.profile.overview.guild.ranks

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.nemesis.rio.presentation.view.fragment.BaseDataListFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

class GuildRaidRanksFragment : BaseDataListFragment() {
    private val ranksListDataController: GuildRaidRanksListDataController by inject()
    private lateinit var viewModel: GuildRaidRanksViewModel
    override val isDataListLoading: LiveData<Boolean>
        get() = viewModel.isLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = requireParentFragment().getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRaidRanksListData()
        viewBinding.dataListRecyclerview.setController(ranksListDataController)
    }

    private fun observeRaidRanksListData() {
        viewModel.guildRaidRanksListData.observe(viewLifecycleOwner) {
            ranksListDataController.setData(it)
        }
    }
}
