package by.kos.moviesapp;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {

  @GET("movie?rating=kp&serach=7-10&sortField=votes.kp&sortType=-1&limit=5&token=token_here")
  Single<MovieResponse> loadMovies();

}
