package ru.mycompany.NewsApp.ui.activities;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.models.Article;
import ru.mycompany.NewsApp.models.Match;
import ru.mycompany.NewsApp.models.NewsItemModel;
import ru.mycompany.NewsApp.ui.adapters.MainAdapter;
import ru.mycompany.NewsApp.ui.adapters.NewsItemClickListener;
import ru.mycompany.NewsApp.ui.adapters.SwipeRemoveCallback;
import ru.mycompany.NewsApp.ui.adapters.renderers.ArticleRenderer;
import ru.mycompany.NewsApp.ui.adapters.renderers.MatchRenderer;
import ru.mycompany.NewsApp.viewmodels.MainViewModel;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NewsItemClickListener {

    @ViewById
    RecyclerView rv_news;
    @ViewById
    SwipeRefreshLayout srl_refresh;
    private MainViewModel viewModel;
    @Bean
    MainAdapter adapter;

    @ViewById
    ChipGroup cg_tags;


    @AfterViews
    void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @AfterViews
    void initAdapter() {
        adapter.registerRenderer(new MatchRenderer(Match.TYPE, this));
        adapter.registerRenderer(new ArticleRenderer(Article.TYPE, this));
        rv_news.setLayoutManager(new LinearLayoutManager(this));
        rv_news.setAdapter(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(new SwipeRemoveCallback(adapter));
        helper.attachToRecyclerView(rv_news);
    }

    @AfterViews
    void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getVisibleData().observe(this, new Observer<CopyOnWriteArrayList<NewsItemModel>>() {
            @Override
            public void onChanged(CopyOnWriteArrayList<NewsItemModel> newsItemModels) {
                Log.d("___onDataChanged", newsItemModels.toString());
                if (newsItemModels.isEmpty() && !isConnected()) {
                    //If something went wrong notify user
                    Toast.makeText(MainActivity.this, getString(R.string.bad_internet_warning), Toast.LENGTH_LONG).show();
                    return;
                }
                final LayoutAnimationController controller
                        = AnimationUtils.loadLayoutAnimation(rv_news.getContext(), R.anim.layout_animation_slide_right);
                rv_news.setLayoutAnimation(controller);
                adapter.setItems(newsItemModels);
                adapter.notifyDataSetChanged();
                rv_news.scheduleLayoutAnimation();
            }
        });
        viewModel.getTags().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> tags) {
                for (String tag : tags) {
                    Chip chip = new Chip(MainActivity.this);
                    chip.setId(View.generateViewId());
                    chip.setText(tag);
                    chip.setChipBackgroundColor(getResources().getColorStateList(R.color.colorAccentDark));
                    chip.setTextColor(Color.WHITE);
                    chip.setClickable(true);
                    chip.setCheckable(true);
                    chip.setRippleColor(getResources().getColorStateList(R.color.colorBackground));
                    cg_tags.addView(chip);
                }
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @AfterViews
    void filterSelectedTag() {
        cg_tags.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int id) {
                if (id == View.NO_ID) {
                    //if no chip was selected app would crash
                    //default chip to be selected - 'all news'
                    Chip chip_all_posts = findViewById(R.id.chip_all_posts);
                    chip_all_posts.setChecked(true);
                    return;
                }
                Chip chip = chipGroup.findViewById(id);
                String text = chip.getText().toString();
                viewModel.filter(text);
            }
        });
    }

    @AfterViews
    void onRefreshListenerInit() {
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("___onRefresh", Boolean.toString(isConnected()));
                if (isConnected()) {
                    //if connected try downloading data from server
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final List<NewsItemModel> updatedData = viewModel.refreshPosts();
                            final List<String> updatedTags = viewModel.refreshTags();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // LiveData can only be updated on ui thread
                                    viewModel.onDataUpdated(updatedData, updatedTags);
                                    srl_refresh.setRefreshing(false);
                                }
                            });
                        }
                    }).start();
                } else {
                    //else stop animation and notify user
                    srl_refresh.setRefreshing(false);
                    Toast.makeText(MainActivity.this, getString(R.string.bad_internet_warning), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView search = (SearchView) searchItem.getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.onSearchRequested(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.onSearchRequested(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onNewsItemClick(NewsItemModel item) {
        switch (item.getType()) {
            case Match.TYPE:
                MatchActivity_.intent(this).extra("Match", item).start();
                break;
            case Article.TYPE:
                ArticleActivity_.intent(this).extra("Article", item).start();
                break;
        }
    }

}
