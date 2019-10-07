package com.example.moviesandroid.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.moviesandroid.R;
import com.example.moviesandroid.core.model.local.Movie;
import com.example.moviesandroid.core.viewmodel.MovieViewModel;
import com.example.moviesandroid.databinding.ActivityMoviesInfoBinding;
import com.example.moviesandroid.view.MainActivity;
import com.google.android.material.snackbar.Snackbar;

public class MoviesInfoFragment extends AppCompatActivity {
    private Movie movie;
    private Boolean bool;
    private ActivityMoviesInfoBinding activityMoviesInfoBinding;
    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_info);
        View parentlayout = findViewById(android.R.id.content);
        movieViewModel = ViewModelProviders.of(MoviesInfoFragment.this).get(MovieViewModel.class);
        activityMoviesInfoBinding = DataBindingUtil.setContentView(MoviesInfoFragment.this, R.layout.activity_movies_info);
        Intent i = getIntent();
        if (i.hasExtra("movie")) {
            movie = i.getParcelableExtra("movie");
            bool = i.getBooleanExtra("boolean", false);
            if (MainActivity.imageup <= 2) {
                Snackbar.make(parentlayout, "Swipe up for detail!", Snackbar.LENGTH_SHORT).show();
                MainActivity.imageup++;
            }
            activityMoviesInfoBinding.setMovie(movie);
        }
    }

}
