package ru.mycompany.NewsApp.ui.adapters.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.models.Article;
import ru.mycompany.NewsApp.ui.adapters.viewholders.ArticleViewHolder;

public class ArticleRenderer extends ViewRenderer<Article, ArticleViewHolder> {
    public ArticleRenderer(int viewType, @NonNull Context context) {
        super(viewType, context);
    }

    @Override
    public void onBindView(@NonNull final Article article, ArticleViewHolder holder) {
        Picasso.with(getContext())
                .load(article.getMainPhotoUrl())
                .into(holder.mainPhoto);
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
        holder.source.setText(article.getSource());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNewsItemClick(article);
            }
        });
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
