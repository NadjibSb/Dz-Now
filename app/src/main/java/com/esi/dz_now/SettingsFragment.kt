package com.esi.dz_now


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.esi.dz_now.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentSettingsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_settings,container,false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.settings_fragment_title)
        return binding.root
    }


}
