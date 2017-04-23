package com.example.raghu.retrofitsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public class MainActivity extends AppCompatActivity {


    // Just host this json {"last_question":"0","level":"0","error":"0"}
    // somewhere
    private static final String BASE_URL = "https://api.myjson.com/bins/";
    private final static String API_KEY="ddfffa28b4ace50cacf67274370469a1";
    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // get();
        getMovies();
    }

    private void get()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {

                Request originalRequest = chain.request(); //Current Request

                Response response = chain.proceed(originalRequest); //Get response of the request

                //I am logging the response body in debug mode. When I do this I consume the response (OKHttp only lets you do this once) so i have re-build a new one using the cached body
                String bodyString = response.body().string();

                Log.i("NetworkModule", String.format("Sending request %s with headers %s ", originalRequest.url(), originalRequest.headers()));
                Log.i("", (String.format("Got response HTTP %s %s \n\n with body %s \n\n with headers %s ", response.code(), response.message(), bodyString, response.headers())));

                response = response.newBuilder().body(
                        ResponseBody.create(response.body().contentType(), bodyString))
                        .build();

                return response;
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API mApi = retrofit.create(API.class);

        Call<Example> call = mApi.getData();
        call.enqueue(new Callback<Example>()
        {


            @Override
            public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {
                Log.i("Response",""+response.body().toString());
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t)
            {

                System.out.println(t.getMessage());
            }
        });


    }

    public interface API
    {

        @GET("cw29l")
        @Headers(
                "Content-Type: application/json")
        Call<Example> getData();
    }

    private static String bodyToString(final Request request){

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public void getMovies()
    {

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call= apiInterface.getNowPlayingMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, retrofit2.Response<MovieResponse> response) {

                Log.d("res",""+response.body());
                movieList= response.body().getResults();
                Log.d("log", "Number of movies received: " + movieList.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

}
