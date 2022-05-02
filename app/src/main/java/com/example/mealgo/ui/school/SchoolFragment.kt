package com.example.mealgo.ui.school

import android.content.Context
import android.util.Log
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mealgo.R
import com.example.mealgo.databinding.FragmentSchoolBinding
import com.example.mealgo.base.BaseFragment
import com.example.mealgo.util.Constants.Companion.TAG
import com.example.mealgo.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolFragment: BaseFragment<FragmentSchoolBinding>(R.layout.fragment_school) {
    private val schoolViewModel: SchoolViewModel by viewModels()
    private val schoolListAdapter by lazy { SchoolListAdapter() }

    override fun init() {
        binding.schoolListView.adapter = schoolListAdapter

        binding.editSchool.doAfterTextChanged { text ->
            schoolViewModel.getSchoolList(text.toString())
        }

        schoolViewModel.schoolList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    Log.d(TAG, "schoolList: ${response.data}")
                    schoolListAdapter.setData(response.data!!)
                }
                is NetworkResult.Error -> {
                    Log.d(TAG, "schoolList: ${response.message}")
                }
                is NetworkResult.Loading -> {
                    Log.d(TAG, "schoolList: Loading")
                }
            }
        }
    }
}