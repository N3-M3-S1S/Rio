package com.nemesis.rio.presentation.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.databinding.ActivityLauncherBinding
import com.nemesis.rio.presentation.main.MainActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class LauncherActivity : AppCompatActivity(R.layout.activity_launcher) {
    private val viewBinding by viewBinding<ActivityLauncherBinding>()
    private val viewModel by viewModel<LauncherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewBinding()
        observeNavigateToMainScreenEvent()
    }

    private fun setupViewBinding() {
        with(viewBinding) {
            lifecycleOwner = this@LauncherActivity
            applicationInitializing = viewModel.applicationInitializing
        }
    }

    private fun observeNavigateToMainScreenEvent() {
        viewModel.navigateToMainScreenEvent
            .onEach { navigateToMainScreen() }.launchIn(lifecycleScope)
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
