package com.example.mealgo.ui.meal

import androidx.appcompat.app.AppCompatActivity
import com.example.mealgo.R
import com.example.mealgo.base.BaseFragment
import com.example.mealgo.databinding.FragmentMealBinding


class MealFragment : BaseFragment<FragmentMealBinding>(R.layout.fragment_meal) {


    override fun init() {
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.show()
    }
}