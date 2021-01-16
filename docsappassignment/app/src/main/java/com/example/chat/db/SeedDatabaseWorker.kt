package com.example.chat.db

import android.content.Context
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker @WorkerInject constructor (
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val chatDao: ChatDao
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {

            val mList = ArrayList<ChatList>()

            for(i in 0..10){
                val chatList = ChatList(i,"ChatList $i")
                mList.add(chatList)
            }
            chatDao.insertChatList(mList)
            Result.success()
            
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}
