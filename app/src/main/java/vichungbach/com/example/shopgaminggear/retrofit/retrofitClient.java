package vichungbach.com.example.shopgaminggear.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.*;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitClient {
    private static  Retrofit instance;
    public  static  Retrofit getInstance(String baseUrl){
        if (instance ==null){
            instance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return instance;
    }
}
