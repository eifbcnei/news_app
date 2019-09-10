package ru.mycompany.NewsApp.ui.activities;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.models.Article;
import ru.mycompany.NewsApp.models.Match;
import ru.mycompany.NewsApp.models.NewsItemModel;
import ru.mycompany.NewsApp.ui.adapters.MainAdapter;
import ru.mycompany.NewsApp.ui.adapters.NewsItemClickListener;
import ru.mycompany.NewsApp.ui.adapters.renderers.ArticleRenderer;
import ru.mycompany.NewsApp.ui.adapters.renderers.MatchRenderer;
import ru.mycompany.NewsApp.viewmodels.MainViewModel;

@EActivity(R.layout.activity_main)
//@OptionsMenu(R.menu.menu_search)
public class MainActivity extends AppCompatActivity implements NewsItemClickListener {

    @ViewById
    RecyclerView rv_news;
    @ViewById
    SwipeRefreshLayout srl_refresh;
    private MainViewModel viewModel;
    @Bean
    MainAdapter adapter;

    @AfterViews
    void initToolbar(){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @AfterViews
    void initAdapter() {
        adapter.registerRenderer(new MatchRenderer(Match.TYPE, this));
        adapter.registerRenderer(new ArticleRenderer(Article.TYPE, this));
        rv_news.setLayoutManager(new LinearLayoutManager(this));
        rv_news.setAdapter(adapter);
    }

    @AfterViews
    void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getVisibleData().observe(this, new Observer<List<NewsItemModel>>() {
            @Override
            public void onChanged(List<NewsItemModel> newsItemModels) {
                adapter.setItems(newsItemModels);
                adapter.notifyDataSetChanged();
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


   /* @ViewById
    SearchView search_view;

    @OptionsItem(R.id.action_search)
    void onSearch() {
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    }*/

    @AfterViews
    void onRefreshListenerInit() {
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    /**
                     *  if connected try downloading data from server
                     * */
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final List<NewsItemModel> updatedData = new ArrayList<>(viewModel.refresh());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    /**
                                     * LiveData can only be updated on ui thread
                                     */
                                    viewModel.onDataUpdated(updatedData);
                                    srl_refresh.setRefreshing(false);
                                }
                            });
                        }
                    }).start();
                } else {
                    /**
                     * else stop animation
                     */
                    srl_refresh.setRefreshing(false);
                }
            }
        });
    }


    @Override
    public void onNewsItemClick(NewsItemModel item) {
        switch (item.getType()) {
            case Match.TYPE:
                //TODO implement MatchActivity
                break;
            case Article.TYPE:
                ArticleActivity_.intent(this).extra("Article", (Article) item).start();
                break;
        }
    }
}
