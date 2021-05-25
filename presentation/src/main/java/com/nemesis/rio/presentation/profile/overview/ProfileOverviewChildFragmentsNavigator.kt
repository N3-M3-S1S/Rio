package com.nemesis.rio.presentation.profile.overview

import android.util.SparseArray
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commitNow
import com.nemesis.rio.presentation.R
import kotlin.reflect.KClass

class ProfileOverviewChildFragmentsNavigator(
    val bottomNavigationMenuId: Int,
    private val bottomNavigationMenuOptionIdToFragmentClass: SparseArray<KClass<out Fragment>>
) {
    private lateinit var fragmentManager: FragmentManager
    private val childFragmentContainerIdNotInitialized = -1
    private var childFragmentContainerId = childFragmentContainerIdNotInitialized

    fun setup(fragmentManager: FragmentManager, childFragmentContainerId: Int) {
        this.fragmentManager = fragmentManager
        this.childFragmentContainerId = childFragmentContainerId
    }

    fun showChildFragmentForBottomNavigationMenuOptionId(@IdRes navigationMenuOptionID: Int) {
        checkIfSetupWasDone()

        val fragmentClass = getFragmentClassForNavigationMenuOptionId(navigationMenuOptionID)
        val fragmentTag = fragmentClass.simpleName!!
        val fragmentForTag = fragmentManager.findFragmentByTag(fragmentTag)

        fragmentManager.commitNow {
            setCustomAnimations(
                R.anim.nav_default_enter_anim,
                R.anim.nav_default_exit_anim,
                R.anim.nav_default_pop_enter_anim,
                R.anim.nav_default_pop_exit_anim
            )
            hideFragmentsExceptActive(fragmentTag)
            if (fragmentForTag == null) {
                add(
                    childFragmentContainerId,
                    fragmentClass.java,
                    null,
                    fragmentTag
                )
            } else {
                show(fragmentForTag)
            }
        }
    }

    private fun checkIfSetupWasDone() {
        check(::fragmentManager.isInitialized) { "Fragment manager is not set" }
        check(childFragmentContainerId != childFragmentContainerIdNotInitialized) { "Child fragment container id is not set" }
    }

    private fun getFragmentClassForNavigationMenuOptionId(@IdRes navigationMenuOptionID: Int) =
        bottomNavigationMenuOptionIdToFragmentClass.get(navigationMenuOptionID)
            ?: error("No fragment class for menu item id: $$navigationMenuOptionID")

    private fun FragmentTransaction.hideFragmentsExceptActive(activeFragmentTag: String) {
        fragmentManager.fragments.forEach {
            if (it.tag != activeFragmentTag && it.isVisible) {
                hide(it)
            }
        }
    }
}
