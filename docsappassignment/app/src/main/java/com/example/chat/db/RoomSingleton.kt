package com.example.chat.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors


@Database(entities = arrayOf(ChatMessage::class, ChatList::class, NotSent::class), version = 1, exportSchema = false)
abstract class RoomSingleton : RoomDatabase(){
    abstract fun chatDao():ChatDao

    companion object{
        private var INSTANCE: RoomSingleton? = null
        fun getInstance(context: Context): RoomSingleton{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                        context,
                        RoomSingleton::class.java,
                        "roomdb")
                        .addCallback(seedDatabaseCallback(context))
                    .build()
            }

            return INSTANCE as RoomSingleton
        }

        private fun seedDatabaseCallback(context: Context): RoomDatabase.Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    seedData(context)
                }
            }
        }

        private fun seedData(context: Context) {
            Executors.newSingleThreadScheduledExecutor().execute {
                val db = RoomSingleton.getInstance(context)
                val mList = ArrayList<ChatList>()

               for(i in 0..10){
                   val chatList = ChatList(i,"ChatList $i")
                   mList.add(chatList)
               }
                db.chatDao().insertChatList(mList)
            }
        }
    }

}