package ru.mycompany.NewsApp.ui.adapters.renderers;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.models.Match;
import ru.mycompany.NewsApp.ui.adapters.viewholders.MatchViewHolder;

public class MatchRenderer extends ViewRenderer<Match, MatchViewHolder> {

    public MatchRenderer(final int type, Context context) {
        super(type, context);
    }

    @Override
    public void onBindView(@NonNull final Match match, MatchViewHolder holder) {
        Picasso.with(getContext())
                .load(match.getHost().getEmblem())
                .into(holder.hostTeam);
        Picasso.with(getContext())
                .load(match.getGuest().getEmblem())
                .into(holder.guestTeam);
        holder.hostGoals.setText(Integer.toString(match.getHostGoals()));
        holder.guestGoals.setText(Integer.toString(match.getGuestGoals()));

        String date = match.getDate();
        holder.date.setText(date);
        if (date.equals("Live")) {
            holder.date.setTextColor(getContext().getColor(R.color.red));
        } else {
            holder.date.setTextColor(ColorStateList.valueOf(Color.BLACK));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNewsItemClick(match);
            }
        });
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@Nullable ViewGroup parent) {
        return new MatchViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_match, parent, false));
    }

    @Override
    public int getType() {
        return Match.TYPE;
    }
}
