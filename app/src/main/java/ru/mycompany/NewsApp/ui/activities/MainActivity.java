package ru.mycompany.NewsApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import ru.mycompany.NewsApp.MainViewModel;
import ru.mycompany.NewsApp.R;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    RecyclerView rv_news;
    private MainViewModel viewModel;

    @AfterViews
    void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

}
