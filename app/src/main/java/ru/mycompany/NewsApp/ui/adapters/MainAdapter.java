package ru.mycompany.NewsApp.ui.adapters;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.mycompany.NewsApp.models.NewsItemModel;
import ru.mycompany.NewsApp.ViewRenderer;

public class MainAdapter extends RecyclerView.Adapter {
    @NonNull
    private ArrayList<NewsItemModel> mItems = new ArrayList<>();
    @NonNull
    private SparseArray<ViewRenderer> mRenderers = new SparseArray<>();


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ViewRenderer viewRenderer = mRenderers.get(viewType);
        if (viewRenderer != null) {
            return viewRenderer.onCreateViewHolder(parent);
        } else {
            throw new RuntimeException("Not supported Item View Type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final NewsItemModel item = getItem(position);
        final ViewRenderer renderer = mRenderers.get(item.getType());
        if (renderer != null) {
            renderer.onBindView(mItems.get(position), holder);
        } else {
            throw new RuntimeException("Not supported ViewHolder: " + holder);
        }
    }

    public void registerRenderer(@NonNull final ViewRenderer renderer) {
        final int type = renderer.getType();
        if (mRenderers.get(type) == null) {
            mRenderers.put(type, renderer);
        } else {
            throw new RuntimeException("ViewRenderer already exist with this type: " + type);
        }
    }

    @Override
    public int getItemViewType(final int position) {
        final NewsItemModel item = getItem(position);
        return item.getType();
    }

    private NewsItemModel getItem(final int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(@NonNull final ArrayList<NewsItemModel> items) {
        mItems.clear();
        mItems.addAll(items);
    }
}
