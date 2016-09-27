package example.raghunandan.databinding.apis;


import example.raghunandan.databinding.models.FeedResponse;
import example.raghunandan.databinding.utils.Constants;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


/**
 * Created by Raghunandan on 03-01-2016.
 */
public interface Api {


    // (@Header("language")String value,
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET(Constants.FETCH_FEED)
    Observable<FeedResponse> fetch_Feed(@Query("page") int page, @Query("per_page") int perpage, @Query("active") boolean active);


}
