package raghu.me.myapplication;

import raghu.me.myapplication.model.Users;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface Api {

    @GET("users")
     Call<List<Users>> getUsers();

}
