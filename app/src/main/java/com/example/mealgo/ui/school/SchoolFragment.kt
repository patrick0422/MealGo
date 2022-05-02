package com.example.mealgo.ui.school

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.mealgo.R
import com.example.mealgo.base.BaseFragment
import com.example.mealgo.databinding.FragmentSchoolBinding
import com.example.mealgo.ui.MainActivity
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity?)?.apply {
            supportActionBar?.show()
            binding.bottomNav.visibility = View.GONE
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStop() {
        (activity as MainActivity?)?.apply {
            supportActionBar?.hide()
            binding.bottomNav.visibility = View.VISIBLE
        }
        super.onStop()
    }


}