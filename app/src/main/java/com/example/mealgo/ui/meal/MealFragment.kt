package com.example.mealgo.ui.meal

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mealgo.R
import com.example.mealgo.base.BaseFragment
import com.example.mealgo.databinding.FragmentMealBinding
import com.example.mealgo.util.Constants.Companion.PREFERENCES_NO_INFO
import com.example.mealgo.util.Constants.Companion.TAG
import com.example.mealgo.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MealFragment : BaseFragment<FragmentMealBinding>(R.layout.fragment_meal) {
    private val mealViewModel: MealViewModel by viewModels()

    override fun init() {
        binding.viewModel = mealViewModel

        val activity = activity as AppCompatActivity
        activity.supportActionBar?.show()

        isLoading(true)
        mealViewModel.getSchool()

        setObserver()
    }

    private fun setObserver() {
        mealViewModel.school.observe(viewLifecycleOwner, { school ->
            if (school.schoolCode == PREFERENCES_NO_INFO) {
                makeToast("학교 정보를 먼저 설정해주세요")
                findNavController().navigate(R.id.action_mealFragment_to_profileFragment)
            }
            else {
                mealViewModel.getMeal()
            }
        })

        mealViewModel.mealList.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    clearMeal()

                    setMealData(response.data!!)
                    isLoading(false)
                }
                is NetworkResult.Error -> {
                    isLoading(false)
                    Log.d(TAG, "init: ${response.message}")
                }
                is NetworkResult.Loading -> {
                    isLoading(true)
                }
            }
        })
    }

    private fun clearMeal() = with(binding) {
        textBreakfast.text = ""
        textLunch.text = ""
        textDinner.text = ""
    }

    private fun setMealData(mealList: List<String?>) {
        with(binding) {
            when(mealList.size) {
                1 -> {
                    textBreakfast.text = PREFERENCES_NO_INFO
                    textLunch.text = mealList[0]
                    textDinner.text = PREFERENCES_NO_INFO
                }
                2 -> {
                    textBreakfast.text = mealList[0]
                    textLunch.text = mealList[1]
                    textDinner.text = PREFERENCES_NO_INFO
                }
                3 -> {
                    textBreakfast.text = mealList[0]
                    textLunch.text = mealList[1]
                    textDinner.text = mealList[2]
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        with(binding) {
            if (loading) {
                cardBreakfast.visibility = View.INVISIBLE
                cardLunch.visibility = View.INVISIBLE
                cardDinner.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            } else {
                cardBreakfast.visibility = View.VISIBLE
                cardLunch.visibility = View.VISIBLE
                cardDinner.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }
}