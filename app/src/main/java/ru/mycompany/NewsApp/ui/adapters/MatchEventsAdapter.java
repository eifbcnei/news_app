package ru.mycompany.NewsApp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.models.MatchEvent;

public class MatchEventsAdapter extends RecyclerView.Adapter<MatchEventsAdapter.ViewHolder> {
    private List<MatchEvent> matchEvents;
    private Context mContext;

    public MatchEventsAdapter(Context context, List<MatchEvent> matchEvents) {
        mContext = context;
        this.matchEvents = matchEvents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_match_event, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatchEvent event = matchEvents.get(position);
        holder.tv_minute.setText(Integer.toString(event.getMinute()));
        if (event.isHostTeamPlayer()) {
            holder.tv_host_player.setText(event.getPlayer());
        } else {
            holder.tv_guest_player.setText(event.getPlayer());
        }
        switch (event.getType()) {
            case "GOAL":
                holder.iv_type.setImageResource(R.drawable.ic_ball);
                break;
            case "YELLOW_CARD":
                holder.iv_type.setImageResource(R.drawable.ic_yellow_card);
                break;
            case "RED_CARD":
                holder.iv_type.setImageResource(R.drawable.ic_red_card);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return matchEvents.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tv_minute, tv_host_player, tv_guest_player;
        final ImageView iv_type;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_minute = itemView.findViewById(R.id.tv_time);
            tv_host_player = itemView.findViewById(R.id.tv_host_player);
            tv_guest_player = itemView.findViewById(R.id.tv_guest_player);
            iv_type = itemView.findViewById(R.id.iv_event_icon);
        }
    }
}
