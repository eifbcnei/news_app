package ru.mycompany.NewsApp.ui.activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

import ru.mycompany.NewsApp.AppPreferences;
import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.models.Article;
import ru.mycompany.NewsApp.models.Match;
import ru.mycompany.NewsApp.models.NewsItemModel;
import ru.mycompany.NewsApp.ui.adapters.main.MainAdapter;
import ru.mycompany.NewsApp.ui.adapters.main.NewsItemClickListener;
import ru.mycompany.NewsApp.ui.adapters.main.SwipeRemoveCallback;
import ru.mycompany.NewsApp.ui.adapters.main.renderers.ArticleRenderer;
import ru.mycompany.NewsApp.ui.adapters.main.renderers.MatchRenderer;
import ru.mycompany.NewsApp.viewmodels.MainViewModel;

@EActivity
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
    @ViewById
    Chip chip_all_posts;
    @ViewById
    SearchView sv_search;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(AppPreferences.getCurrentTheme());
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //when return from settings activity
        //recreate this activity according to preferences
        //if returns from another activity this will update data
        recreate();
    }

    @AfterViews
    void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(null);

        final ImageButton btnSettings = findViewById(R.id.ib_settings);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        //hide btnSettings when sv_search view is expanded
        //else show
        sv_search.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSettings.setVisibility(View.GONE);
            }
        });

        sv_search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                btnSettings.setVisibility(View.VISIBLE);
                return false;
            }
        });
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    }

    @Override
    public void onBackPressed() {
        //when search view is expanded close it
        //else close app
        if (!sv_search.isIconified()) {
            sv_search.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }

    @AfterViews
    void initMainAdapter() {
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
                if (!AppPreferences.areTagsVisible()) {
                    cg_tags.setVisibility(View.GONE);
                } else {
                    initTags(tags);
                }
            }
        });
    }

    private void initTags(List<String> tags) {
        //default chips are : all news,live,hot
        final int DEFAULT_CHIPS_COUNT = 3;
        if (cg_tags.getChildCount() > DEFAULT_CHIPS_COUNT) {
            //if tags were updated then remove not default tags
            //and then add new tags
            cg_tags.removeViews(DEFAULT_CHIPS_COUNT, cg_tags.getChildCount() - DEFAULT_CHIPS_COUNT);
        }

        //if tags were updated set default checked
        chip_all_posts.setChecked(true);
        boolean isNightTheme = AppPreferences.isNightTheme();

        for (String tag : tags) {
            Chip chip = new Chip(MainActivity.this);
            chip.setId(View.generateViewId());
            chip.setText(tag);
            chip.setChipBackgroundColor(getResources().getColorStateList(isNightTheme ?
                    R.color.dark_blue : R.color.violet));
            chip.setTextColor(getResources().getColor(isNightTheme ? R.color.orange : R.color.white));
            chip.setClickable(true);
            chip.setCheckable(true);
            chip.setRippleColor(getResources().getColorStateList(isNightTheme ? R.color.light_gray : R.color.light_blue));
            cg_tags.addView(chip);
        }
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
                    chip_all_posts = findViewById(R.id.chip_all_posts);
                    chip_all_posts.setChecked(true);
                    return;
                }
                Chip chip = chipGroup.findViewById(id);
                String tag = chip.getText().toString();
                viewModel.filter(tag);
            }
        });
    }

    @AfterViews
    void onRefreshListenerInit() {
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
