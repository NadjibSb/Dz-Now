package com.esi.dz_now.screens.Settings


import android.os.Bundle
import android.preference.PreferenceManager
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


        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val currentTheme = sharedPref.getString(KEY_CURRENT_THEME, LIGHT_THEME)

        binding.themeSwitch.isChecked = currentTheme == DARK_THEME

        /*binding.themeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                //sharedPref.edit().putString(KEY_CURRENT_THEME, DARK_THEME).apply()
                Toast.makeText(context, "Dark theme", Toast.LENGTH_SHORT).show()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                //sharedPref.edit().putString(KEY_CURRENT_THEME, LIGHT_THEME).apply()
            }
            activity?.recreate()
        }*/
    }


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

    private fun categorieSelectionSetup(categories: List<Categories>) {
        var category_:Categories
        var culture_:Categories = categories[0]
        var health_:Categories =  categories[0]
        var sport_:Categories = categories[0]
        var international_:Categories =  categories[0]
        var politics_:Categories= categories[0]
        var tech_:Categories = categories[0]
        var msg:String = ""
        for(category_ in categories)
        {
            if(category_.name == Categories.CULTURE.name) culture_= category_
            if(category_.name == Categories.SPORTS.name) sport_= category_
            if(category_.name == Categories.TECH.name) tech_= category_
            if(category_.name == Categories.POLITICS.name) politics_ = category_
            if(category_.name == Categories.INTERNATIONAL.name) international_ = category_
            if(category_.name == Categories.SANTE.name) health_ = category_

        }
        checkBoxCulture.isChecked = culture_.isActivated
        checkBoxCulture.setOnCheckedChangeListener { view, isChecked ->
            culture_.isActivated = isChecked
            msg = getString(R.string.culture_category)+" "+ (if (isChecked) getString(R.string.enabled) else getString(R.string.disabled))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxSanté.isChecked = health_.isActivated
        checkBoxSanté.setOnCheckedChangeListener { view, isChecked ->
            health_.isActivated = isChecked
            msg = getString(R.string.health_category)+" "+ (if (isChecked) getString(R.string.enabled) else getString(R.string.disabled))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxInternational.isChecked = international_.isActivated
        checkBoxInternational.setOnCheckedChangeListener { view, isChecked ->
            international_.isActivated = isChecked
            msg = getString(R.string.international_category)+" "+ (if (isChecked) getString(R.string.enabled) else getString(R.string.disabled))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxPolitique.isChecked = politics_.isActivated
        checkBoxPolitique.setOnCheckedChangeListener { view, isChecked ->
            politics_.isActivated = isChecked
            msg = getString(R.string.politic_category)+" "+ (if (isChecked) getString(R.string.enabled) else getString(R.string.disabled))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxSport.isChecked = sport_.isActivated
        checkBoxSport.setOnCheckedChangeListener { view, isChecked ->
            sport_.isActivated = isChecked
            msg = getString(R.string.sport_category)+" "+ (if (isChecked) getString(R.string.enabled) else getString(R.string.disabled))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        checkBoxTechnologie.isChecked = tech_.isActivated
        checkBoxTechnologie.setOnCheckedChangeListener { view, isChecked ->
            tech_.isActivated = isChecked
            msg = getString(R.string.tech_category)+" "+ (if (isChecked) getString(R.string.enabled) else getString(R.string.disabled))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

    }
}
