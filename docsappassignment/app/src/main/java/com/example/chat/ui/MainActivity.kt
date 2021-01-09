package com.example.chat.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.databinding.ActivityMainBinding
import com.example.chat.db.RoomSingleton
import com.example.chat.util.ConnectionLiveData
import com.example.chat.util.isNetworkAvailable
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.absoluteValue
import com.example.chat.util.Result
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var chatAdapter: ChatAdapter
    private var verticalScrollOffset = AtomicInteger(0)
    private var id by Delegates.notNull<Int>()
    private val chatViewModel: ChatViewModel by viewModels {
        MyViewModelFactory(
                RoomSingleton.getInstance(
                        applicationContext
                )
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        id = intent.getIntExtra("id", -1)
        Log.i("chatid","$id")

        val connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this,  { isConnected ->
            isConnected?.let {
                if(isConnected) {
                    Log.i("Connected","True")
                    chatViewModel.getAllNotSent(id)
                }else{
                    Log.i("Connected","False")
                }
                //
            }
        })

      chatViewModel.chatNotSent.observe(this, {
            it?.let {
                for(i in it.indices){
                    chatViewModel.sent(it[i])
                }
            }
        })

        chatViewModel.chatSent.observe(this, {
            it?.let { result ->
                when (result) {
                    is Result.Success -> {
                        val chatResponse = result.data
                        Log.i("chatResponse",""+chatResponse.message)
                        chatResponse.message.chatSendOrReceive = 2
                        chatViewModel.storeReceivedMessage(chatResponse.message.message, id)
                        chatViewModel.deleteNotSent(id)
                    }
                    is Result.Error -> {
                        Toast.makeText(this, "${result.exception}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        chatAdapter = ChatAdapter(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = chatAdapter

        binding.send.setOnClickListener {

            if (binding.messageInput.text.toString().isNotEmpty()) {
                if (isNetworkAvailable()) {
                    chatViewModel.setChatMessage(binding.messageInput.text.toString(), id)
                    binding.messageInput.setText("")
                } else {
                    chatViewModel.storeInDb(binding.messageInput.text.toString(), id)
                    chatViewModel.storeInNotSent(binding.messageInput.text.toString(), id)
                    binding.messageInput.setText("")
                }
            }
        }

        binding.recyclerView.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            val y = oldBottom - bottom
            if (y.absoluteValue > 0) {
                binding.recyclerView.post {
                    if (y > 0 || verticalScrollOffset.get().absoluteValue >= y.absoluteValue) {
                        binding.recyclerView.scrollBy(0, y)
                    } else {
                        binding.recyclerView.scrollBy(0, verticalScrollOffset.get())
                    }
                }
            }
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var state = AtomicInteger(RecyclerView.SCROLL_STATE_IDLE)

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                state.compareAndSet(RecyclerView.SCROLL_STATE_IDLE, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        if (!state.compareAndSet(RecyclerView.SCROLL_STATE_SETTLING, newState)) {
                            state.compareAndSet(RecyclerView.SCROLL_STATE_DRAGGING, newState)
                        }
                    }
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        state.compareAndSet(RecyclerView.SCROLL_STATE_IDLE, newState)
                    }
                    RecyclerView.SCROLL_STATE_SETTLING -> {
                        state.compareAndSet(RecyclerView.SCROLL_STATE_DRAGGING, newState)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (state.get() != RecyclerView.SCROLL_STATE_IDLE) {
                    verticalScrollOffset.getAndAdd(dy)
                }
            }
        })
        binding.messageInput.doOnTextChanged { text, start, before, count ->

            binding.send.isEnabled = text.toString().isNotEmpty()
        }


        chatViewModel.chatResponse.observe(this, {
            it?.let { result ->
                when (result) {
                    is Result.Success -> {
                        val chatRespone = result.data
                        chatRespone.message.chatSendOrReceive = 2
                        chatViewModel.storeReceivedMessage(chatRespone.message.message, id)
                    }
                    is Result.Error -> {
                        Toast.makeText(this, "${result.exception}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        chatViewModel.getAllChats(id).observe(this, { chatList ->
            chatList?.let {
                val sortedList = chatList.sortedWith(compareBy { it.chatTime })
                chatAdapter.setChatList(sortedList)
                binding.recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
            }
        })
    }
}
