package com.davidulloa.examen.di;

import android.app.Application;

import com.davidulloa.examen.MovieApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class,
                        AppModule.class
                        , MainActivityModule.class
                        })
public interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(MovieApp movieApp);
}
