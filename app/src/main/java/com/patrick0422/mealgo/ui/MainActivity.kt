package com.patrick0422.mealgo.ui

import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.patrick0422.mealgo.R
import com.patrick0422.mealgo.base.BaseActivity
import com.patrick0422.mealgo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var navController: NavController

    override fun init() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        setUpBottomNav()
    }

    private fun setUpBottomNav() {
        supportActionBar?.hide()
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mealFragment,
                R.id.profileFragment,
            )
        )
        binding.bottomNav.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onNavigateUp(): Boolean = navController.navigateUp() || super.onNavigateUp()

    override fun onBackPressed() {
        if (!navController.navigateUp())
            super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            navController.navigateUp()
        return true
    }
}