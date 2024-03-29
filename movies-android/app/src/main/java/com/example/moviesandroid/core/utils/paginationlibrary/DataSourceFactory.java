package com.example.moviesandroid.core.utils.paginationlibrary;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.moviesandroid.core.api.MoviesApi;

/* ****


 This class is the data source for the paging library for this project. But paging isn't implemented by this class in this project. Hence, this code isn't used in this project.
   But it can be used as an alternative.


**** */
public class DataSourceFactory extends DataSource.Factory {
    private DataSource dataSource;
    private Application application;
    private MoviesApi moviesApi;
    private MutableLiveData<DataSource> dataSourceMutableLiveData;

    public DataSourceFactory(MoviesApi moviesApi, Application application) {
        this.application = application;
        this.moviesApi = moviesApi;
    }

    public DataSource create() {
        dataSource = new DataSource(moviesApi, application);
        dataSourceMutableLiveData.postValue(dataSource);
        return null;
    }

    public MutableLiveData<DataSource> getDataSourceMutableLiveData() {
        return dataSourceMutableLiveData;
    }
}
