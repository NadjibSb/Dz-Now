package com.esi.dz_now.screens.Settings


import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.esi.dz_now.R
import com.esi.dz_now.data.Categories
import com.esi.dz_now.data.SharedData
import com.esi.dz_now.databinding.FragmentSettingsBinding
import com.esi.dz_now.screens.MainActivity
import kotlinx.android.synthetic.main.fragment_settings.*
import android.widget.CompoundButton
import android.graphics.Color
import android.util.Log


class SettingsFragment : Fragment() {

    private lateinit var data: SharedData
    private lateinit var categories:List<Categories>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentSettingsBinding = DataBindingUtil.inflate(inflater,
            com.esi.dz_now.R.layout.fragment_settings,container,false)
        (activity as MainActivity).supportActionBar?.title = getString(com.esi.dz_now.R.string.settings_fragment_title)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = activity as SharedData
        categories = data.getCategories()

        checkBoxSport.isChecked = categories[0].isActivated
        checkBoxSport.setOnCheckedChangeListener { view, isChecked ->
            categories[0].isActivated = isChecked
            val msg = "Catégorie Sport " + (if (isChecked) "activée" else "désactivée")+"."
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

        }

        checkBoxCulture.isChecked = categories[1].isActivated
        checkBoxCulture.setOnCheckedChangeListener { view, isChecked ->
            categories[1].isActivated = isChecked
            val msg = "Catégorie Culture " + (if (isChecked) "activée" else "désactivée")+"."
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

        }

        checkBoxPolitique.isChecked = categories[2].isActivated
        checkBoxPolitique.setOnCheckedChangeListener { view, isChecked ->
            categories[2].isActivated = isChecked
            val msg = "Catégorie Politique " + (if (isChecked) "activée" else "désactivée")+"."
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

        }

        checkBoxInternational.isChecked = categories[3].isActivated
        checkBoxInternational.setOnCheckedChangeListener { view, isChecked ->
            categories[3].isActivated = isChecked
            val msg = "Catégorie International " + (if (isChecked) "activée" else "désactivée")+"."
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

        }

        checkBoxSanté.isChecked = categories[4].isActivated
        checkBoxSanté.setOnCheckedChangeListener { view, isChecked ->
            categories[4].isActivated = isChecked
            val msg = "Catégorie Santé " + (if (isChecked) "activée" else "désactivée")+"."
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

        }

        checkBoxTechnologie.isChecked = categories[5].isActivated
        checkBoxTechnologie.setOnCheckedChangeListener { view, isChecked ->
            categories[5].isActivated = isChecked
            val msg = "Catégorie Technologie " + (if (isChecked) "activée" else "désactivée")+"."
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

        }

        toggleButtonTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The toggle is enabled/checked
                Toast.makeText(context,"Light Theme",Toast.LENGTH_SHORT).show()

                this.activity?.setTheme(com.esi.dz_now.R.style.AppTheme)
            } else {
                // The toggle is disabled
                Toast.makeText(context,"Dark Theme off",Toast.LENGTH_SHORT).show()

                activity?.setTheme(com.esi.dz_now.R.style.AppTheme_DARK)

            }
        }


    }




}
