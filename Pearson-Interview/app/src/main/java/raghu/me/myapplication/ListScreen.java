package raghu.me.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import raghu.me.myapplication.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class ListScreen extends AppCompatActivity implements ListAdapter.OnClickListener {

    private ProgressBar progressbar;
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        progressbar =  findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new ListAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api service = retrofit.create(Api.class);
        service.getUsers().enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if(response.isSuccessful()){
                    progressbar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    List<Users> list = response.body();
                    mAdapter.setData(list);
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                progressbar.setVisibility(View.GONE);
                 t.printStackTrace();
            }
        });
}

    @Override
    public void onClick(Users user) {
        Intent intent = new Intent(this,DetailScreen.class);
        intent.putExtra("user",user);
        startActivity(intent);

    }
}


