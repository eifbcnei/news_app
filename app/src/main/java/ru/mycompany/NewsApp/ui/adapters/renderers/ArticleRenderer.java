package ru.mycompany.NewsApp.ui.adapters.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.ViewRenderer;
import ru.mycompany.NewsApp.models.Article;
import ru.mycompany.NewsApp.ui.adapters.viewholders.ArticleViewHolder;

public class ArticleRenderer extends ViewRenderer<Article, ArticleViewHolder> {
    public ArticleRenderer(int viewType, @NonNull Context context) {
        super(viewType, context);
    }

    @Override
    public void onBindView(@NonNull Article article, ArticleViewHolder holder) {
        holder.photo.setImageBitmap(article.getPhoto());
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getContent());
        holder.source.setText(article.getSource());
    }


    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@Nullable ViewGroup parent) {
        return new ArticleViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_article, parent, false));
    }

    @Override
    public int getType() {
        return Article.TYPE;
    }
}
