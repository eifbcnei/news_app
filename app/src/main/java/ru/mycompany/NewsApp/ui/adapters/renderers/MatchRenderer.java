package ru.mycompany.NewsApp.ui.adapters.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.ViewRenderer;
import ru.mycompany.NewsApp.models.Match;
import ru.mycompany.NewsApp.ui.adapters.viewholders.MatchViewHolder;

public class MatchRenderer extends ViewRenderer<Match, MatchViewHolder> {

    public MatchRenderer(final int type, Context context) {
        super(type, context);
    }


    @Override
    public void onBindView(@NonNull Match match, MatchViewHolder holder) {
        holder.hostTeam.setImageBitmap(match.getHostTeam());
        holder.guestTeam.setImageBitmap(match.getGuestTeam());
        holder.hostGoals.setText(Integer.toString(match.getHostGoals()));
        holder.guestGoals.setText(Integer.toString(match.getGuestGoals()));
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
