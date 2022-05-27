package com.patrick0422.mealgo.ui.meal

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.patrick0422.mealgo.R
import com.patrick0422.mealgo.base.BaseFragment
import com.patrick0422.mealgo.databinding.FragmentMealBinding
import com.patrick0422.mealgo.util.Constants.Companion.PREFERENCES_NO_INFO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealFragment : BaseFragment<FragmentMealBinding>(R.layout.fragment_meal) {
    private val mealViewModel: MealViewModel by viewModels()

    override fun init() {
        binding.viewModel = mealViewModel

        isLoading(true)
        mealViewModel.getSchool()

        setObserver()
    }

    private fun setObserver() {
        mealViewModel.school.observe(viewLifecycleOwner) { school ->
            if (school.schoolCode == PREFERENCES_NO_INFO) {
                makeToast("학교 정보를 먼저 설정해주세요")
                findNavController().navigate(R.id.action_mealFragment_to_profileFragment)
            }
            else {
                if(mealViewModel.mealList.value == null)
                    mealViewModel.getMeal()
            }
        }
        mealViewModel.mealDate.observe(viewLifecycleOwner) {
            binding.textDate.text = mealViewModel.mealDateFormatted
        }
        mealViewModel.mealList.observe(viewLifecycleOwner) { mealList ->
            clearMeal()
            setMealData(mealList)
            isLoading(false)
        }
    }

    private fun clearMeal() = with(binding) {
        textBreakfast.text = ""
        textLunch.text = ""
        textDinner.text = ""
    }

    private fun setMealData(mealList: List<String>) {
        with(binding) {
            textBreakfast.text = mealList[0]
            textLunch.text = mealList[1]
            textDinner.text = mealList[2]
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