package com.example.mealgo.ui.profile

import android.util.Log
import androidx.fragment.app.viewModels
import com.example.mealgo.R
import com.example.mealgo.base.BaseFragment
import com.example.mealgo.databinding.FragmentProfileBinding
import com.example.mealgo.util.Constants.Companion.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun init() {
        profileViewModel.getSchool()
        profileViewModel.school.observe(viewLifecycleOwner, { school ->
            Log.d(TAG, "init: $school")
        })

        binding.textEdit.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_schoolFragment)
        }
    }
}