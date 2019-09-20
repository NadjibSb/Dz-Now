package com.esi.dz_now.screens.settings


import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.esi.dz_now.R
import com.esi.dz_now.data.Categories
import com.esi.dz_now.databinding.FragmentSettingsBinding
import com.esi.dz_now.screens.*
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var categories: List<Categories>
    private val TAG = "TAG-SettingsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_settings, container, false
        )
        (activity as MainActivity).supportActionBar?.title =
            getString(R.string.settings_fragment_title)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categories = getAllCategories()

        spinnerSetup()
        categorieSelectionSetup(categories)
        setupTheme()
    }


    //Setup the Language spinner
    private fun spinnerSetup() {
        val spinner: Spinner = binding.languageSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            context,
            R.array.languages,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.setSelection(
            when (Locale.getDefault().language) {
                "en" -> 0
                "fr" -> 1
                "ar" -> 2
                else -> 0
            }
        )
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (id) {
                    0L -> switchLanguage("en")
                    1L -> switchLanguage("fr")
                    2L -> switchLanguage("ar")
                }
            }
        }
    }

    private fun switchLanguage(newlanguage: String) {
        //Save the new language
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPref.edit().putString(KEY_CURRENT_LANGUAGE, newlanguage).apply()

        //change the language
        var currentLanguage = Locale.getDefault().language
        if (!currentLanguage.equals(newlanguage)) {
            var locale = Locale(newlanguage)
            Locale.setDefault(locale)
            var res = this@SettingsFragment.activity!!.resources
            var config = res.configuration
            config.setLocale(locale)
            res.updateConfiguration(config, res.displayMetrics)
            activity?.recreate()
        }
    }

    private fun setupTheme() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val currentTheme = sharedPref.getString(KEY_CURRENT_THEME, LIGHT_THEME)

        binding.themeSwitch.isChecked = currentTheme == DARK_THEME


        binding.themeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            val oldTheme = sharedPref.getString(KEY_CURRENT_THEME, "first")
            Log.i(TAG, oldTheme)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPref.edit().putString(KEY_CURRENT_THEME, DARK_THEME).apply()
                Toast.makeText(context, "Dark theme", Toast.LENGTH_SHORT).show()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPref.edit().putString(KEY_CURRENT_THEME, LIGHT_THEME).apply()
            }
            val newTheme = sharedPref.getString(KEY_CURRENT_THEME, "first")
            if (oldTheme != newTheme)
                activity?.recreate()

        }
    }

    private fun categorieSelectionSetup(categories: List<Categories>) {
        var culture_: Categories = categories[0]
        var health_: Categories = categories[0]
        var sport_: Categories = categories[0]
        var international_: Categories = categories[0]
        var politics_: Categories = categories[0]
        var tech_: Categories = categories[0]
        var msg = ""
        for (category_ in categories) {
            if (category_.name == Categories.CULTURE.name) culture_ = category_
            if (category_.name == Categories.SPORT.name) sport_ = category_
            if (category_.name == Categories.TECH.name) tech_ = category_
            if (category_.name == Categories.POLITICS.name) politics_ = category_
            if (category_.name == Categories.INTERNATIONAL.name) international_ = category_
            if (category_.name == Categories.HEALTH.name) health_ = category_

        }
        checkBoxCulture.isChecked = culture_.isActivated
        checkBoxCulture.setOnCheckedChangeListener { view, isChecked ->
            culture_.isActivated = isChecked
            msg =
                getString(R.string.culture_category) + " " + (if (isChecked) getString(R.string.enabled) else getString(
                    R.string.disabled
                ))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxSanté.isChecked = health_.isActivated
        checkBoxSanté.setOnCheckedChangeListener { view, isChecked ->
            health_.isActivated = isChecked
            msg =
                getString(R.string.health_category) + " " + (if (isChecked) getString(R.string.enabled) else getString(
                    R.string.disabled
                ))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxInternational.isChecked = international_.isActivated
        checkBoxInternational.setOnCheckedChangeListener { view, isChecked ->
            international_.isActivated = isChecked
            msg =
                getString(R.string.international_category) + " " + (if (isChecked) getString(R.string.enabled) else getString(
                    R.string.disabled
                ))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxPolitique.isChecked = politics_.isActivated
        checkBoxPolitique.setOnCheckedChangeListener { view, isChecked ->
            politics_.isActivated = isChecked
            msg =
                getString(R.string.politic_category) + " " + (if (isChecked) getString(R.string.enabled) else getString(
                    R.string.disabled
                ))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxSport.isChecked = sport_.isActivated
        checkBoxSport.setOnCheckedChangeListener { view, isChecked ->
            sport_.isActivated = isChecked
            msg =
                getString(R.string.sport_category) + " " + (if (isChecked) getString(R.string.enabled) else getString(
                    R.string.disabled
                ))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxTechnologie.isChecked = tech_.isActivated
        checkBoxTechnologie.setOnCheckedChangeListener { view, isChecked ->
            tech_.isActivated = isChecked
            msg =
                getString(R.string.tech_category) + " " + (if (isChecked) getString(R.string.enabled) else getString(
                    R.string.disabled
                ))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

    }

    private fun getAllCategories(): List<Categories> {
        var list = mutableListOf<Categories>()

        list.addAll(Categories.values().toList())
        list.sortBy { categories ->
            categories.title
        }
        return list
    }
}
