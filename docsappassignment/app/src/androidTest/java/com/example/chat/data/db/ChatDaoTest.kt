package com.example.chat.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatDaoTest {

    private lateinit var mDatabase: RoomSingleton

    @Before
    fun init() {
        mDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                RoomSingleton::class.java
        ).build()
    }

    @Test
    @Throws(InterruptedException::class)
    fun select_chat_by_id() = runBlocking {

        for (i in 1..10) {
            val chatMessage = ChatMessage("Hell0 $i", 12345, 1, 1)
            mDatabase.chatDao().insertAll(chatMessage)
        }

        val dbChats = mDatabase.chatDao().getAllChatMessages(1).first()

        assertThat(dbChats.size, equalTo(10))
    }

    @Test
    @Throws(InterruptedException::class)
    fun check_insertion_into_chat_list() = runBlocking {

        val chatList = listOf(
                ChatList(1, "Title 1"),
                ChatList(2, "Title 2")
        )

        mDatabase.chatDao().insertChatList(chatList)

        val list = mDatabase.chatDao().getAllChats().first()
        assertThat(list.size, equalTo(2))

        list.let {
            assertThat(it[0].chatListTitle, equalTo("Title 1"))
            assertThat(it[0].chatListId, equalTo(1))
        }
    }

    @Test
    @Throws(InterruptedException::class)
    fun check_insert_not_sent() = runBlocking {

        for (i in 1..10) {
            val notSent = NotSent("Message $i", 1,0)
            mDatabase.chatDao().insertNotSent(notSent)
        }


        val list = mDatabase.chatDao().getAllNotSent(1).first()
        assertThat(list.size, equalTo(10))

        list.let {
            assertThat(it[0].chatMessage, equalTo("Message 1"))
            assertThat(it[0].chatListId, equalTo(1))
            assertThat(it[0].sent, equalTo(0))
        }
    }

    @After
    fun cleanup() {
        mDatabase.close()
    }
}
