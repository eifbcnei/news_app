package ru.mycompany.NewsApp.ui.adapters;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.models.NewsItemModel;
import ru.mycompany.NewsApp.ui.activities.MainActivity;
import ru.mycompany.NewsApp.ui.adapters.renderers.ViewRenderer;

@EBean
public class MainAdapter extends RecyclerView.Adapter {
    @NonNull
    private ArrayList<NewsItemModel> mItems = new ArrayList<>();
    @NonNull
    private SparseArray<ViewRenderer> mRenderers = new SparseArray<>();
    private Context mContext;
    private NewsItemModel mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;

    public MainAdapter(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

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

    public void setItems(@NonNull final List<NewsItemModel> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    public void deleteItemAt(int position) {
        mRecentlyDeletedItem = mItems.get(position);
        mRecentlyDeletedItemPosition = position;
        mItems.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar();
    }

    private void showUndoSnackbar() {
        View view = ((MainActivity) mContext).findViewById(R.id.main_container);
        Snackbar snackbar = Snackbar.make(view, mContext.getString(R.string.restore_hint), Snackbar.LENGTH_LONG);
        snackbar.setAction(mContext.getString(R.string.restore), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoDelete();
            }
        });
        snackbar.show();
    }

    private void undoDelete() {
        mItems.add(mRecentlyDeletedItemPosition, mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedItemPosition);
    }
}

