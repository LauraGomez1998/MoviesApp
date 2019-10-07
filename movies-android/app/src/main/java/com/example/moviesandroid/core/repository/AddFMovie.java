package com.example.moviesandroid.core.repository;

import android.os.AsyncTask;

import com.example.moviesandroid.core.database.MoviesDAO;
import com.example.moviesandroid.core.model.local.Movie;

public class AddFMovie extends AsyncTask<Movie, Void, Void> {
    private MoviesDAO moviesDAO;

    public AddFMovie(MoviesDAO moviesDAO) {
        this.moviesDAO = moviesDAO;
    }

    @Override
    protected Void doInBackground(Movie... movies) {
        moviesDAO.insertFMovie(movies[0]);
        return null;
    }
}
