package com.davidulloa.examen.api;

import androidx.lifecycle.LiveData;

import com.davidulloa.examen.data.local.models.Movie;
import com.davidulloa.examen.data.remote.response.MovieResponse;

import java.util.List;

import retrofit2.http.GET;

public interface MovieService {

    @GET("movie/popular")
    LiveData<ApiResponse<MovieResponse>> loadMovies();
}
