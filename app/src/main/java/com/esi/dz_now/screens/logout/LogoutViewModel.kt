package com.esi.dz_now.screens.logout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LogoutViewModel : ViewModel() {

    val isConnected = MutableLiveData<Boolean>()

    init {
        isConnected.value = true
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        isConnected.value = false
    }
}
