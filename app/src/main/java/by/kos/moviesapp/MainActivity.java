package by.kos.moviesapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

  private MainViewModel viewModel;
  private RecyclerView rvMovies;
  private MoviesAdapter moviesAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    rvMovies = findViewById(R.id.rvMovies);
    moviesAdapter = new MoviesAdapter();
    rvMovies.setAdapter(moviesAdapter);
    rvMovies.setLayoutManager(new GridLayoutManager(this, 2));

    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    viewModel.getMovies().observe(this, movies -> {
      moviesAdapter.setMovieList(movies);
    });
    viewModel.loadMovies();
  }
}