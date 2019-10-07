package com.example.moviesandroid.core.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.moviesandroid.core.database.MovieDb;
import com.example.moviesandroid.core.database.MoviesDAO;
import com.example.moviesandroid.core.model.local.Movie;

import java.util.List;

public class MovieRepository {

    private MoviesDAO moviesDAO;


    public MovieRepository(Application application) {
        MovieDb movieDb = MovieDb.getInstance(application);
        moviesDAO = movieDb.getFDAO();
    }

    public LiveData<List<Movie>> getAllFMovies() {
        return moviesDAO.getAllFMovies();
    }

    public Movie getMovie(String id) {
        return moviesDAO.getMovie(id);
    }

    public void AddMovie(Movie movie) {
        new AddFMovie(moviesDAO).execute(movie);
    }

    public void DeleteMovie(Movie movie) {
        new DeleteFMovie(moviesDAO).execute(movie);
    }

}
