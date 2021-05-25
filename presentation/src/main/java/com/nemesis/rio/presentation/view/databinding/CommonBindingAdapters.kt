package com.nemesis.rio.presentation.view.databinding

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("swipeRefresh_onRefresh")
fun SwipeRefreshLayout.onRefresh(block: () -> Unit) {
    setOnRefreshListener { block() }
}

@BindingAdapter("swipeRefresh_isRefreshing")
fun SwipeRefreshLayout.isRefreshing(isRefreshing: Boolean) {
    setRefreshing(isRefreshing)
}
