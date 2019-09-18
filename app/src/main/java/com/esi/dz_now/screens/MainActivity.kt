package com.esi.dz_now.screens

import android.os.Bundle
import android.preference.PreferenceManager
import android.speech.tts.TextToSpeech
import android.util.Log
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import java.util.*


const val KEY_CURRENT_THEME = "Theme"
const val LIGHT_THEME = "light"
const val DARK_THEME = "dark"


const val KEY_CURRENT_LANGUAGE = "Language"
const val FR = "fr"
const val EN = "en"
const val AR = "ar"

class MainActivity : AppCompatActivity(), SharedData, TextToSpeech.OnInitListener {

    private val TAG = "TAG-MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val multiStartNavigationUi = MultiStartNavigationUI(
        listOf(
            R.id.homeFragment,
            R.id.favoriteFragment,
            R.id.settingsFragment
        )
    )
    private var tts: TextToSpeech? = null
    private var dataUtil = DataUtil()

    override fun onCreate(savedInstanceState: Bundle?) {


        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this.applicationContext)
        //check the App Theme
        val currentTheme = sharedPref.getString(KEY_CURRENT_THEME, LIGHT_THEME)
        if (currentTheme == DARK_THEME) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        //check the App language
        val currentLanguage = sharedPref.getString(KEY_CURRENT_LANGUAGE, EN)
        if (currentLanguage != EN) switchLanguage(currentLanguage)
        super.onCreate(savedInstanceState)



        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        navController = this.findNavController(R.id.nav_host_fragment)
        // setup NavController with actionBar & Drawer
        multiStartNavigationUi.setupActionBarWithNavController(
            this,
            navController,
            binding.drawerLayout
        )
        NavigationUI.setupWithNavController(binding.navView, navController)

        tts = TextToSpeech(this, this)



    }


    override fun onBackPressed() {
        if (!multiStartNavigationUi.onBackPressed(this, navController)) {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp() =
        multiStartNavigationUi.navigateUp(binding.drawerLayout, navController)


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

    private fun switchLanguage(newlanguage: String) {
        var currentLanguage = Locale.getDefault().language
        if (currentLanguage != newlanguage) {
            var locale = Locale(newlanguage)
            Locale.setDefault(locale)
            var config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.FRANCE)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    fun speakOut(text: String) {

        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    fun stopTts() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
        }
    }

}
