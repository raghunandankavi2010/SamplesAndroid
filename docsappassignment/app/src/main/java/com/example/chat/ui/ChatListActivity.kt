package com.example.chat.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.databinding.ActivityChatListBinding
import com.example.chat.db.RoomSingleton


class ChatListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatListBinding
    private val chatListViewModel: ChatListViewModel by viewModels {
        ChatListViewModelFactory(
            RoomSingleton.getInstance(
                applicationContext
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

       val layoutManager = LinearLayoutManager(this)
        binding.chatListRv.layoutManager  = layoutManager
        val dividerItemDecoration = DividerItemDecoration(
            this,
            layoutManager.orientation
        )
        binding.chatListRv.addItemDecoration(dividerItemDecoration)
        binding.chatListRv.setHasFixedSize(true)
        val adapter = ChatListAdapter(this) {
          val intent = Intent(this@ChatListActivity, MainActivity::class.java)
            intent.putExtra("id", it.chatListId)
            startActivity(intent)
        }
        binding.chatListRv.adapter = adapter
        chatListViewModel.getAllChats().observe(this, { list ->
            list?.let {
                adapter.submitList(it)
            }
        })

    }
}