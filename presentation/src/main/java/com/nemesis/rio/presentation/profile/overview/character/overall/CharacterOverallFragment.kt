package com.nemesis.rio.presentation.profile.overview.character.overall

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.databinding.FragmentCharacterOverallBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CharacterOverallFragment : Fragment(R.layout.fragment_character_overall) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val characterOverallViewModel: CharacterOverallViewModel =
            requireParentFragment().getViewModel()

        FragmentCharacterOverallBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = characterOverallViewModel
        }
    }
}
