package ru.mycompany.NewsApp.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.mycompany.NewsApp.models.Article;
import ru.mycompany.NewsApp.models.Match;

public interface RetrofitImpl {

    @GET("matches")
    Call<List<Match>> getAllMatchPosts();

    @GET("articles")
    Call<List<Article>> getAllArticlesPosts();
}
