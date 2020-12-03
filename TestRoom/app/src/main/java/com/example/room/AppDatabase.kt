package com.example.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PersonEntity::class, Pet::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}