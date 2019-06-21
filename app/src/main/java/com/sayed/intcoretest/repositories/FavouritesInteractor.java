package com.sayed.intcoretest.repositories;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.sayed.intcoretest.model.Movie;
import com.sayed.intcoretest.offline_db.MovieOfflineProvider;

import java.util.ArrayList;
import java.util.List;

public class FavouritesInteractor {

    //get fav movies from offline db
    public void getFavMovies(Context context,FavListener favListener){
        Cursor c = context.
                getContentResolver().
                query(MovieOfflineProvider.CONTENT_URI, MovieOfflineProvider.PROJECTION, null, null, null); // get all movies
        if (c!=null&&c.moveToFirst()){
            List<Movie> movieList=new ArrayList<>();
            do {
                Movie movie=new Movie(true);
                movie.setId(c.getInt(c.getColumnIndex(MovieOfflineProvider.MOVIE__ID)));
                movie.setTitle(c.getString(c.getColumnIndex(MovieOfflineProvider.MOVIE_TITLE)));
                movie.setPoster_path(c.getString(c.getColumnIndex(MovieOfflineProvider.POSTER_PATH)));
                movieList.add(movie);
            }while (c.moveToNext());

            favListener.onSuccess(movieList);
        }else favListener.noData();

    }

    //delete movie from offline db
    public void deleteMovieFromOfflineDB(int movie_id,Context context){
        context.getContentResolver().delete(Uri.parse(MovieOfflineProvider.URL.concat("/").concat(String.valueOf(movie_id))),null,null);
    }

    /*
     * fav interactor interface
     */
    public interface FavListener{
        void onSuccess(List<Movie> moviesResult);
        void noData();
    }
}
