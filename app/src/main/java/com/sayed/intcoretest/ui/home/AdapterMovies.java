package com.sayed.intcoretest.ui.home;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sayed.intcoretest.R;
import com.sayed.intcoretest.databinding.SingleItemMovieBinding;
import com.sayed.intcoretest.model.Movie;
import com.sayed.intcoretest.ui.base.BaseAdapter;
import com.sayed.intcoretest.ui.base.DataBindingViewHolder;

import java.util.List;

public class AdapterMovies extends BaseAdapter {

    //dec data
    private MoviesCallback listener;
    private List<Movie> items;
    private String posterImageUrl="https://image.tmdb.org/t/p/original";

    //constructor to init data
    public AdapterMovies(MoviesCallback listener, List<Movie> items) {
        this.listener = listener;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext()),parent);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MoviesViewHolder)holder).onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //on Refresh
    void refresh(List<Movie> item){
        this.items=item;
        notifyDataSetChanged();
    }

    //add New Item --overloading
    void add(Movie item){
        items.add(item);
        notifyDataSetChanged();
    }

    //add new page --overloading
    void add(List<Movie> newPageItems){
        items.addAll(newPageItems);
        notifyDataSetChanged();
    }

    //clear items
    void clearItems(){
        items.clear();
    }

    /**
     * Create View holder
     */
    public class MoviesViewHolder extends DataBindingViewHolder<SingleItemMovieBinding>{

        //constructor
        public MoviesViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, R.layout.single_item_movie);
        }

        //On Bind
        void onBind(Movie movie){

            //set image with caching
            Glide.with(getContext())
                    .load(posterImageUrl.concat(movie.getPoster_path()))
                    .into(binding.posterImage);

            binding.posterFavouriteButton.setColorFilter(movie.isFavourite()? Color.YELLOW:Color.GRAY); //set fav btn color
            binding.posterFavouriteButton.setOnClickListener(v -> {
                    binding.posterFavouriteButton.setColorFilter(movie.isFavourite()?Color.GRAY:Color.YELLOW);
                    items.get(getAdapterPosition()).setFavourite(!movie.isFavourite());
                    listener.onFavouriteClick(items.get(getAdapterPosition()),getAdapterPosition());

            });

        }
    }

    /**
     * create click interface
     */
    interface MoviesCallback{
        void onFavouriteClick(Movie movie,int position);
    }
}
