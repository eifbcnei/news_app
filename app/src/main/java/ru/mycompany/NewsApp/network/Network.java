package ru.mycompany.NewsApp.network;

import retrofit2.Retrofit;

public class Network {
    private static final Network instance = new Network();

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private Retrofit mRetrofit;

    public static Network getInstance() {
        return instance;
    }

    private Network() {
    }
}
