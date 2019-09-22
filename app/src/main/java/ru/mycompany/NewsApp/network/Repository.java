package ru.mycompany.NewsApp.network;

import android.os.AsyncTask;
import android.util.Log;

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

    public List<String> getTags() throws ExecutionException, InterruptedException {
        return new TagsDownloader().execute().get();
    }

    static class TagsDownloader extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            try {
                Response<List<String>> response = Network
                        .getInstance()
                        .getApi()
                        .getTags()
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
                    List<Match> matches = response.body();
                    for (Match match : matches) {
                        match.setHost(Network
                                .getInstance()
                                .getApi()
                                .getTeam(match.getHost_key())
                                .execute()
                                .body()
                                .get(0));
                        match.setGuest(Network
                                .getInstance()
                                .getApi()
                                .getTeam(match.getGuest_key())
                                .execute()
                                .body()
                                .get(0));
                    }
                    return matches;
                }
            } catch (Exception e) {
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
