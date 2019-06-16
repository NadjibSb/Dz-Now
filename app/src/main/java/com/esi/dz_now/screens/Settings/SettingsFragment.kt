package com.esi.dz_now.screens.Settings


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
import com.esi.dz_now.data.SharedData
import com.esi.dz_now.databinding.FragmentSettingsBinding
import com.esi.dz_now.screens.MainActivity
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*


const val KEY_CURRENT_THEME = "current_theme"
const val LIGHT_THEME = "light"
const val DARK_THEME = "dark"


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var data: SharedData
    private lateinit var categories: List<Categories>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_settings, container, false
        )
        (activity as MainActivity).supportActionBar?.title = getString(R.string.settings_fragment_title)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = activity as SharedData
        categories = data.getAllCategories()

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

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (id) {
                    0L -> switchLanguage("en")
                    1L -> switchLanguage("fr")
                    2L -> switchLanguage("ar")
                }
            }
        }
    }

    private fun switchLanguage(newlanguage: String) {
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
            Log.i("nadjib", oldTheme)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPref.edit().putString(KEY_CURRENT_THEME, DARK_THEME).apply()
                Toast.makeText(context, "Dark theme", Toast.LENGTH_SHORT).show()
                //activity?.recreate()
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPref.edit().putString(KEY_CURRENT_THEME, LIGHT_THEME).apply()
                //activity?.recreate()
            }
            val newTheme = sharedPref.getString(KEY_CURRENT_THEME, "first")
            if (!oldTheme.equals(newTheme))
                activity?.recreate()

        }
    }

    private fun categorieSelectionSetup(categories: List<Categories>) {


        checkBoxCulture.isChecked = categories[0].isActivated
        checkBoxCulture.setOnCheckedChangeListener { view, isChecked ->
            categories[0].isActivated = isChecked
            val msg = "Catégorie Culture " + (if (isChecked) "activée" else "désactivée") + "."
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxSanté.isChecked = categories[1].isActivated
        checkBoxSanté.setOnCheckedChangeListener { view, isChecked ->
            categories[1].isActivated = isChecked
            val msg = "Catégorie Santé " + (if (isChecked) "activée" else "désactivée") + "."
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxInternational.isChecked = categories[2].isActivated
        checkBoxInternational.setOnCheckedChangeListener { view, isChecked ->
            categories[2].isActivated = isChecked
            val msg = "Catégorie International " + (if (isChecked) "activée" else "désactivée") + "."
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxPolitique.isChecked = categories[3].isActivated
        checkBoxPolitique.setOnCheckedChangeListener { view, isChecked ->
            categories[3].isActivated = isChecked
            val msg = "Catégorie Politique " + (if (isChecked) "activée" else "désactivée") + "."
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxSport.isChecked = categories[4].isActivated
        checkBoxSport.setOnCheckedChangeListener { view, isChecked ->
            categories[4].isActivated = isChecked
            val msg = "Catégorie Sport " + (if (isChecked) "activée" else "désactivée") + "."
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxTechnologie.isChecked = categories[5].isActivated
        checkBoxTechnologie.setOnCheckedChangeListener { view, isChecked ->
            categories[5].isActivated = isChecked
            val msg = "Catégorie Technologie " + (if (isChecked) "activée" else "désactivée") + "."
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}
