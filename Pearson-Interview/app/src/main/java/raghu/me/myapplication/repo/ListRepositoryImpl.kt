package raghu.me.myapplication.repo

import androidx.lifecycle.MutableLiveData
import raghu.me.myapplication.di.ListRepository

import raghu.me.myapplication.models.Users
import raghu.me.myapplication.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import raghu.me.myapplication.di.RetrofitDependency

class ListRepositoryImpl(private val retrofitDependency: RetrofitDependency): ListRepository {

    var data = MutableLiveData<List<Users>>()

    override fun  getUsers(): MutableLiveData<List<Users>>{
        val service = retrofitDependency.provideRetrofit().create(Api::class.java)
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

       return data
    }
}