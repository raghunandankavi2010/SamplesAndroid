package raghu.me.myapplication.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import raghu.me.myapplication.di.ListRepository
import raghu.me.myapplication.models.Users


class ListScreenViewModel(private val repo: ListRepository) : ViewModel() {


    fun getUsers() : LiveData<List<Users>> {
        return  repo.getUsers()
    }
}