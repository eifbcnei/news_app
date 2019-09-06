package ru.mycompany.NewsApp.ui.adapters.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.mycompany.NewsApp.R;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    public final TextView title, description, source;
    public final ImageView photo;

    public ArticleViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.tv_article_title);
        description = itemView.findViewById(R.id.tv_article_description);
        source = itemView.findViewById(R.id.tv_source);
        photo = itemView.findViewById(R.id.iv_article_photo);
    }
}
