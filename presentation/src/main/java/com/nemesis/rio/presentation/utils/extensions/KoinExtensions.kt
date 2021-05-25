package com.nemesis.rio.presentation.utils.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.koin.androidx.scope.ScopeHandlerViewModel
import org.koin.androidx.scope.createScope
import org.koin.core.scope.Scope

fun Fragment.getFragmentRetainedScope(): Scope {
    val scopeViewModel = viewModels<ScopeHandlerViewModel>().value
    if (scopeViewModel.scope == null) {
        scopeViewModel.scope = createScope()
    }
    return scopeViewModel.scope!!
}

fun Fragment.fragmentRetainedScope(): Lazy<Scope> = lazy { getFragmentRetainedScope() }
