package ru.mycompany.NewsApp;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private Repository repository;

    public MainViewModel() {
        repository = Repository.getInstance();
    }
}
