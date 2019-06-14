package com.esi.dz_now.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.esi.dz_now.R
import com.esi.dz_now.data.Article
import com.esi.dz_now.data.Categories
import com.esi.dz_now.data.DataUtil
import com.esi.dz_now.data.SharedData
import com.esi.dz_now.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), SharedData {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val multiStartNavigationUi = MultiStartNavigationUI(
        listOf(
            R.id.homeFragment,
            R.id.favoriteFragment,
            R.id.settingsFragment
        )
    )
    private var dataUtil = DataUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

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


    override fun getAllArticles(): MutableList<Article> {
        return dataUtil.getAllArticles()
    }

    override fun getArticlesListByCategorie(categories: Categories): MutableList<Article> {
        return dataUtil.getArticlesListByCategorie(categories)!!
    }


    override fun getAllCategories(): List<Categories> {
        return dataUtil.getAllCategories()
    }

    override fun getCategories(): List<Categories> {
        return dataUtil.getCategories()
    }

    override fun getArticleById(articleId: Int): Article {
        return dataUtil.getArticleById(articleId)
    }

    override fun getFavories(): MutableList<Article> {
        return dataUtil.getFavories()
    }
}
