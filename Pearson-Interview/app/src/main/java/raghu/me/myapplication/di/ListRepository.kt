
package raghu.me.myapplication.di


import androidx.lifecycle.MutableLiveData
import raghu.me.myapplication.models.Users

interface ListRepository {
    fun  getUsers(): MutableLiveData<List<Users>>
}
