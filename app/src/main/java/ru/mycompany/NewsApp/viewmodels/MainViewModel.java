package ru.mycompany.NewsApp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.mycompany.NewsApp.models.Article;
import ru.mycompany.NewsApp.models.NewsItemModel;
import ru.mycompany.NewsApp.network.Repository;

public class MainViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<String> query = new MutableLiveData<>();
    private MutableLiveData<List<NewsItemModel>> visibleData = new MutableLiveData<>();
    private MutableLiveData<List<NewsItemModel>> allData = new MutableLiveData<>();

    public MainViewModel() {
        repository = Repository.getInstance();
        query.setValue("");
        allData.setValue(refresh());
        visibleData.setValue(allData.getValue());
    }

    public LiveData<List<NewsItemModel>> getVisibleData() {
        MediatorLiveData<List<NewsItemModel>> mediator = new MediatorLiveData<>();
        mediator.addSource(query, new Observer<String>() {
            @Override
            public void onChanged(final String s) {
                /**
                 * when search query is blank
                 * show all articles and matches
                 * else
                 * show articles which title/description/source
                 * containing query as substring
                 */
                if (s.trim().equals("")) {
                    List<NewsItemModel> items = new ArrayList<>();
                    for (NewsItemModel item : allData.getValue()) {
                        if (item instanceof Article
                                && (
                                ((Article) item).getTitle().contains(s)
                                        || ((Article) item).getSource().contains(s)
                                        || ((Article) item).getDescription().contains(s))
                        ) {
                            items.add(item);
                        }
                    }
                    visibleData.setValue(items);
                } else {
                    visibleData.setValue(allData.getValue());
                }
            }
        });
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

    public void onSearchRequested(String query) {
        this.query.setValue(query);
    }
}
