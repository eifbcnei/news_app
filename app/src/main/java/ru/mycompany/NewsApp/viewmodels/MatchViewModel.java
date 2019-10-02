package ru.mycompany.NewsApp.viewmodels;

import androidx.lifecycle.ViewModel;

import ru.mycompany.NewsApp.models.Player;
import ru.mycompany.NewsApp.network.Repository;

public class MatchViewModel extends ViewModel {
    private static final Repository repository;

    static {
        repository = Repository.getInstance();
    }

    public Player loadPlayer(String playerId) {
        return repository.loadPlayer(playerId);
    }
}
