package com.patrick0422.mealgo.ui.school

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.patrick0422.mealgo.R
import com.patrick0422.mealgo.base.BaseFragment
import com.patrick0422.mealgo.databinding.FragmentSchoolBinding
import com.patrick0422.mealgo.ui.MainActivity
import com.patrick0422.mealgo.util.Constants.Companion.TAG
import com.patrick0422.mealgo.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SchoolFragment : BaseFragment<FragmentSchoolBinding>(R.layout.fragment_school) {
    private val schoolViewModel: SchoolViewModel by viewModels()
    private val schoolListAdapter by lazy { SchoolListAdapter() }

    @OptIn(FlowPreview::class)
    override fun init() {
        binding.schoolListView.adapter = schoolListAdapter

        val searchQuery = MutableStateFlow("")

        binding.editSchool.doAfterTextChanged { text ->
            searchQuery.value = text.toString()
        }

        lifecycleScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged() // 중복 제거
                .collect { keyword ->
                    Log.d(TAG, "init: $keyword")
                    schoolViewModel.getSchoolList(keyword)
                }
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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