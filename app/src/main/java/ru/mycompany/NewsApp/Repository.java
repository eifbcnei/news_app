package ru.mycompany.NewsApp;

public class Repository {
    private final static Repository instance = new Repository();

    private Repository() {
    }

    public static Repository getInstance() {
        return instance;
    }
}
