package com.sayed.intcoretest.presenter;

import com.sayed.intcoretest.model.MoviesResult;
import com.sayed.intcoretest.repositories.MoviesInteractor;
import com.sayed.intcoretest.ui.home.MoviesView;

public class MoviesPresenter implements MoviesInteractor.MoviesListener {

    //dec Data
    private MoviesView moviesView;
    private MoviesInteractor moviesInteractor;

    public MoviesPresenter(MoviesView moviesView, MoviesInteractor moviesInteractor) {
        this.moviesView = moviesView;
        this.moviesInteractor = moviesInteractor;
    }

    //get movies
    public void getMovies(int page){
        moviesView.showLoading();
        moviesInteractor.getMovies(page,this);
    }

    /**
     * Callback Methods to handle get movies results
     */
    @Override
    public void onSuccess(MoviesResult moviesResult) {
        if (moviesResult==null) return;
        moviesView.addItems(moviesResult.getResults(),moviesResult.getPage(),moviesResult.getTotal_pages());
    }

    @Override
    public void onFail() {
        moviesView.onFail();
    }
}
