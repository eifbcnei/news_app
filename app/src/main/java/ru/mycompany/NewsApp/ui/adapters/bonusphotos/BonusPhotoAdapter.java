package ru.mycompany.NewsApp.ui.adapters.bonusphotos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.mycompany.NewsApp.R;

public class BonusPhotoAdapter extends RecyclerView.Adapter<BonusPhotoAdapter.ViewHolder> {

    private List<String> mUrls;
    private Context mContext;

    public BonusPhotoAdapter(Context context, List<String> urls) {
        mContext = context;
        mUrls = urls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_bonus_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(mContext)
                .load(mUrls.get(position))
                .into(holder.bonusPhoto);
    }

    @Override
    public int getItemCount() {
        return mUrls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView bonusPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bonusPhoto = itemView.findViewById(R.id.iv_item_bonus_photo);
        }
    }
}
