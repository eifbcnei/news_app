package ru.mycompany.NewsApp.ui.adapters.matchevents;

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
    private static final String EVENT_YELLOW_CARD = "YELLOW_CARD";
    private static final String EVENT_RED_CARD = "RED_CARD";
    private static final String EVENT_CHANGE = "CHANGE";
    private static final String EVENT_INJURY = "INJURY";
    private static final String EVENT_GOAL = "GOAL";

    private List<MatchEvent> matchEvents;
    private Context mContext;
    private PlayerClickListener listener;

    public MatchEventsAdapter(Context context, List<MatchEvent> matchEvents) {
        mContext = context;
        listener = (PlayerClickListener) context;
        this.matchEvents = matchEvents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_match_event, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final MatchEvent event = matchEvents.get(position);
        holder.tv_minute.setText(Integer.toString(event.getMinute()));
        if (event.isHostTeamPlayer()) {
            holder.tv_host_player.setText(event.getPlayer());
            holder.tv_host_player.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!event.getType().equals(EVENT_CHANGE))
                        listener.onPlayerSelected(event.getPlayer());
                }
            });
        } else {
            holder.tv_guest_player.setText(event.getPlayer());
            holder.tv_guest_player.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!event.getType().equals(EVENT_CHANGE))
                        listener.onPlayerSelected(event.getPlayer());
                }
            });
        }
        switch (event.getType()) {
            case EVENT_GOAL:
                holder.iv_type.setImageResource(R.drawable.ic_ball);
                break;
            case EVENT_YELLOW_CARD:
                holder.iv_type.setImageResource(R.drawable.ic_yellow_card);
                break;
            case EVENT_RED_CARD:
                holder.iv_type.setImageResource(R.drawable.ic_red_card);
                break;
            case EVENT_CHANGE:
                holder.iv_type.setImageResource(R.drawable.ic_change_24dp);
                break;
            case EVENT_INJURY:
                holder.iv_type.setImageResource(R.drawable.ic_injury_24dp);
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


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_minute = itemView.findViewById(R.id.tv_time);
            tv_host_player = itemView.findViewById(R.id.tv_host_player);
            tv_guest_player = itemView.findViewById(R.id.tv_guest_player);
            iv_type = itemView.findViewById(R.id.iv_event_icon);
        }
    }
}
