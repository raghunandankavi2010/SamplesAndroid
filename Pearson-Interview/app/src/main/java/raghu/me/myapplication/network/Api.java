package raghu.me.myapplication.network;

import raghu.me.myapplication.models.Users;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface Api {

    @GET("users")
     Call<List<Users>> getUsers();

}
