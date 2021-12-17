package com.example.mealgo.ui

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mealgo.R
import com.example.mealgo.databinding.ActivityMainBinding
import com.example.mealgo.base.BaseActivity
import com.example.mealgo.util.Constants.Companion.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var navController: NavController

    override fun init() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        with (navController) {
            when (item.itemId) {
                android.R.id.home -> {
                    popBackStack()
                }
                R.id.userProfile -> {
                    if (currentDestination?.id == R.id.schoolFragment)
                        popBackStack()
                    else
                        navigate(R.id.action_mealFragment_to_profileFragment)
                }
                else -> {

                }
            }
        }
        
        return true
    }

    override fun onBackPressed() {
        if (navController.backQueue.isEmpty())
            super.onBackPressed()
        else
            navController.popBackStack()
    }
}