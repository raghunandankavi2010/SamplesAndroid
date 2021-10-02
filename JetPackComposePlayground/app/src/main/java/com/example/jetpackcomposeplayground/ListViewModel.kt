package com.example.jetpackcomposeplayground

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ListViewModel : ViewModel() {

    private val list = mutableListOf(
        Task(1L, "Buy milk", true),
        Task(2L, "Watch 'Call Me By Your Name'", false),
        Task(3L, "Listen 'Local Natives'", false),
        Task(4L, "Study about 'fakes instead of mocks'", false),
        Task(5L, "Congratulate Rafael", false),
        Task(6L, "Watch Kotlin YouTube Channel", false),
        Task(7L, "Buy milk", true),
        Task(8L, "Watch 'Call Me By Your Name'", false),
        Task(9L, "Listen 'Local Natives'", false),
        Task(10L, "Study about 'fakes instead of mocks'", false),
        Task(11L, "Congratulate Rafael", false),
        Task(12L, "Watch Kotlin YouTube Channel", false),
        Task(13L, "Buy milk", true),
        Task(14L, "Watch 'Call Me By Your Name'", false),
        Task(15L, "Listen 'Local Natives'", false),
        Task(16L, "Study about 'fakes instead of mocks'", false),
        Task(17L, "Congratulate Rafael", false),
        Task(18L, "Watch Kotlin YouTube Channel", false)
    )

    private val _taskList: MutableStateFlow<List<Task>> = MutableStateFlow(list)

    val taskList: StateFlow<List<Task>>
        get() = _taskList.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    fun onCheckedChange(task: Task) {

        val current = _taskList.value
        val replacement = current.map { if (it.id == task.id) it.copy(isCompleted = !it.isCompleted) else it }
        _taskList.value = replacement

    }
}