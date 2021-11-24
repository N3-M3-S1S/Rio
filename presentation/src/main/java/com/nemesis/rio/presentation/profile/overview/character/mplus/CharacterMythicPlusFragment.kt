package com.nemesis.rio.presentation.profile.overview.character.mplus

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.nemesis.rio.domain.mplus.ranks.MythicPlusRanksScope
import com.nemesis.rio.domain.mplus.runs.sorting.MythicPlusRunsSortingOption
import com.nemesis.rio.domain.sorting.SortingOrder
import com.nemesis.rio.presentation.mplus.ranks.MythicPlusRanksType
import com.nemesis.rio.presentation.mplus.runs.MythicPlusRunItemModel
import com.nemesis.rio.presentation.mplus.runs.mythicPlusRunItemModelPreloader
import com.nemesis.rio.presentation.profile.overview.character.mplus.ranks.MythicPlusRanksScopeSelectDialogFragment
import com.nemesis.rio.presentation.profile.overview.character.mplus.ranks.MythicPlusRanksTypeSelectDialogFragment
import com.nemesis.rio.presentation.profile.overview.character.mplus.runs.CharacterMythicPlusRunsSortingOptionSelectDialogFragment
import com.nemesis.rio.presentation.profile.overview.character.mplus.scores.CharacterMythicPlusScoresSeasonSelectDialogFragment
import com.nemesis.rio.presentation.sorting.SortingOrderSelectDialogFragment
import com.nemesis.rio.presentation.utils.extensions.getEnum
import com.nemesis.rio.presentation.utils.extensions.getEnumOrNull
import com.nemesis.rio.presentation.utils.extensions.observeFragmentResult
import com.nemesis.rio.presentation.view.epoxy.preloader.coil.addCoilPreloader
import com.nemesis.rio.presentation.view.fragment.BaseDataListFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class CharacterMythicPlusFragment : BaseDataListFragment() {
    private lateinit var mythicPlusDataController: CharacterMythicPlusDataController
    private lateinit var viewModel: CharacterMythicPlusViewModel
    override val isDataListLoading: LiveData<Boolean>
        get() = viewModel.isLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = requireParentFragment().getViewModel()
        mythicPlusDataController = get { parametersOf(viewModel) }
        recycledViewPool.setMaxRecycledViews(MythicPlusRunItemModel.VIEW_TYPE, 10)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(viewModel)
        setupRecyclerView()
        observeCharacterMythicPlusData()
        observeCharacterMythicPlusEvents()
        observeSelectRanksType()
        observeSelectRanksScope()
        observeSelectSeason()
        observeSelectRunsSortingOption()
        observeSelectRunsSortingOrder()
    }

    private fun setupRecyclerView() {
        setupRecyclerViewGrid()
        with(viewBinding.dataListRecyclerview) {
            addCoilPreloader(
                requireContext(),
                preloader = mythicPlusRunItemModelPreloader
            )
            setController(mythicPlusDataController)
        }
    }

    private fun setupRecyclerViewGrid() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        viewBinding.dataListRecyclerview.layoutManager = gridLayoutManager
    }

    private fun observeCharacterMythicPlusData() {
        viewModel.characterMythicPlusData.observe(viewLifecycleOwner) {
            mythicPlusDataController.setData(it)
        }
    }

    private fun observeCharacterMythicPlusEvents() {
        viewModel.characterMythicPlusOptionSelectEvent.onEach { event ->
            val optionSelectDialogFragment = getOptionSelectDialogFragmentForEvent(event)
            optionSelectDialogFragment.show(childFragmentManager, null)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun getOptionSelectDialogFragmentForEvent(event: CharacterMythicPlusOptionSelectEvent) =
        when (event) {
            is SelectRanksType -> MythicPlusRanksTypeSelectDialogFragment.create(event.selectedRanksType)

            is SelectRanksScope -> MythicPlusRanksScopeSelectDialogFragment.create(
                event.selectedRanksScope,
                event.faction
            )

            is SelectScoresSeason -> CharacterMythicPlusScoresSeasonSelectDialogFragment.create(
                event.expansionsWithSeasons,
                event.selectedSeason
            )
            is SelectRunsSoringOption -> CharacterMythicPlusRunsSortingOptionSelectDialogFragment.create(
                event.selectedSortingOption
            )
            is SelectRunsSortingOrder -> SortingOrderSelectDialogFragment.create(event.selectedSortingOrder)
        }

    private fun observeSelectRanksType() {
        observeFragmentResult(MythicPlusRanksTypeSelectDialogFragment.REQUEST_KEY) {
            val selectedRanksType = it.getEnum<MythicPlusRanksType>(
                MythicPlusRanksTypeSelectDialogFragment.SELECTED_RANKS_TYPE_KEY
            )
            viewModel.onRanksTypeChanged(selectedRanksType)
        }
    }

    private fun observeSelectRanksScope() {
        observeFragmentResult(MythicPlusRanksScopeSelectDialogFragment.REQUEST_KEY) {
            val selectedRanksScope =
                it.getEnumOrNull<MythicPlusRanksScope>(MythicPlusRanksScopeSelectDialogFragment.SELECTED_SCOPE_KEY)
            viewModel.onRanksScopeChanged(selectedRanksScope)
        }
    }


    private fun observeSelectSeason() {
        observeFragmentResult(CharacterMythicPlusScoresSeasonSelectDialogFragment.REQUEST_KEY) {
            val selectedSeason =
                it.getString(CharacterMythicPlusScoresSeasonSelectDialogFragment.SELECTED_SEASON_KEY)!!
            viewModel.onSeasonChanged(selectedSeason)
        }
    }

    private fun observeSelectRunsSortingOption() {
        observeFragmentResult(CharacterMythicPlusRunsSortingOptionSelectDialogFragment.REQUEST_KEY) {
            val selectedSortingOption = it.getEnum<MythicPlusRunsSortingOption>(
                CharacterMythicPlusRunsSortingOptionSelectDialogFragment.SELECTED_OPTION_KEY
            )
            viewModel.onRunsSortingOptionChanged(selectedSortingOption)
        }
    }

    private fun observeSelectRunsSortingOrder() {
        observeFragmentResult(SortingOrderSelectDialogFragment.REQUEST_KEY) {
            val selectedSortingOrder =
                it.getEnum<SortingOrder>(SortingOrderSelectDialogFragment.SELECTED_SORTING_ORDER_KEY)
            viewModel.onRunsSortingOrderChanged(selectedSortingOrder)
        }
    }
}
