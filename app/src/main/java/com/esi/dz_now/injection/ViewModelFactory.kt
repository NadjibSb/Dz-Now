package com.esi.dz_now.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.esi.dz_now.model.database.AppDatabase
import com.esi.dz_now.viewmodel.SavedArticlesListViewModel

class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedArticlesListViewModel::class.java)) {
            val db = Room.databaseBuilder(
                activity.applicationContext,
                AppDatabase::class.java,
                "articles"
            ).build()
            @Suppress("UNCHECKED_CAST")
            return SavedArticlesListViewModel(db.articleDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}