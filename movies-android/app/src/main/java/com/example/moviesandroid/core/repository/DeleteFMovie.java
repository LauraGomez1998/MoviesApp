package com.example.moviesandroid.core.repository;

import android.os.AsyncTask;

import com.example.moviesandroid.core.database.MoviesDAO;
import com.example.moviesandroid.core.model.local.Movie;

public class DeleteFMovie extends AsyncTask<Movie, Void, Void> {
    private MoviesDAO moviesDAO;

    public DeleteFMovie(MoviesDAO moviesDAO) {
        this.moviesDAO = moviesDAO;
    }

    @Override
    protected Void doInBackground(Movie... movies) {
        moviesDAO.deleteFMovie(movies[0]);
        return null;
    }
}
