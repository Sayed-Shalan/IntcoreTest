package com.sayed.intcoretest.presenter;

import android.content.Context;

import com.sayed.intcoretest.model.Movie;
import com.sayed.intcoretest.repositories.FavouritesInteractor;
import com.sayed.intcoretest.ui.home.FavouriteView;

import java.util.List;

public class FavouritePresenter implements FavouritesInteractor.FavListener {

    //dec data
    private FavouriteView favouriteView;
    private FavouritesInteractor favouritesInteractor;
    private Context context;

    //constructor
    public FavouritePresenter(FavouriteView favouriteView, FavouritesInteractor favouritesInteractor, Context context) {
        this.favouriteView = favouriteView;
        this.favouritesInteractor = favouritesInteractor;
        this.context = context;
    }

    //get list from offline
    public void getFavMoviesList(){
        favouritesInteractor.getFavMovies(context,this);
    }

    //delete a movie from offline db
    public void deleteMovieFromOfflineDB(int movie_id){
        favouritesInteractor.deleteMovieFromOfflineDB(movie_id,context);
    }

    @Override
    public void onSuccess(List<Movie> moviesResult) {
        favouriteView.addItems(moviesResult);
    }

    @Override
    public void noData() {
        favouriteView.showNoData();
    }
}
