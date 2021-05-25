package com.nemesis.rio.presentation.sorting

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.nemesis.rio.domain.sorting.SortingOrder
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.itemOption
import com.nemesis.rio.presentation.utils.extensions.getEnum
import com.nemesis.rio.presentation.view.fragment.BaseOptionSelectDialogFragment

class SortingOrderSelectDialogFragment : BaseOptionSelectDialogFragment() {

    companion object {
        const val REQUEST_KEY = "sorting_order"
        const val SELECTED_SORTING_ORDER_KEY = "selected_sorting_order"

        fun create(selectedSortingOrder: SortingOrder) = SortingOrderSelectDialogFragment().apply {
            arguments = bundleOf(SELECTED_SORTING_ORDER_KEY to selectedSortingOrder)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.optionSelectToolbar.setTitle(R.string.sort_order_title)
        setupSortingOrderList()
    }

    private fun setupSortingOrderList() {
        viewBinding.optionSelectRecyclerview.withModels {
            val sortingOrders = enumValues<SortingOrder>()
            val selectedSortingOrder = requireArguments().getEnum<SortingOrder>(
                SELECTED_SORTING_ORDER_KEY
            )
            sortingOrders.forEach { sortingOrder ->
                itemOption {
                    id(sortingOrder.hashCode())
                    stringResId(sortingOrder.stringResId)
                    isSelected(sortingOrder == selectedSortingOrder)
                    onClick { onSortingOrderSelected(sortingOrder) }
                }
            }
        }
    }

    private fun onSortingOrderSelected(sortingOrder: SortingOrder) {
        parentFragmentManager.setFragmentResult(
            REQUEST_KEY, bundleOf(SELECTED_SORTING_ORDER_KEY to sortingOrder)
        )
        dismiss()
    }
}
