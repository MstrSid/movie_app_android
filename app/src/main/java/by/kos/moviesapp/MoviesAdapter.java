package by.kos.moviesapp;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import by.kos.moviesapp.MoviesAdapter.MovieViewHolder;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {

  private List<Movie> movieList = new ArrayList<>();
  private OnReachEndListener onReachEndListener;
  private OnClickItem onClickItem;

  public void setOnClickItem(OnClickItem onClickItem) {
    this.onClickItem = onClickItem;
  }

  public void setMovieList(List<Movie> movieList) {
    this.movieList = movieList;
    notifyDataSetChanged();
  }

  public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
    this.onReachEndListener = onReachEndListener;
  }

  @NonNull
  @Override
  public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.movie_item, parent, false);
    return new MovieViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
    Movie movie = movieList.get(position);

    Double rating = movie.getRating().getKp();
    int backgroundId;

    if (rating > 8.5) {
      backgroundId = R.drawable.circle_green;
    } else if (rating >= 7) {
      backgroundId = R.drawable.circle_yellow;
    } else {
      backgroundId = R.drawable.circle_red;
    }

    Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundId);

    Glide.with(holder.itemView).load(movie.getPoster().getUrl()).into(holder.ivPoster);
    holder.tvRating.setText(String.valueOf(Math.round(rating * 10.0) / 10.0));
    holder.tvRating.setBackground(background);

    if (position >= movieList.size() - 10 && onReachEndListener != null) {
      onReachEndListener.onReachEnd();
    }

    holder.itemView.setOnClickListener(view -> {
      if (onClickItem != null) {
        onClickItem.onClickItem(movie);
      }
    });
  }


  interface OnReachEndListener {

    void onReachEnd();
  }

  interface OnClickItem {

    void onClickItem(Movie movie);
  }

  @Override
  public int getItemCount() {
    return movieList.size();
  }

  static class MovieViewHolder extends RecyclerView.ViewHolder {

    private final ImageView ivPoster;
    private final TextView tvRating;


    public MovieViewHolder(@NonNull View itemView) {
      super(itemView);
      ivPoster = itemView.findViewById(R.id.ivPoster);
      tvRating = itemView.findViewById(R.id.tvRating);
    }
  }
}
