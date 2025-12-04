package com.example.vivito.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vivito.dao.UserDao
import com.example.vivito.dao.CitaDao
import com.example.vivito.model.User
import com.example.vivito.model.Cita

@Database(entities = [User::class, Cita::class], version = 1)
abstract class AppDatabaseC : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun citaDao(): CitaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabaseC? = null

        fun getDatabase(context: Context): AppDatabaseC {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabaseC::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

