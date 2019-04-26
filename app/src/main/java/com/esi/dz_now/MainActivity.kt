package com.esi.dz_now

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.esi.dz_now.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val multiStartNavigationUi = MultiStartNavigationUI(
        listOf(
            R.id.homeFragment,
            R.id.favoriteFragment,
            R.id.settingsFragment
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        navController = this.findNavController(R.id.nav_host_fragment)
        // setup NavController with actionBar & Drawer
        multiStartNavigationUi.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)


    }


    override fun onBackPressed() {
        if (!multiStartNavigationUi.onBackPressed(this, navController)) {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp() = multiStartNavigationUi.navigateUp(binding.drawerLayout, navController)


}
