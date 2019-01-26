package raghu.me.myapplication.repo

import androidx.lifecycle.MutableLiveData
import raghu.me.myapplication.di.RetrofitDependency
import raghu.me.myapplication.models.Users
import raghu.me.myapplication.network.*

class FetchListData constructor(
     private val retrofitDependency: RetrofitDependency): Runnable {

    var data = MutableLiveData<Result<List<Users>>>()
    override fun run() {

        val service = retrofitDependency.provideRetrofit().create(Api::class.java)
        val response = service.users.execute()
        if(response.isSuccessful) {
            val responseBody: List<Users> = response.body() as List<Users>
            data.postValue(Result.Success(responseBody))
        } else {
            val errorBody = response.errorBody() as Throwable
            data.postValue(Result.Error(errorBody))
        }

    }

}