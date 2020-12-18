package com.davidulloa.examen.data.remote.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.davidulloa.examen.AppExecutors;
import com.davidulloa.examen.api.ApiResponse;
import com.davidulloa.examen.api.MovieService;
import com.davidulloa.examen.data.local.dao.MovieDao;
import com.davidulloa.examen.data.local.models.Movie;
import com.davidulloa.examen.data.network.NetworkBoundResource;
import com.davidulloa.examen.data.network.Resource;
import com.davidulloa.examen.data.remote.response.MovieResponse;
import com.davidulloa.examen.util.RateLimiter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class MovieRepository {
    private MovieDao movieDao;
    private MovieService movieService;
    private AppExecutors appExecutors;

    private RateLimiter<String> movieListRateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    @Inject
    public MovieRepository(MovieDao movieDao, MovieService movieService, AppExecutors appExecutors) {
        this.movieDao = movieDao;
        this.movieService = movieService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<Movie>>> gteMovies(){
        return new NetworkBoundResource<List<Movie>, MovieResponse>(appExecutors){

            @Override
            protected void saveCallResult(@NonNull MovieResponse item) {
                movieDao.saveMovies(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                return data == null || data.isEmpty() || movieListRateLimit.shouldFetch("");
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return movieDao.getMovies();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieResponse>> createCall() {
                return movieService.loadMovies();
            }
        }.getAsLiveData();
    }
}
