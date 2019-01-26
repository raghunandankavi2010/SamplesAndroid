package raghu.me.myapplication.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import raghu.me.myapplication.AppExecutors
import raghu.me.myapplication.di.ListRepository
import raghu.me.myapplication.di.RetrofitDependency
import raghu.me.myapplication.models.Users
import raghu.me.myapplication.network.Api

class ListRepositoryImpl(private val retrofitDependency: RetrofitDependency): ListRepository {

    var data = MutableLiveData<List<Users>>()

    override fun  getUsers(appExecutors: AppExecutors):LiveData<Result<List<Users>>>{


        val fetchListData = FetchListData(retrofitDependency)
        appExecutors.networkIO().execute(fetchListData)
        return fetchListData.data
        /*val service = retrofitDependency.provideRetrofit().create(Api::class.java)
        service.users.enqueue(object : Callback<List<Users>> {
            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                if (response.isSuccessful) {

                     val list = response.body()
                     data.value= (list!!)

                }
            }
            override fun onFailure(call: Call<List<Users>>, t: Throwable) {

                t.printStackTrace()
            }
        })

    return data*/
    }
}