package com.davidulloa.examen.data.remote.response;

import com.davidulloa.examen.data.local.models.Movie;

import java.util.List;

public class MovieResponse {
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
