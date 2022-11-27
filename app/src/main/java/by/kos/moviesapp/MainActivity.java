package by.kos.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

  private MainViewModel viewModel;
  private RecyclerView rvMovies;
  private MoviesAdapter moviesAdapter;
  private ProgressBar progressBar;
  private FloatingActionButton fab;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initViews();

    moviesAdapter = new MoviesAdapter();
    rvMovies.setAdapter(moviesAdapter);
    rvMovies.setLayoutManager(new GridLayoutManager(this, 2));

    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    viewModel.getMovies().observe(this, movies -> {
      moviesAdapter.setMovieList(movies);
    });

    viewModel.getIsLoading().observe(this, isLoading -> {
      if (isLoading) {
        progressBar.setVisibility(View.VISIBLE);
      } else {
        progressBar.setVisibility(View.INVISIBLE);
      }
    });

    moviesAdapter.setOnReachEndListener(() -> {
      viewModel.loadMovies();
    });

    moviesAdapter.setOnClickItem(movie -> {
      Intent intent = MovieDetailActivity.newIntent(this, movie);
      startActivity(intent);
    });

    fab.setOnClickListener(view -> {
      Intent intent = MovieFavActivity.newIntent(this);
      startActivity(intent);
    });

  }

  private void initViews() {
    rvMovies = findViewById(R.id.rvMovies);
    progressBar = findViewById(R.id.progressBar);
    fab = findViewById(R.id.floatingActionButton);
  }
}