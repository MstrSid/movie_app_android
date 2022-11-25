package by.kos.moviesapp;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

  @GET("movie?rating=kp&search=6-10&sortField=votes.kp&sortType=-1&limit=30&token=t")
  Single<MovieResponse> loadMovies(@Query("page") int page);

  @GET("movie?token=t&field=id")
  Single<TrailerResponse> loadTrailers(@Query("search") int id);

  @GET("review?token=t&field=movieId")
  Single<ReviewResponse> loadReviews(@Query("search") int id);

}
