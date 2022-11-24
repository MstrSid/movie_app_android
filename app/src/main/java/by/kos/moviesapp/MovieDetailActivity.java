package by.kos.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.net.URI;

public class MovieDetailActivity extends AppCompatActivity {

  private static final String EXTRA_MOVIE = "movie";

  private ImageView ivPoster;
  private TextView tvTitle;
  private TextView tvYear;
  private TextView tvDescription;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);
    initViews();
    Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
    Glide.with(this).load(movie.getPoster().getUrl()).into(ivPoster);
    tvTitle.setText(movie.getName());
    tvYear.setText(String.valueOf(movie.getYear()));
    tvDescription.setText(movie.getDescription());

    ApiFactory.getApiService().loadTrailers(movie.getId()).subscribeOn(Schedulers.io()).observeOn(
        AndroidSchedulers.mainThread()).subscribe(trailerResponse -> {
      for (Trailer trailer: trailerResponse.getTrailerList().getTrailers()) {
        Log.d("MovieDetailActivity",trailer.getName());
        Log.d("MovieDetailActivity",trailer.getUrl());
      }

    }, throwable -> Log.d("MovieDetailActivity", throwable.getMessage()));
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
  }
}