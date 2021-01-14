package com.example.chat.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.Executors


@Database(entities = arrayOf(ChatMessage::class, ChatList::class, NotSent::class), version = 1, exportSchema = false)
abstract class RoomSingleton : RoomDatabase() {
    abstract fun chatDao(): ChatDao


    companion object {

        // For Singleton instantiation
        @Volatile
        private var INSTANCE: RoomSingleton? = null

        fun getInstance(context: Context): RoomSingleton {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): RoomSingleton {
            return Room.databaseBuilder(
                    context,
                    RoomSingleton::class.java,
                    "roomdb")
                    .addCallback(
                            object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                                    WorkManager.getInstance(context).enqueue(request)
                                }
                            }
                    )
                    .build()
        }
    }
    

}