package com.sayed.intcoretest.presenter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.sayed.intcoretest.model.Movie;
import com.sayed.intcoretest.model.MoviesResult;
import com.sayed.intcoretest.offline_db.MovieOfflineProvider;
import com.sayed.intcoretest.repositories.MoviesInteractor;
import com.sayed.intcoretest.ui.home.MoviesView;

import java.util.List;

public class MoviesPresenter implements MoviesInteractor.MoviesListener {

    //dec Data
    private MoviesView moviesView;
    private MoviesInteractor moviesInteractor;
    private Context context;

    public MoviesPresenter(MoviesView moviesView, MoviesInteractor moviesInteractor,Context context) {
        this.moviesView = moviesView;
        this.moviesInteractor = moviesInteractor;
        this.context=context;
    }

    //get movies
    public void getMovies(int page){
        moviesView.showLoading();
        moviesInteractor.getMovies(page,this);
    }

    //add movie to offline db
    public void addMovieToOfflineDB(Movie movie){
        moviesInteractor.insertNewMovieToOfflineDB(movie,context);
    }

    //delete a movie from offline db
    public void deleteMovieFromOfflineDB(int movie_id){
        moviesInteractor.deleteMovieFromOfflineDB(movie_id,context);
    }

    /**
     * Callback Methods to handle get movies results
     */
    @Override
    public void onSuccess(MoviesResult moviesResult) {
        if (moviesResult==null) return;
        checkIfFavourite(moviesResult.getResults(),moviesResult.getPage(),moviesResult.getTotal_pages());

    }

    //check if movie is favourite
    private void checkIfFavourite(List<Movie> results, int page, int total_pages) {
        for (Movie movie:results){
            movie.setFavourite(hitDbForSelectMovie(movie.getId()));
        }
        moviesView.addItems(results,page,total_pages);
    }

    //does movie exists in offline db
    private boolean hitDbForSelectMovie(int id) {
        Cursor c = context.getContentResolver().
                query(Uri.parse(MovieOfflineProvider.URL.concat("/").concat(String.valueOf(id))), MovieOfflineProvider.PROJECTION, null, null, null);
        return c!=null&&c.moveToFirst();

    }

    @Override
    public void onFail() {
        moviesView.onFail();
    }
}
