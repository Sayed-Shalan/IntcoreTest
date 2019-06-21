package com.sayed.intcoretest.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.sayed.intcoretest.app.AppController;
import com.sayed.intcoretest.model.Movie;
import com.sayed.intcoretest.model.MoviesResult;
import com.sayed.intcoretest.offline_db.MovieOfflineProvider;
import com.sayed.intcoretest.services.MoviesService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesInteractor {

    //get movies
    public void getMovies(int page,MoviesListener moviesListener){
        MoviesService moviesService=AppController.getRetrofit().create(MoviesService.class);
        moviesService.getMovies("a3bbc8ba7c5d94e0cd5d7cb994af8c62",page).enqueue(new Callback<MoviesResult>() {
            @Override
            public void onResponse(Call<MoviesResult> call, Response<MoviesResult> response) {
                if (response.isSuccessful()) {
                    Log.e("On Response","Succ");
                    moviesListener.onSuccess(response.body());

                } else {
                    Log.e("On Response","Err");
                    moviesListener.onFail();

                }
            }

            @Override
            public void onFailure(Call<MoviesResult> call, Throwable t) {
                moviesListener.onFail();
            }
        });
    }

    //insert new movie to offline db
    public void insertNewMovieToOfflineDB(Movie movie, Context context){
        ContentValues contentValues=new ContentValues();
        contentValues.put(MovieOfflineProvider.MOVIE__ID,movie.getId());
        contentValues.put(MovieOfflineProvider.MOVIE_TITLE,movie.getTitle());
        contentValues.put(MovieOfflineProvider.POSTER_PATH,movie.getPoster_path());
        context.getContentResolver().insert(MovieOfflineProvider.CONTENT_URI,contentValues);
    }

    //delete movie from offline db
    public void deleteMovieFromOfflineDB(int movie_id,Context context){
        context.getContentResolver().delete(Uri.parse(MovieOfflineProvider.URL.concat("/").concat(String.valueOf(movie_id))),null,null);
    }


    /*
     * Movies interactor interface
     */
    public interface MoviesListener{
        void onSuccess(MoviesResult moviesResult);
        void onFail();
    }
}
