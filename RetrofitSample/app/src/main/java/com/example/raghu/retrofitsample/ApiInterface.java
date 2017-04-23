package com.example.raghu.retrofitsample;

/**
 * Created by raghu on 23/4/17.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


// to define endpoints of a URL we will use special retrofit annotations. we have used Get annotation in
// to get information from the API and the parameters os this method are @Query,@Path,
public interface ApiInterface {
    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String apikey);

    //return value is alwayays a parameterized call<T> oject whose paremeter is generic so we can have
    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
