package com.example.moviesandroid.view;

import android.content.Context;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.moviesandroid.BuildConfig;
import com.example.moviesandroid.R;
import com.example.moviesandroid.core.adapter.SearchAdapter;
import com.example.moviesandroid.core.model.local.Movie;
import com.example.moviesandroid.core.model.remote.Discover;
import com.example.moviesandroid.core.model.remote.DiscoverDBResponse;
import com.example.moviesandroid.core.model.remote.GenresList;
import com.example.moviesandroid.core.model.remote.GenresListDBResponse;
import com.example.moviesandroid.core.model.remote.MovieDBResponse;
import com.example.moviesandroid.core.api.MoviesApi;
import com.example.moviesandroid.core.api.RetrofitInstance;
import com.example.moviesandroid.core.utils.DiscoverToMovie;
import com.example.moviesandroid.view.fragments.MoviesFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ProgressBar progressBar;
    public static ArrayList<Movie> movieList = new ArrayList<>();
    public static int category = 0;
    private NavigationView navigationView;
    public static FragmentTransaction fragmentTransaction;
    public static MoviesFragment moviesFragment = new MoviesFragment();
    public static FragmentManager fragmentManager;
    public static int totalPages;
    public static int totalPagesGenres;
    public static int drawer = 0;
    public static int imageup = 0;
    public static int genreid;
    private static Observable<MovieDBResponse> observableMovie;
    private static Observable<DiscoverDBResponse> observableDB;
    private static Observable<GenresListDBResponse> genresListObservable;
    public static int selected;
    public static ArrayList<Discover> discovers;
    public static ArrayList<GenresList> genresLists;
    public static ArrayList<Discover> search;
    public static ArrayList<Movie> moviesearch;
    public static String queryM;
    private MatrixCursor cursor;
    private static CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        navigationView = findViewById(R.id.nav_view);
        progressBar = findViewById(R.id.progressBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        progressBar.animate().alpha(1).setDuration(500);
        progressBar.setIndeterminate(true);
        navigationView.getMenu().getItem(0).setChecked(true);
        getDataFirst(category, MainActivity.this);
    }

    public static void getDataFirst(int a, final Context context) {
        final MoviesApi moviesApi = RetrofitInstance.getService();
        String ApiKey = BuildConfig.ApiKey;

        if (a == 0) {
            observableMovie = moviesApi.getPopularMoviesWithRx(ApiKey, 1);
        } else {
            observableMovie = moviesApi.getTopRatedMoviesWithRx(ApiKey, 1);
        }
        compositeDisposable.add(
                observableMovie.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<MovieDBResponse>() {
                            @Override
                            public void onNext(MovieDBResponse movieDBResponse) {
                                if (movieDBResponse != null && movieDBResponse.getMovies() != null) {
                                    movieList = (ArrayList<Movie>) movieDBResponse.getMovies();
                                    totalPages = movieDBResponse.getTotalPages();
                                    if (progressBar != null) {
                                        progressBar.setIndeterminate(false);
                                    }
                                    if (fragmentManager.getFragments().isEmpty()) {
                                        fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.add(R.id.frame_layout, moviesFragment).commitAllowingStateLoss();
                                    } else {
                                        fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.replace(R.id.frame_layout, moviesFragment).commitAllowingStateLoss();
                                    }
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(context, "Error!" + e.getMessage().trim(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int fragments = getSupportFragmentManager().getBackStackEntryCount();
            if (fragments == 1) {
                finish();
            } else if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.movies) {
            drawer = 0;
            for (int i = 0; i <= getSupportFragmentManager().getBackStackEntryCount(); i++) {
                getSupportFragmentManager().popBackStack();
            }
            getDataFirst(drawer, MainActivity.this);
        } else if (id == R.id.toprated) {
            drawer = 1;
            for (int i = 0; i <= getSupportFragmentManager().getBackStackEntryCount(); i++) {
                getSupportFragmentManager().popBackStack();
            }
            getDataFirst(drawer, MainActivity.this);
        } else if (id == R.id.genres) {
            drawer = 2;
            for (int i = 0; i <= getSupportFragmentManager().getBackStackEntryCount(); i++) {
                getSupportFragmentManager().popBackStack();
            }
            getGenresList();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getGenresList() {
        final MoviesApi moviesApi = RetrofitInstance.getService();
        String ApiKey = BuildConfig.ApiKey;
        drawer = 2;
        genresListObservable = moviesApi.getGenresList(ApiKey);
        compositeDisposable.add(
                genresListObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<GenresListDBResponse>() {
                            @Override
                            public void onNext(GenresListDBResponse genresListDBResponse) {
                                if (genresListDBResponse != null && genresListDBResponse.getGenresLists() != null) {
                                    genresLists = (ArrayList<GenresList>) genresListDBResponse.getGenresLists();
                                    String[] a = new String[genresLists.size()];
                                    for (int i = 0; i < genresLists.size(); i++) {
                                        a[i] = genresLists.get(i).getName();
                                    }
                                    new MaterialDialog.Builder(MainActivity.this).title("Choose A Category").items(a).itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                        @Override
                                        public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {

                                            selected = which;
                                            genreid = genresLists.get(which).getId();
                                            getFirstGenreData(genreid, MainActivity.this);
                                            return true;
                                        }
                                    }).canceledOnTouchOutside(false).cancelable(false).show();
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(MainActivity.this, "Error!" + e.getMessage().trim(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onComplete() {

                            }
                        })
        );
    }

    public static void getFirstGenreData(int genreid, final Context context) {
        final MoviesApi moviesApi = RetrofitInstance.getService();
        String ApiKey = BuildConfig.ApiKey;
        observableDB = moviesApi.discover(ApiKey, Integer.toString(genreid), false, false, 1);
        compositeDisposable.add(
                observableDB.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<DiscoverDBResponse>() {
                            @Override
                            public void onNext(DiscoverDBResponse discoverDBResponse) {
                                if (discoverDBResponse != null && discoverDBResponse.getResults() != null) {
                                    discovers = (ArrayList<Discover>) discoverDBResponse.getResults();
                                    totalPagesGenres = discoverDBResponse.getTotalPages();
                                    DiscoverToMovie discoverToMovie = new DiscoverToMovie(discovers);
                                    movieList = discoverToMovie.getMovies();
                                    if (progressBar != null) {
                                        progressBar.setIndeterminate(false);
                                    }
                                    fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.add(R.id.frame_layout, moviesFragment).commitAllowingStateLoss();
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(context, "Error!" + e.getMessage().trim(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onComplete() {

                            }
                        })
        );
    }


    public void search(final SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                for (int i = 0; i <= getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    getSupportFragmentManager().popBackStack();
                }
                if (!query.isEmpty()) {
                    searchView.setQuery("", false);
                    searchView.clearFocus();
                    searchView.setIconified(true);
                    final MoviesApi moviesApi = RetrofitInstance.getService();
                    final String ApiKey = BuildConfig.ApiKey;
                    queryM = query;
                    drawer = 3;
                    observableDB = moviesApi.search(ApiKey, false, query);
                    compositeDisposable.add(observableDB
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<DiscoverDBResponse>() {
                                @Override
                                public void onNext(DiscoverDBResponse discoverDBResponse) {
                                    if (discoverDBResponse != null && discoverDBResponse.getResults() != null) {
                                        discovers = (ArrayList<Discover>) discoverDBResponse.getResults();
                                        totalPagesGenres = discoverDBResponse.getTotalPages();
                                        DiscoverToMovie discoverToMovie = new DiscoverToMovie(discovers);
                                        movieList = discoverToMovie.getMovies();
                                        if (progressBar != null) {
                                            progressBar.setIndeterminate(false);
                                        }
                                        fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.add(R.id.frame_layout, moviesFragment).commitAllowingStateLoss();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onComplete() {

                                }
                            }));
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final MoviesApi moviesApi = RetrofitInstance.getService();
                final String ApiKey = BuildConfig.ApiKey;
                queryM = newText;
                drawer = 3;
                observableDB = moviesApi.search(ApiKey, false, queryM);
                compositeDisposable.add(observableDB
                        //  .debounce(400,TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<DiscoverDBResponse>() {
                            @Override
                            public void onNext(DiscoverDBResponse discoverDBResponse) {
                                if (discoverDBResponse != null && discoverDBResponse.getResults() != null) {
                                    search = (ArrayList<Discover>) discoverDBResponse.getResults();
                                    DiscoverToMovie discoverToMovie = new DiscoverToMovie(search);
                                    moviesearch = discoverToMovie.getMovies();
                                    String[] a = new String[moviesearch.size()];
                                    for (int i = 0; i < a.length; i++) {
                                        a[i] = moviesearch.get(i).getTitle();
                                    }
                                    ArrayAdapter<String> Adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.search_list, a);
                                    String[] columnNames = {"_id", "text"};
                                    cursor = new MatrixCursor(columnNames);
                                    String[] temp = new String[2];
                                    int id = 0;
                                    for (String item : a) {
                                        temp[0] = Integer.toString(id++);
                                        temp[1] = item;
                                        cursor.addRow(temp);

                                    }
                                    SearchAdapter searchAdapter = new SearchAdapter(MainActivity.this, cursor, true, searchView, moviesearch);
                                    searchView.setSuggestionsAdapter(searchAdapter);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        search(searchView);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
