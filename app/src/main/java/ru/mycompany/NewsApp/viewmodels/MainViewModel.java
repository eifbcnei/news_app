package ru.mycompany.NewsApp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ru.mycompany.NewsApp.App;
import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.models.NewsItemModel;
import ru.mycompany.NewsApp.network.Repository;

public class MainViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<CopyOnWriteArrayList<NewsItemModel>> visibleData = new MutableLiveData<>();
    private MutableLiveData<List<NewsItemModel>> allData = new MutableLiveData<>();
    private MutableLiveData<List<String>> tags = new MutableLiveData<>();

    public MainViewModel() {
        repository = Repository.getInstance();
        allData.setValue(refreshPosts());
        tags.setValue(refreshTags());
        visibleData.setValue(new CopyOnWriteArrayList<>(allData.getValue()));
    }

    public LiveData<List<String>> getTags() {
        return tags;
    }

    public LiveData<CopyOnWriteArrayList<NewsItemModel>> getVisibleData() {
        return visibleData;
    }

    public List<NewsItemModel> refreshPosts() {
        List<NewsItemModel> data;
        try {
            data = repository.getAllPosts();
        } catch (Exception e) {
            e.printStackTrace();
            data = new ArrayList<>();
        }
        return data;
    }

    public void onDataUpdated(List<NewsItemModel> updatedData,List<String> updatedTags) {
        allData.setValue(updatedData);
        visibleData.setValue(new CopyOnWriteArrayList<>(updatedData));
        tags.setValue(updatedTags);
    }

    /**
     * when search query is not blank
     * show all articles and matches
     * where query is ignore-case-substring of some tag
     * else
     * show all data
     */
    public void onSearchRequested(String query) {
        if (!query.trim().equals("")) {
            List<NewsItemModel> items = new ArrayList<>();
            for (NewsItemModel item : allData.getValue()) {
                for (String tag : item.getTags()) {
                    if (tag.toLowerCase().contains(query.toLowerCase())) {
                        items.add(item);
                        break;
                    }
                }
            }
            visibleData.setValue(new CopyOnWriteArrayList<>(items));
        } else {
            visibleData.setValue(new CopyOnWriteArrayList<>((allData).getValue()));
        }
    }

    public void filter(String tag) {
        if (tag.equals(App.appContext().getString(R.string.all_news))) {
            visibleData.setValue(new CopyOnWriteArrayList<>(allData.getValue()));
            return;
        }

        List<NewsItemModel> visible = new ArrayList<>();

        for (NewsItemModel itemModel : allData.getValue()) {
            if (itemModel.getTags().contains(tag)) {
                visible.add(itemModel);
            }
        }

        visibleData.setValue(new CopyOnWriteArrayList<>(visible));
    }

    public List<String> refreshTags() {
        try {
            return repository.getTags();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
