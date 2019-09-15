package ru.mycompany.NewsApp.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.mycompany.NewsApp.models.Article;
import ru.mycompany.NewsApp.models.Match;
import ru.mycompany.NewsApp.models.Team;

public interface RetrofitImpl {
    @GET("matches")
    Call<List<Match>> getAllMatchPosts();

    @GET("articles")
    Call<List<Article>> getAllArticlesPosts();

    @GET("teams")
    Call<List<Team>> getTeam(@Query("name") String team);

    @GET("tags")
    Call<List<String>> getTags();
}
