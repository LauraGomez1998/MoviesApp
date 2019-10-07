package com.example.moviesandroid.core.database.dao;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.moviesandroid.core.LiveDataTestUtil;
import com.example.moviesandroid.core.database.MovieDbTest;
import com.example.moviesandroid.core.model.local.Movie;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class MovieDaoTest extends MovieDbTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void insertAndLoad() throws InterruptedException {
        Movie movie = new Movie(1, 1, false, 1.1, "Batman", 10.0, "/Batman.jpg", "Castellano", "Batman V", "/path", false, "", "");
        db.getFDAO().insertFMovie(movie);

        List<Movie> loaded = LiveDataTestUtil.getValue(db.getFDAO().getAllFMovies());
        assertEquals(1, loaded.size());
        assertEquals("Batman", loaded.get(0).getTitle());
        assertEquals("/Batman.jpg", loaded.get(0).getPosterPath());
        assertEquals("Castellano", loaded.get(0).getOriginalLanguage());
    }
}
