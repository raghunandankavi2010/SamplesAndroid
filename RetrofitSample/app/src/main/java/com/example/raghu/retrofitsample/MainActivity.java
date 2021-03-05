package com.example.raghu.retrofitsample;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getProducts();
    }


   public void getProducts() {
       ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
       apiInterface.getProducts().enqueue(new Callback<ResponseObject>() {

           @Override
           public void onResponse(@NotNull Call<ResponseObject> call, @NotNull retrofit2.Response<ResponseObject> response) {
               if (response.isSuccessful()) {
                   ResponseObject responseObject = response.body();
                   Product product = responseObject.getProduct();
                   String content = "";
                   content += "NAME: " + product.getName() + "\n";
                   content += "COMPANY: " + product.getCompany() + "\n";
                   content += "KEY: " + product.getKey() + "\n";
                   TextView text = findViewById(R.id.text);
                   text.setText(content);
               }

           }

           @Override
           public void onFailure(@NotNull Call<ResponseObject> call, Throwable t) {
                      t.printStackTrace();
           }
       });
   }

}
