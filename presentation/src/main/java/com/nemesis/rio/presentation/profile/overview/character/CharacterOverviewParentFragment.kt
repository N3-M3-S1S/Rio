package com.nemesis.rio.presentation.profile.overview.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.nemesis.rio.domain.profile.Character
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.profile.character.attributes.themeResId
import com.nemesis.rio.presentation.profile.overview.ProfileOverviewParentFragment

class CharacterOverviewParentFragment : ProfileOverviewParentFragment<Character>(), NavController.OnDestinationChangedListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        findNavController().addOnDestinationChangedListener(this)
        setCharacterClassThemeToActivity()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun setCharacterClassThemeToActivity() {
        val characterClassThemeResId =
            getProfileFromArguments(requireArguments()).attributes.characterClass.themeResId
        requireActivity().setTheme(characterClassThemeResId)
    }

    override fun getProfileFromArguments(arguments: Bundle): Character =
        CharacterOverviewParentFragmentArgs.fromBundle(arguments).characterParcel.character

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        if (destination.id != R.id.characterOverviewFragment) {
            setAppThemeToActivity()
            findNavController().removeOnDestinationChangedListener(this)
        }
    }

    private fun setAppThemeToActivity() {
        requireActivity().setTheme(R.style.AppTheme)
    }
}
