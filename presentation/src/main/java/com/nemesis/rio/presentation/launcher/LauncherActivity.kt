package com.nemesis.rio.presentation.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.databinding.ActivityLauncherBinding
import com.nemesis.rio.presentation.main.MainActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
        lifecycleScope.launch {
            viewModel.navigateToMainScreenEvent
                .flowWithLifecycle(lifecycle)
                .collect { navigateToMainScreen() }
        }
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
