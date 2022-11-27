package by.kos.moviesapp;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieFavActivity extends AppCompatActivity {

  private RecyclerView rvFavMovies;
  private MoviesAdapter moviesAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_fav);

    initViews();
    moviesAdapter = new MoviesAdapter();
    rvFavMovies.setAdapter(moviesAdapter);
    rvFavMovies.setLayoutManager(new GridLayoutManager(this, 2));
    moviesAdapter.setOnClickItem(movie -> {
      Intent intent = MovieDetailActivity.newIntent(MovieFavActivity.this, movie);
      startActivity(intent);
    });

    MovieFavViewModel viewModel = new ViewModelProvider(this).get(MovieFavViewModel.class);

    viewModel.getMovies().observe(this, movies -> moviesAdapter.setMovieList(movies));
  }

  public static Intent newIntent(Context context) {
    return new Intent(context, MovieFavActivity.class);
  }

  private void initViews() {
    rvFavMovies = findViewById(R.id.rvFavMovies);
  }
}