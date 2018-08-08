package com.example.administrator.wanandroid.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hyy on 2018/8/8.
 */

public class RetrofitClient {

    private RetrofitClient(){}

    private static class ClientHolder {

        private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();


        private static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static Retrofit getInstance(){
        return ClientHolder.retrofit;
    }
}
