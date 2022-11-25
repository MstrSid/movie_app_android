package by.kos.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);

    viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
    initViews();

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
  }
}