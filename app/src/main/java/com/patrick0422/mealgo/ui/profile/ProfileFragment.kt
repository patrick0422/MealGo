package com.patrick0422.mealgo.ui.profile

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.patrick0422.mealgo.R
import com.patrick0422.mealgo.base.BaseFragment
import com.patrick0422.mealgo.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun init() {
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.hide()

        profileViewModel.getSchool()
        profileViewModel.school.observe(viewLifecycleOwner) { school ->
            binding.textSchoolName.text = school.schoolName
            binding.textSchoolLocation.text = school.schoolLocation
        }

        binding.textEdit.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_schoolFragment)
        }
    }
}