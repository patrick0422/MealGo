package com.example.mealgo.ui.profile

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.mealgo.R
import com.example.mealgo.base.BaseFragment
import com.example.mealgo.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun init() {
        profileViewModel.getSchool()
        profileViewModel.school.observe(viewLifecycleOwner, { school ->
            binding.textSchoolName.text = school.schoolName
            binding.textSchoolLocation.text = school.schoolLocation
        })

        binding.textEdit.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_schoolFragment)
        }
    }
}