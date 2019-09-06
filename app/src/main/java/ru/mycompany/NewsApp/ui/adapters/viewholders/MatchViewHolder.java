package ru.mycompany.NewsApp.ui.adapters.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.mycompany.NewsApp.R;

public class MatchViewHolder extends RecyclerView.ViewHolder {
    public final ImageView hostTeam, guestTeam;
    public final TextView hostGoals, guestGoals;


    public MatchViewHolder(@NonNull View itemView) {
        super(itemView);
        hostTeam = itemView.findViewById(R.id.iv_team_host);
        guestTeam = itemView.findViewById(R.id.iv_team_guest);
        hostGoals = itemView.findViewById(R.id.tv_host_goals);
        guestGoals = itemView.findViewById(R.id.tv_guest_goals);
    }
}
