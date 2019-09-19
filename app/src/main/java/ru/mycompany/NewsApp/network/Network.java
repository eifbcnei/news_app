package ru.mycompany.NewsApp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static final Network instance = new Network();

    private static final String BASE_URL = "https://my-json-server.typicode.com/eifbcnei/news_app/";
    private Retrofit mRetrofit;

    public static Network getInstance() {
        return instance;
    }

    private Network() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(2, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(client)
                .build();
    }
    private Gson getGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }
    RetrofitImpl getApi() {
        return mRetrofit.create(RetrofitImpl.class);
    }
}
