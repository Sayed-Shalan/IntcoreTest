package com.sayed.intcoretest.ui.home;

import com.sayed.intcoretest.model.Movie;

import java.util.List;

public interface FavouriteView {
    void addItems(List<Movie> moviesList); //new Items
    void showNoData();//on zero items-list empty
}
