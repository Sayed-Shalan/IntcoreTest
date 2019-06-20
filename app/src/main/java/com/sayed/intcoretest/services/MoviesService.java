package com.sayed.intcoretest.services;

import com.sayed.intcoretest.model.MoviesResult;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesService {

    @GET("3/discover/movie?sort_by=popularity.desc")
    Call<MoviesResult> getMovies(@Query(value = "api_key") String apiKey,@Query("page") int page);
}
