package com.sayed.intcoretest.ui.home;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sayed.intcoretest.R;
import com.sayed.intcoretest.databinding.FragmentFavouritesBinding;
import com.sayed.intcoretest.managers.BroadcastManager;
import com.sayed.intcoretest.model.Movie;
import com.sayed.intcoretest.presenter.FavouritePresenter;
import com.sayed.intcoretest.repositories.FavouritesInteractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavouritesFragment extends Fragment implements FavouriteView{

    //dec data
    FragmentFavouritesBinding binding;
    AdapterMovies adapterMovies;
    List<Movie> items;
    FavouritePresenter favouritePresenter;

    //on fav click
    AdapterMovies.MoviesCallback moviesCallback= (movie, position) -> {
         favouritePresenter.deleteMovieFromOfflineDB(movie.getId());
         removeMovieFromList(movie);
        sendMovieBroadCast(movie); //to notify movies frag,ent with updates


    };

    //register receiver if movie fav icon is clicked on movie fragment
    private BroadcastReceiver mMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Movie movie=intent.getParcelableExtra(BroadcastManager.KEY_MOVIE_OBJECT);
            if (movie!=null){
                if (movie.isFavourite()) adapterMovies.add(movie);
                else removeMovieFromList(getMovieById(movie));
            }
        }
    };



    /**
     * ON CREATE
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_favourites, container, false);
        return binding.getRoot();
    }

    /**
     * ON VIEW CREATED
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(); //init Data
    }

    /**
     * ON PAUSE
     */
    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).unregisterReceiver(mMessageReceiver);

    }



    //Init Data
    private void initData() {
        items=new ArrayList<>();
        setupAdapter();
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).registerReceiver(mMessageReceiver,
                new IntentFilter(BroadcastManager.ACTION_MOVIE_FAV_CLICK));
        favouritePresenter=new FavouritePresenter(this,new FavouritesInteractor(),getActivity());
        favouritePresenter.getFavMoviesList();
    }

    //setup adapter
    private void setupAdapter() {
        items.clear();
        adapterMovies=new AdapterMovies(moviesCallback,items);
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(),2));
        binding.recycler.setAdapter(adapterMovies);
        adapterMovies.notifyDataSetChanged();
    }

    //remove movie from list
    private void removeMovieFromList(Movie movie){
        items.remove(movie);
        adapterMovies.refresh(items);
        checkFavList();
    }

    //if list empty
    private void checkFavList() {
        binding.tvNoMovies.setVisibility(items.size()==0?View.VISIBLE:View.INVISIBLE);
    }

    //get movie full object
    private Movie getMovieById(Movie movie) {
        for (Movie object:items){
            if (object.getId()==movie.getId()) return object;
        }
        return movie;
    }

    //Send Movie Broadcast
    private void sendMovieBroadCast(Movie movie) {
        Intent intent = new Intent(BroadcastManager.ACTION_MOVIE_REMOVED);
        intent.putExtra(BroadcastManager.KEY_MOVIE_DELETED, movie);
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).sendBroadcast(intent);
    }

    /**
     * Fav View Callback methods
     */
    @Override
    public void addItems(List<Movie> moviesList) {
        binding.tvNoMovies.setVisibility(View.INVISIBLE);
        adapterMovies.add(moviesList);
    }

    @Override
    public void showNoData() {
        binding.tvNoMovies.setVisibility(View.VISIBLE);
    }


}
