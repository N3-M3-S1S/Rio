package com.nemesis.rio.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.nemesis.rio.domain.profile.Profile
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.app.browser.UrlBrowser
import com.nemesis.rio.presentation.app.messages.Message
import com.nemesis.rio.presentation.databinding.ActivityMainBinding
import com.nemesis.rio.presentation.profile.overview.overviewDirection
import kotlinx.coroutines.flow.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import splitties.alertdialog.appcompat.alertDialog
import splitties.alertdialog.appcompat.neutralButton
import splitties.alertdialog.appcompat.okButton
import splitties.resources.str
import splitties.snackbar.action
import splitties.toast.toast

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val viewModel by viewModel<MainActivityViewModel>()
    private val viewBinding by viewBinding<ActivityMainBinding>(CreateMethod.INFLATE)
    private val urlBrowser: UrlBrowser by inject()
    private val navigateToProfileOverviewEventFlow: Flow<Profile> by inject(
        navigateToProfileOverviewEventFlowQualifier
    )
    private val openUrlInBrowserEventFlow: Flow<String> by inject(openUrlInBrowserEventFlowQualifier)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        setupNavController()
        setupToolbar()
        observeNavigateToProfileOverviewEventFlow()
        observeOpenUrlInBrowserEventFlow()
        observeMessageEventFlow()
    }

    private fun setupToolbar() {
        setSupportActionBar(viewBinding.activityMainToolbar)
        setupActionBarWithNavController(navController)
    }

    override fun setTitle(title: CharSequence?) {
        viewBinding.activityMainToolbar.title = title
    }

    override fun setTitle(titleId: Int) {
        val title = str(titleId)
        setTitle(title)
    }

    private fun setupNavController() {
        navController =
            (supportFragmentManager.findFragmentById(R.id.activity_main_nav_host_fragment) as NavHostFragment).navController
    }

    private fun observeNavigateToProfileOverviewEventFlow() {
        lifecycleScope.launchWhenStarted {
            navigateToProfileOverviewEventFlow
                .map { it.overviewDirection }
                .collect(navController::navigate)
        }
    }

    private fun observeOpenUrlInBrowserEventFlow() {
        openUrlInBrowserEventFlow.onEach { urlBrowser.openUrl(this, it) }.launchIn(lifecycleScope)
    }

    private fun observeMessageEventFlow() {
        viewModel.messages.onEach(::showMessage).launchIn(lifecycleScope)
    }

    private fun showMessage(message: Message) {
        when (message) {
            is Message.Short -> showShortMessage(message)
            is Message.Long -> showLongMessage(message)
        }
    }

    private fun showShortMessage(shortMessage: Message.Short) {
        showSnackbar(shortMessage.text)
    }

    private fun showLongMessage(longMessage: Message.Long) {
        showSnackbar(longMessage.shortText) {
            action(R.string.message_long_more) { showLongMessageDialog(longMessage) }
        }
    }

    private fun showLongMessageDialog(longMessage: Message.Long) = with(longMessage) {
        alertDialog(shortText, fullText) {
            okButton()
            neutralButton(R.string.message_copy) {
                viewModel.copyToClipboard(fullText)
                toast(R.string.message_copied)
            }
        }.show()
    }

    private inline fun showSnackbar(text: String, snackbarSetup: Snackbar.() -> Unit = {}) {
        Snackbar.make(viewBinding.root, text, Snackbar.LENGTH_SHORT)
            .apply {
                snackbarSetup()
                setSnackbarAnchorView(this)
            }
            .show()
    }

    private fun setSnackbarAnchorView(snackbar: Snackbar) {
        val currentDestinationId = navController.currentDestination!!.id
        val isCurrentDestinationProfileOverview =
            currentDestinationId == R.id.characterOverviewFragment || currentDestinationId == R.id.guildOverviewFragment
        if (isCurrentDestinationProfileOverview) {
            snackbar.anchorView =
                viewBinding.activityMainNavHostFragment.findViewById(R.id.profile_overview_bottom_navigation)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
