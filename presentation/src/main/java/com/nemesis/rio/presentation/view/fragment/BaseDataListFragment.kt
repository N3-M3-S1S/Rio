package com.nemesis.rio.presentation.view.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.databinding.FragmentDataListBinding

abstract class BaseDataListFragment : Fragment(R.layout.fragment_data_list) {
    protected val recycledViewPool = RecyclerView.RecycledViewPool()
    protected val viewBinding by viewBinding<FragmentDataListBinding>()
    protected open val isDataListLoading: LiveData<Boolean>? = null

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            isDataLoading = isDataListLoading
            lifecycleOwner = viewLifecycleOwner
            dataListRecyclerview.setRecycledViewPool(recycledViewPool)
        }
    }
}
