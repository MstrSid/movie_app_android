package by.kos.moviesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

  private MainViewModel viewModel;
  private RecyclerView rvMovies;
  private MoviesAdapter moviesAdapter;
  private ProgressBar progressBar;

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
  }

  private void initViews() {
    rvMovies = findViewById(R.id.rvMovies);
    progressBar = findViewById(R.id.progressBar);
  }
}