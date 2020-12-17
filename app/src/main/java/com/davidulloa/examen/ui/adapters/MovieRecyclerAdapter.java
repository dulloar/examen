package com.davidulloa.examen.ui.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;

import com.davidulloa.examen.R;
import com.davidulloa.examen.data.local.models.Movie;
import com.davidulloa.examen.databinding.MovieListBinding;
import com.davidulloa.examen.ui.common.DataBoundListAdapter;

import java.util.List;

public class MovieRecyclerAdapter extends DataBoundListAdapter<Movie, MovieListBinding> {


    private final androidx.databinding.DataBindingComponent dataBindingComponent;
    private final MovieOnclick callback;

    public MovieRecyclerAdapter(DataBindingComponent dataBindingComponent, MovieOnclick callback) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
    }

    @Override
    protected MovieListBinding createBinding(ViewGroup parent) {
        MovieListBinding movieListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                                                , R.layout.movie_list,parent,false,dataBindingComponent);
        movieListBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie movie = movieListBinding.getMovie();
                if(movie != null){
                    callback.onClickMovie(movie);
                }
            }
        });
        return movieListBinding;
    }

    @Override
    protected void bind(MovieListBinding binding, Movie item) {
        binding.setMovie(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected boolean areItemsTheSame(Movie oldItem, Movie newItem) {
        return oldItem == newItem;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected boolean areContentsTheSame(Movie oldItem, Movie newItem) {
        return oldItem.getId() == newItem.getId() && oldItem.getPosterPath() == newItem.getPosterPath();
    }

    public interface MovieOnclick{
        void onClickMovie(Movie movie);
    }
}
