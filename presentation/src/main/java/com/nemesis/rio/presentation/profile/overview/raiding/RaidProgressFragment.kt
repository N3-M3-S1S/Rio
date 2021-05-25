package com.nemesis.rio.presentation.profile.overview.raiding

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nemesis.rio.presentation.view.epoxy.preloader.coil.addCoilPreloader
import com.nemesis.rio.presentation.view.fragment.BaseDataListFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

abstract class RaidProgressFragment<VM : RaidProgressViewModel>(private val viewModelClass: KClass<VM>) :
    BaseDataListFragment() {
    private val raidProgressListController: RaidProgressListDataController by inject()
    private lateinit var viewModel: RaidProgressViewModel
    override val isDataListLoading: LiveData<Boolean>
        get() = viewModel.isLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = requireParentFragment()
            .getViewModel(clazz = viewModelClass)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        raidProgressListController.onRestoreInstanceState(savedInstanceState)
        setupRecyclerView()
        observeRaidProgressData()
    }

    private fun setupRecyclerView() {
        setupRecyclerViewForCurrentOrientation()
        viewBinding.dataListRecyclerview.setController(raidProgressListController)
    }

    private fun observeRaidProgressData() {
        viewModel.raidProgressListData.observe(viewLifecycleOwner) {
            raidProgressListController.setData(it)
        }
    }

    private fun setupRecyclerViewForCurrentOrientation() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewBinding.dataListRecyclerview.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        } else { // coil preloader can be used only with linear layout manager
            viewBinding.dataListRecyclerview.addCoilPreloader(
                requireContext(),
                preloader = raidProgressItemModelPreloader
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        raidProgressListController.onSaveInstanceState(outState)
    }
}
