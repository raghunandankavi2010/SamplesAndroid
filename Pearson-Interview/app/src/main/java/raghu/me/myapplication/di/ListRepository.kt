
package raghu.me.myapplication.di


import androidx.lifecycle.LiveData
import raghu.me.myapplication.AppExecutors
import raghu.me.myapplication.models.Users


interface ListRepository {
    fun  getUsers(appExecutors: AppExecutors): LiveData<raghu.me.myapplication.repo.Result<List<Users>>>
}
