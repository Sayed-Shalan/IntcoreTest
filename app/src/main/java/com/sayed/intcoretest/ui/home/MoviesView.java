package com.sayed.intcoretest.ui.home;

import com.sayed.intcoretest.model.Movie;

import java.util.List;

public interface MoviesView {
    void addItems(List<Movie> moviesList,int pageNum,int totalPages); //new Items
    void onFail();//on fail to get items
    void showLoading(); //show swipe refresh
}
