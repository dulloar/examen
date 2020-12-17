package com.davidulloa.examen.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.davidulloa.examen.data.local.models.Movie;
import com.davidulloa.examen.data.network.Resource;
import com.davidulloa.examen.data.remote.repository.MovieRepository;

import java.util.List;

import javax.inject.Inject;

public class MovieViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private final LiveData<Resource<List<Movie>>> movies;

    @Inject
    public MovieViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.movies = movieRepository.gteMovies();
    }

    public LiveData<Resource<List<Movie>>> getMovies(){
        return this.movies;
    }
}
