package com.example.moviesandroid.core.utils.paginationlibrary;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.moviesandroid.BuildConfig;
import com.example.moviesandroid.core.model.local.Movie;
import com.example.moviesandroid.core.api.MoviesApi;
import com.example.moviesandroid.core.api.RetrofitInstance;

/* *****

This class is the data source for the paging library for this project. But paging isn't implemented by this class in this project. Hence, this code isn't used in this project.
   But it can be used as an alternative.


*****    */

public class DataSource extends PageKeyedDataSource<Long, Movie> {
    private MoviesApi moviesApi;
    private Application application;
    private String ApiKey = BuildConfig.ApiKey;


    public DataSource(MoviesApi moviesApi, Application application) {
        this.moviesApi = moviesApi;
        this.application = application;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Movie> callback) {
        moviesApi = RetrofitInstance.getService();
       /* Call<MovieDBResponse> call=moviesApi.getPopularMovies(ApiKey,1);
        call.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {
                MovieDBResponse movieDBResponse = response.body();
                ArrayList<Movie> moviesFragment = new ArrayList<>();
                if (movieDBResponse != null & movieDBResponse.getMovies() != null) {
                    moviesFragment = (ArrayList<Movie>) movieDBResponse.getMovies();
                    callback.onResult(moviesFragment, null, (long) 2);
                }
            }
            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {

            }
        });*/

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Movie> callback) {
        moviesApi = RetrofitInstance.getService();
/*     Call<MovieDBResponse> call=moviesApi.getPopularMovies(ApiKey, Math.toIntExact(params.key));
        call.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {
                MovieDBResponse movieDBResponse=response.body();
                ArrayList<Movie> moviesFragment=new ArrayList<>();
                if(movieDBResponse!=null&&movieDBResponse.getMovies()!=null) {
                    moviesFragment = (ArrayList<Movie>) movieDBResponse.getMovies();
                    callback.onResult(moviesFragment, params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {

            }
        });*/

    }
}
