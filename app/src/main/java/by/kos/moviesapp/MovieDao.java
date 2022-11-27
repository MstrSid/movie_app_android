package by.kos.moviesapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.rxjava3.core.Completable;
import java.util.List;

@Dao
public interface MovieDao {

  @Query("SELECT * FROM fav_movies")
  LiveData<List<Movie>> getAllFavMovies();

  @Query("SELECT * FROM fav_movies WHERE id = :movieId")
  LiveData<Movie> getFavMovie(int movieId);

  @Insert
  Completable insertMovie(Movie movie);

  @Query("DELETE FROM fav_movies WHERE id = :id")
  Completable removeMovie(int id);

}
