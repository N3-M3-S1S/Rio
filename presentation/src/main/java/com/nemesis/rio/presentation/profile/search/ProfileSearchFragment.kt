package com.nemesis.rio.presentation.profile.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nemesis.rio.domain.server.Region
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.databinding.FragmentProfileSearchBinding
import com.nemesis.rio.presentation.server.realm.RealmSelectDialogFragment
import com.nemesis.rio.presentation.server.region.RegionSelectDialogFragment
import com.nemesis.rio.presentation.utils.extensions.getEnum
import com.nemesis.rio.presentation.utils.extensions.observeFragmentResult
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import splitties.systemservices.inputMethodManager
import splitties.views.onClick
import splitties.views.recyclerview.fixedSize

class ProfileSearchFragment : Fragment(R.layout.fragment_profile_search) {
    private val viewBinding: FragmentProfileSearchBinding by viewBinding()
    private val viewModel: ProfileSearchViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycle.addObserver(viewModel)
        setupViewBinding()
        observeSearchButtonClick()
        observeKeyboardSearchButtonClick()
        observeOptionSelectEvent()
        observeRegionSelect()
        observeRealmSelect()
    }

    private fun setupViewBinding() {
        with(viewBinding) {
            viewModel = this@ProfileSearchFragment.viewModel
            lifecycleOwner = this@ProfileSearchFragment.viewLifecycleOwner
        }
    }

    private fun observeSearchButtonClick() {
        viewBinding.searchButton.onClick {
            hideKeyboard()
            viewModel.search()
        }
    }

    private fun observeKeyboardSearchButtonClick() {
        viewBinding.profileNameEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH && viewModel.isSearchEnabled.value == true) {
                hideKeyboard()
                viewModel.search()
            }
            true
        }
    }

    private fun hideKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(viewBinding.profileNameEditText.windowToken, 0)
    }

    private fun observeOptionSelectEvent() {
        viewModel.profileSearchOptionSelectEvent.onEach { event ->
            val optionSelectDialogFragment = getOptionSelectDialogFragmentForEvent(event)
            optionSelectDialogFragment.show(childFragmentManager, null)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun getOptionSelectDialogFragmentForEvent(profileSearchOptionSelectEvent: ProfileSearchOptionSelectEvent) =
        when (profileSearchOptionSelectEvent) {
            is SelectRealm -> RealmSelectDialogFragment.create(
                profileSearchOptionSelectEvent.realmList,
                profileSearchOptionSelectEvent.selectedRealm
            )
            is SelectRegion -> RegionSelectDialogFragment.create(profileSearchOptionSelectEvent.selectedRegion)
        }

    private fun observeRegionSelect() {
        observeFragmentResult(RegionSelectDialogFragment.REQUEST_KEY) {
            val selectedRegion = it.getEnum<Region>(RegionSelectDialogFragment.SELECTED_REGION_KEY)
            viewModel.onRegionChanged(selectedRegion)
        }
    }

    private fun observeRealmSelect() {
        observeFragmentResult(RealmSelectDialogFragment.REQUEST_KEY) {
            val selectedRealm = it.getString(RealmSelectDialogFragment.SELECTED_REALM_KEY)!!
            viewModel.onRealmChanged(selectedRealm)
        }
    }

    override fun onStart() {
        super.onStart()
        setupSearchHistoryRecyclerViewAsNestedScrollViewChild()
    }

    /**
     * This method sets search history recycler view's fixed size to false so its size changes when items count changes (e.g. it removes blank space when an item is removed from the list)
     * It also disables over scroll glow, so there will be no glow at the recyclerview when a user over scrolls by touching search history fragment.
     */
    private fun setupSearchHistoryRecyclerViewAsNestedScrollViewChild() {
        val profileSearchHistoryRecyclerView =
            viewBinding.profileSearchHistoryFragment.findViewById<RecyclerView>(R.id.data_list_recyclerview)
        with(profileSearchHistoryRecyclerView) {
            fixedSize = false
            overScrollMode = View.OVER_SCROLL_NEVER
        }
    }
}
