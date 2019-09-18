package com.esi.dz_now.screens.logout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LogoutViewModel : ViewModel() {

    val is_connected = MutableLiveData<Boolean>()

    init {
        is_connected.value = true
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        is_connected.value = false
    }
}
