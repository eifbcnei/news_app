package ru.mycompany.NewsApp.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.mycompany.NewsApp.models.Article;
import ru.mycompany.NewsApp.models.NewsItemModel;
import ru.mycompany.NewsApp.network.Repository;

public class MainViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<List<NewsItemModel>> visibleData = new MutableLiveData<>();
    private MutableLiveData<List<NewsItemModel>> allData = new MutableLiveData<>();

    public MainViewModel() {
        repository = Repository.getInstance();
        allData.setValue(refresh());
        visibleData.setValue(allData.getValue());
    }

    public LiveData<List<NewsItemModel>> getVisibleData() {
        return visibleData;
    }

    public List<NewsItemModel> refresh() {
        List<NewsItemModel> data;
        try {
            data = repository.getAllPosts();
        } catch (Exception e) {
            data = new ArrayList<>();
        }
        return data;
    }

    public void onDataUpdated(List<NewsItemModel> updatedData) {
        allData.setValue(updatedData);
    }

    /**
     * when search query is blank
     * show all articles and matches
     * else
     * show articles which title/description/source
     * containing query as substring
     */
    public void onSearchRequested(String query) {
        if (!query.trim().equals("")) {
            List<NewsItemModel> items = new ArrayList<>();
            for (NewsItemModel item : allData.getValue()) {
                if (item instanceof Article
                        && (
                        ((Article) item).getTitle().contains(query)
                                || ((Article) item).getSource().contains(query)
                                || ((Article) item).getDescription().contains(query))
                ) {
                    items.add(item);
                }
            }
            visibleData.setValue(items);
        } else {
            visibleData.setValue(allData.getValue());
        }
    }
}
