package com.nemesis.rio.presentation.launcher

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val launcherModule = module {
    viewModel<LauncherViewModel>()
}
