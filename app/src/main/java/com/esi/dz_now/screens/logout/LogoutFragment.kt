package com.esi.dz_now.screens.logout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.esi.dz_now.R
import com.esi.dz_now.screens.LoginActivity

class LogoutFragment : Fragment() {


    private lateinit var viewModel: LogoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.logout_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LogoutViewModel::class.java)
        viewModel.logout()
        viewModel.isConnected.observe(this, Observer { isConnected ->
            if (isConnected == false) {
                // Go to login screen
                var intent = Intent(activity, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                startActivity(intent)
            }
        })
    }

}
