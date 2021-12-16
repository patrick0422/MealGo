package com.example.mealgo.ui.meal

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.mealgo.R
import com.example.mealgo.base.BaseFragment
import com.example.mealgo.databinding.FragmentMealBinding
import com.example.mealgo.util.Constants.Companion.TAG
import com.example.mealgo.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class MealFragment : BaseFragment<FragmentMealBinding>(R.layout.fragment_meal) {
    private val mealViewModel: MealViewModel by viewModels()
    private val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))

    private val mealList: List<String> = listOf()

    override fun init() {
        binding.viewModel = mealViewModel

        val activity = activity as AppCompatActivity
        activity.supportActionBar?.show()

        mealViewModel.getSchool()

        setObserver()
    }

    private fun setObserver() {
        mealViewModel.school.observe(viewLifecycleOwner, {
            mealViewModel.getMealList(date)
        })

        mealViewModel.mealList.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    setMealData(response.data!!)
                }
                is NetworkResult.Error -> {
                    Log.d(TAG, "init: ${response.message}")
                }
                is NetworkResult.Loading -> {

                }
            }
        })
    }

    private fun setMealData(mealList: List<String?>) {
        with(binding) {
            textBreakfast.text = mealList[0] ?: "정보 없음"
            textLunch.text = mealList[1] ?: "정보 없음"
            textDinner.text = mealList[2] ?: "정보 없음"
        }
    }
}