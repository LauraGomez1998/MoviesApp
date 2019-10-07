package com.example.moviesandroid.core.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.moviesandroid.core.api.MoviesApi;
import com.example.moviesandroid.core.api.RetrofitInstance;
import com.example.moviesandroid.core.model.local.Movie;
import com.example.moviesandroid.core.repository.MovieRepository;
import com.example.moviesandroid.core.utils.paginationlibrary.DataSource;
import com.example.moviesandroid.core.utils.paginationlibrary.DataSourceFactory;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;

    private LiveData<DataSource> dataSourceLiveData;

    private Executor executor;

    private LiveData<PagedList<Movie>> pagedListLiveData;


    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        MoviesApi moviesApi = RetrofitInstance.getService();
        DataSourceFactory dataSourceFactory = new DataSourceFactory(moviesApi, application);
        dataSourceLiveData = dataSourceFactory.getDataSourceMutableLiveData();
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build();
        executor = Executors.newFixedThreadPool(5);
        pagedListLiveData = (new LivePagedListBuilder<Long, Movie>(dataSourceFactory, config)).setFetchExecutor(executor).build();
    }


    public LiveData<PagedList<Movie>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    public Movie getMovie(String id) {
        return movieRepository.getMovie(id);
    }

    public LiveData<List<Movie>> getAllMovies() {
        return movieRepository.getAllFMovies();
    }

    public void AddMovie(Movie movie) {

        movieRepository.AddMovie(movie);
    }

    public void DeleteMovie(Movie movie) {
        movieRepository.DeleteMovie(movie);
    }

}
