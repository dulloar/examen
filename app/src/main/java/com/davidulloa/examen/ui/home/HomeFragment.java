package com.davidulloa.examen.ui.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidulloa.examen.R;
import com.davidulloa.examen.binding.FragmentDataBindingComponent;
import com.davidulloa.examen.data.local.models.Movie;
import com.davidulloa.examen.databinding.FragmentHomeBinding;
import com.davidulloa.examen.di.Injectable;
import com.davidulloa.examen.ui.adapters.MovieRecyclerAdapter;
import com.davidulloa.examen.ui.common.NavigationController;
import com.davidulloa.examen.ui.viewmodel.MovieViewModel;
import com.davidulloa.examen.util.AutoClearedValue;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelProvider;

    @Inject
    NavigationController navigationController;

    androidx.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<FragmentHomeBinding> binding;
    AutoClearedValue<MovieRecyclerAdapter> adapter;

    private MovieViewModel movieViewModel;

    private List<Movie> movies;
    private MovieRecyclerAdapter.MovieOnclick movieOnclick;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentHomeBinding fragmentHomeBinding = DataBindingUtil.inflate(inflater
                    ,R.layout.fragment_home, container, false,dataBindingComponent);

        binding = new AutoClearedValue<>(this,fragmentHomeBinding);

        MovieRecyclerAdapter adapter = new MovieRecyclerAdapter(dataBindingComponent, new MovieRecyclerAdapter.MovieOnclick() {
            @Override
            public void onClickMovie(Movie movie) {

            }
        });

        binding.get().rvMovies.setAdapter(adapter);
        this.adapter = new AutoClearedValue<>(this,adapter);
        // Inflate the layout for this fragment
        return binding.get().getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        movieViewModel = ViewModelProviders.of(getActivity(),viewModelProvider).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(getViewLifecycleOwner(),listResource -> {
            if(listResource != null && listResource.data != null){
                adapter.get().replace(listResource.data);
            } else {
                adapter.get().replace(Collections.emptyList());
            }
        });
    }
}