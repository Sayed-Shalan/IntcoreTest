package com.sayed.intcoretest.ui.home;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sayed.intcoretest.R;
import com.sayed.intcoretest.databinding.FragmentMoviesBinding;
import com.sayed.intcoretest.model.Movie;
import com.sayed.intcoretest.presenter.MoviesPresenter;
import com.sayed.intcoretest.repositories.MoviesInteractor;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment implements MoviesView{

    //Dec Data
    FragmentMoviesBinding binding;
    AdapterMovies adapterMovies;
    List<Movie> items;
    int page=0;
    int total_pages=1;
    MoviesPresenter moviesPresenter;

    //Swipe refresh Listener
    SwipeRefreshLayout.OnRefreshListener onRefreshListener= () -> {
        adapterMovies.clearItems();
        moviesPresenter.getMovies(1);
    };

    //on fav click
    AdapterMovies.MoviesCallback moviesCallback= (movie, position) -> {

    };

    /**
     * ON CREATE
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_movies, container, false);
        return binding.getRoot();
    }

    /**
     * ON VIEW CREATED
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();//init Data
    }

    //init Data
    private void initData() {
        items=new ArrayList<>();
        binding.swipeRefresh.setOnRefreshListener(onRefreshListener);
        setupAdapter();
        moviesPresenter=new MoviesPresenter(this,new MoviesInteractor());
        moviesPresenter.getMovies(1);
    }

    //setup adapter
    private void setupAdapter() {
        items.clear();
        adapterMovies=new AdapterMovies(moviesCallback,items);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        binding.recyclerView.setAdapter(adapterMovies);
        adapterMovies.notifyDataSetChanged();
    }

    /**
     * handle movies view
     */
    @Override
    public void addItems(List<Movie> moviesList, int pageNum, int totalPages) {
        total_pages=totalPages;
        page=pageNum;
        binding.swipeRefresh.setRefreshing(false);
        adapterMovies.add(moviesList);
    }

    @Override
    public void onFail() {
        binding.swipeRefresh.setRefreshing(false);
//        showError(getResources().getString(R.string.error_occurred));
        Toast.makeText(getActivity(),getResources().getString(R.string.error_occurred),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLoading() {
        binding.swipeRefresh.setRefreshing(true);
    }
}
