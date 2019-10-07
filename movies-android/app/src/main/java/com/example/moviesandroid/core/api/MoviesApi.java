package com.example.moviesandroid.core.api;

import com.example.moviesandroid.core.model.remote.DiscoverDBResponse;
import com.example.moviesandroid.core.model.remote.GenresListDBResponse;
import com.example.moviesandroid.core.model.remote.MovieDBResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApi {

    @GET("movie/popular")
    Observable<MovieDBResponse> getPopularMoviesWithRx(@Query("api_key") String apiKey, @Query("page") int pageIndex);

    @GET("movie/top_rated")
    Observable<MovieDBResponse> getTopRatedMoviesWithRx(@Query("api_key") String apiKey, @Query("page") int pageIndex);

    @GET("genre/movie/list")
    Observable<GenresListDBResponse> getGenresList(@Query("api_key") String apiKey);

    @GET("discover/movie")
    Observable<DiscoverDBResponse> discover(@Query("api_key") String apiKey, @Query("with_genres") String genres, @Query("include_adult") Boolean adult, @Query("include_video") Boolean video, @Query("page") int pageIndex);

    @GET("search/movie")
    Observable<DiscoverDBResponse> search(@Query("api_key") String apiKey, @Query("include_adult") Boolean adult, @Query("query") String query);
}
