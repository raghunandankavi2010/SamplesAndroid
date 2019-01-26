package raghu.me.myapplication.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import raghu.me.myapplication.AppExecutors
import raghu.me.myapplication.di.ListRepository
import raghu.me.myapplication.models.Users


class ListScreenViewModel(private val repo: ListRepository, private val appExecutors: AppExecutors) : ViewModel() {

    fun getUsers() : LiveData<raghu.me.myapplication.repo.Result<List<Users>>> {
        return  repo.getUsers(appExecutors)
    }
}