package com.nemesis.rio.presentation.launcher

import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

val launcherModule = module {
    viewModel<LauncherViewModel>()
}
