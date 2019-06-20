package com.sayed.intcoretest.repositories;

import android.util.Log;

import com.sayed.intcoretest.app.AppController;
import com.sayed.intcoretest.model.MoviesResult;
import com.sayed.intcoretest.services.MoviesService;

import java.io.IOException;

import okhttp3.ResponseBody;
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
                    moviesListener.onFail();response.body();

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


    /*
     * Movies interactor interface
     */
    public interface MoviesListener{
        void onSuccess(MoviesResult moviesResult);
        void onFail();
    }
}
