package ru.mycompany.NewsApp;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import ru.mycompany.NewsApp.models.NewsItemModel;

public abstract class ViewRenderer<M extends NewsItemModel, VH extends RecyclerView.ViewHolder> {
    private final int mType;
    @NonNull
    private final Context mContext;

    public ViewRenderer(final int viewType, @NonNull final Context context) {
        mType = viewType;
        mContext = context;
    }

    @NonNull
    protected Context getContext() {
        return mContext;
    }

    public abstract void onBindView(@NonNull M model, VH holder);

    @NonNull
    public abstract VH onCreateViewHolder(@Nullable ViewGroup parent);

    public abstract int getType();
}
