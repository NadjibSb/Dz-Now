package com.esi.dz_now.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.esi.dz_now.model.ArticleDao
import com.esi.dz_now.model.ArticleModel

@Database(entities = [ArticleModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}