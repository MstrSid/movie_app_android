package by.kos.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.net.URI;

public class MovieDetailActivity extends AppCompatActivity {

  private static final String EXTRA_MOVIE = "movie";
  private static final String TAG = "MovieDetailActivity";

  private MovieDetailViewModel viewModel;

  private ImageView ivPoster;
  private TextView tvTitle;
  private TextView tvYear;
  private TextView tvDescription;
  private RecyclerView rvTrailers;
  private TrailerAdapter trailerAdapter;
  private RecyclerView rvReviews;
  private ReviewAdapter reviewAdapter;
  private ImageView ivFav;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);

    viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
    initViews();

    Drawable starOff = ContextCompat.getDrawable(MovieDetailActivity.this,
        android.R.drawable.star_big_off);
    Drawable starOn = ContextCompat.getDrawable(MovieDetailActivity.this,
        android.R.drawable.star_big_on);

    Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

    Glide.with(this).load(movie.getPoster().getUrl()).into(ivPoster);

    trailerAdapter = new TrailerAdapter();
    rvTrailers.setAdapter(trailerAdapter);
    rvTrailers.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    tvTitle.setText(movie.getName());
    tvYear.setText(String.valueOf(movie.getYear()));
    tvDescription.setText(movie.getDescription());

    viewModel.loadTrailers(movie.getId());
    viewModel.getTrailers().observe(this, trailers -> trailerAdapter.setTrailerList(trailers));

    trailerAdapter.setOnTrailerClickListener(trailer -> {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(Uri.parse(trailer.getUrl()));
      startActivity(intent);
    });

    reviewAdapter = new ReviewAdapter();
    rvReviews.setAdapter(reviewAdapter);
    rvReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    viewModel.loadReviews(movie.getId());
    viewModel.getReviews().observe(this, reviews -> reviewAdapter.setReviewList(reviews));

    viewModel.getFavMovie(movie.getId()).observe(this, movieFromDB -> {
      if (movieFromDB == null) {
        ivFav.setImageDrawable(starOff);
        ivFav.setOnClickListener(view -> {
          viewModel.addFavMovie(movie);
        });
      } else {
        ivFav.setImageDrawable(starOn);
        ivFav.setOnClickListener(view -> {
          viewModel.removeFavMovie(movie.getId());
        });
      }
    });

    viewModel.getAdded().observe(this, added -> {
      if(added){
        Snackbar.make(this, getWindow().getDecorView(), "Added", Snackbar.LENGTH_SHORT).show();
      } else {
        Snackbar.make(this, getWindow().getDecorView(), "Error", Snackbar.LENGTH_SHORT).show();
      }
    });

    viewModel.getRemoved().observe(this, removed -> {
      if(removed){
        Snackbar.make(this, getWindow().getDecorView(), "Removed", Snackbar.LENGTH_SHORT).show();
      } else {
        Snackbar.make(this, getWindow().getDecorView(), "Error", Snackbar.LENGTH_SHORT).show();
      }
    });
  }

  public static Intent newIntent(Context context, Movie movie) {
    Intent intent = new Intent(context, MovieDetailActivity.class);
    intent.putExtra(EXTRA_MOVIE, movie);
    return intent;
  }

  private void initViews() {
    ivPoster = findViewById(R.id.ivPoster);
    tvTitle = findViewById(R.id.tvTitle);
    tvYear = findViewById(R.id.tvYear);
    tvDescription = findViewById(R.id.tvDescription);
    rvTrailers = findViewById(R.id.rvTrailers);
    rvReviews = findViewById(R.id.rvReviews);
    ivFav = findViewById(R.id.ivFav);
  }
}