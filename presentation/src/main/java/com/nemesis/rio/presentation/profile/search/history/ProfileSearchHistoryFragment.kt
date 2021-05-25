package com.nemesis.rio.presentation.profile.search.history

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.airbnb.epoxy.EpoxyTouchHelper
import com.nemesis.rio.presentation.ItemProfileSearchHistoryBindingModel_
import com.nemesis.rio.presentation.view.fragment.BaseDataListFragment
import com.nemesis.rio.presentation.view.widget.VerticalRecyclerViewItemDivider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileSearchHistoryFragment : BaseDataListFragment() {
    private val profileSearchHistoryController: ProfileSearchHistoryController by inject()
    private val viewModel: ProfileSearchHistoryViewModel by viewModel()
    override val isDataListLoading: LiveData<Boolean>
        get() = viewModel.isLoading

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeSearchHistory()
    }

    private fun setupRecyclerView() {
        with(viewBinding.dataListRecyclerview) {
            addItemDecoration(VerticalRecyclerViewItemDivider(requireContext()))
            setController(profileSearchHistoryController)
        }
        setupRecyclerViewItemSwipe()
    }

    private fun setupRecyclerViewItemSwipe() {
        EpoxyTouchHelper
            .initSwiping(viewBinding.dataListRecyclerview)
            .leftAndRight()
            .withTarget(ItemProfileSearchHistoryBindingModel_::class.java)
            .andCallbacks(object :
                EpoxyTouchHelper.SwipeCallbacks<ItemProfileSearchHistoryBindingModel_>() {
                override fun onSwipeCompleted(
                    model: ItemProfileSearchHistoryBindingModel_?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    profileSearchHistoryController.onSearchHistoryItemSwiped(position)
                }
            })
    }

    private fun observeSearchHistory() {
        viewModel.searchHistory.observe(viewLifecycleOwner) { profileSearchHistoryController.setData(it) }
    }
}
