package com.nemesis.rio.presentation.server.realm

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import com.airbnb.epoxy.stickyheader.StickyHeaderLinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nemesis.rio.domain.server.realm.Realm
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.view.fragment.BaseOptionSelectDialogFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RealmSelectDialogFragment : BaseOptionSelectDialogFragment() {
    private val realmListKey = "realm_list"
    private val realmListDataController: RealmSelectDataController by inject { parametersOf(::onRealmSelected) }

    @Suppress("UNCHECKED_CAST")
    private val viewModel: RealmSelectViewModel by viewModel {
        val realmList = requireArguments().get(realmListKey) as List<Realm>
        parametersOf(realmList)
    }

    companion object {
        const val REQUEST_KEY = "realm_select"
        const val SELECTED_REALM_KEY = "selected_realm"

        fun create(realmList: List<Realm>, selectedRealm: Realm) =
            RealmSelectDialogFragment().apply {
                arguments = bundleOf(realmListKey to realmList, SELECTED_REALM_KEY to selectedRealm)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()
        setupRecyclerView()
        observeRealmListData()
    }

    private fun setupToolbar() {
        with(viewBinding.optionSelectToolbar) {
            inflateMenu(R.menu.fragment_realm_select_toolbar_menu)
            setTitle(R.string.realm_list_title)
            setupRealmFilter(menu)
        }
    }

    private fun setupRealmFilter(realmSelectMenu: Menu) {
        val realmFilterView = realmSelectMenu.findItem(R.id.realm_search).actionView as SearchView
        setSearchViewFullWidth(realmFilterView)
        setRealmNameChangeListener(realmFilterView)
    }

    private fun setSearchViewFullWidth(realmFilterView: SearchView) {
        realmFilterView.maxWidth = Integer.MAX_VALUE
    }

    private fun setRealmNameChangeListener(realmFilterView: SearchView) {
        realmFilterView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let(viewModel::filterRealmList)
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        with(viewBinding.optionSelectRecyclerview) {
            layoutManager = StickyHeaderLinearLayoutManager(requireContext())
            setController(realmListDataController)
        }
    }

    private fun observeRealmListData() {
        val selectedRealm = requireArguments().getString(SELECTED_REALM_KEY)
        viewModel.realmSelectData.observe(this) {
            realmListDataController.setData(it, selectedRealm)
        }
    }

    private fun onRealmSelected(realm: Realm) {
        parentFragmentManager.setFragmentResult(
            REQUEST_KEY, bundleOf(SELECTED_REALM_KEY to realm)
        )
        dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        setFullscreenBottomSheetDialog(bottomSheetDialog)
        return bottomSheetDialog
    }

    private fun setFullscreenBottomSheetDialog(bottomSheetDialog: BottomSheetDialog) {
        bottomSheetDialog.setOnShowListener { d: DialogInterface ->
            val bottomSheet =
                (d as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                height = ViewGroup.MarginLayoutParams.MATCH_PARENT
            }

            val bottomSheetBehavior: BottomSheetBehavior<FrameLayout> =
                BottomSheetBehavior.from(bottomSheet!!)

            bottomSheetBehavior.isFitToContents = false
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        }
    }
}
