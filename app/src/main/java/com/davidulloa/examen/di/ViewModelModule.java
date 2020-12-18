package com.davidulloa.examen.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.davidulloa.examen.ui.viewmodel.ImageViewModel;
import com.davidulloa.examen.ui.viewmodel.MovieViewModel;
import com.davidulloa.examen.viewmodel.MovieViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel.class)
    abstract ViewModel bindMovieViewModel(MovieViewModel movieViewModel);

 /*   @SuppressWarnings("unused")
    @Binds
    @IntoMap
    @ViewModelKey(ImageViewModel.class)
    abstract ViewModel bindImageViewModel(ImageViewModel imageViewModel);*/

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(MovieViewModelFactory factory);
}
