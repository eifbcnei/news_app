package ru.mycompany.NewsApp.network;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Response;
import ru.mycompany.NewsApp.models.Article;
import ru.mycompany.NewsApp.models.Match;
import ru.mycompany.NewsApp.models.NewsItemModel;

public class Repository {
    private final static Repository instance = new Repository();

    private Repository() {
    }

    public static Repository getInstance() {
        return instance;
    }

    public List<NewsItemModel> getAllPosts() throws ExecutionException, InterruptedException {
        List<NewsItemModel> data = new ArrayList<>();
        data.addAll(new MatchesDownloader().execute().get());
        data.addAll(new ArticlesDownloader().execute().get());
        Collections.shuffle(data);
        return data;
    }


    static class MatchesDownloader extends AsyncTask<Void, Void, List<Match>> {
        @Override
        protected List<Match> doInBackground(Void... voids) {
            try {
                Response<List<Match>> response = Network
                        .getInstance()
                        .getApi()
                        .getAllMatchPosts()
                        .execute();
                if (response.isSuccessful()) {
                    return response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }

    static class ArticlesDownloader extends AsyncTask<Void, Void, List<Article>> {

        @Override
        protected List<Article> doInBackground(Void... voids) {
            try {
                Response<List<Article>> response = Network
                        .getInstance()
                        .getApi()
                        .getAllArticlesPosts()
                        .execute();
                if (response.isSuccessful()) {
                    return response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }
}
