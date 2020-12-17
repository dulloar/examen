package com.davidulloa.examen.di;

import android.app.Application;

import androidx.room.Room;

import com.davidulloa.examen.api.MovieService;
import com.davidulloa.examen.data.RequestInterceptor;
import com.davidulloa.examen.data.local.MovieRoomDatabase;
import com.davidulloa.examen.data.local.dao.MovieDao;
import com.davidulloa.examen.data.remote.ApiConstants;
import com.davidulloa.examen.util.LiveDataCallAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Singleton
    @Provides
    MovieService provideIncidenciaService(){

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new RequestInterceptor());
        OkHttpClient cliente = okHttpClientBuilder.build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(cliente)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(MovieService.class);
    }



    @Singleton
    @Provides
    MovieRoomDatabase provideDB(Application app){
        return Room.databaseBuilder(app, MovieRoomDatabase.class,"movie_db").build();
    }


    @Singleton
    @Provides
    MovieDao providesFotoResponse(MovieRoomDatabase db){return db.MovieDao();}

}
