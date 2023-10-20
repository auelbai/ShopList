package com.example.shoplist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun shopListDao(): ShopListDao


    companion object {
        private const val DATABASE_NAME = "shopList"
        private val LOCK = Any()
        private var INSTANCE: AppDatabase? = null

        fun getInstance(application: Application): AppDatabase {

            INSTANCE?.let {
                return it
            }

            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}