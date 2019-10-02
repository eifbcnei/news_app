package ru.mycompany.NewsApp.ui.adapters.playerattributes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.models.Attribute;
import ru.mycompany.NewsApp.models.Player;

public class PlayerAttributesAdapter extends RecyclerView.Adapter<PlayerAttributesAdapter.ViewHolder> {


    private List<Attribute> players;
    private Context mContext;

    public PlayerAttributesAdapter(Context context, Player player) {
        this.mContext = context;
        this.players = player.getAttributes();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_player_attribute, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Attribute player = players.get(position);
        holder.tv_attribute.setText(player.getAttribute());
        holder.tv_attr_value.setText(player.getValue());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tv_attribute, tv_attr_value;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_attribute = itemView.findViewById(R.id.tv_attribute);
            tv_attr_value = itemView.findViewById(R.id.tv_attr_value);
        }
    }
}
