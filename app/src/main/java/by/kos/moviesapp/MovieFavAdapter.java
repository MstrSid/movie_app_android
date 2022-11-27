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
import by.kos.moviesapp.MovieFavAdapter.MovieFavViewHolder;
import by.kos.moviesapp.MoviesAdapter.OnClickItem;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class MovieFavAdapter extends RecyclerView.Adapter<MovieFavViewHolder> {

  List<Movie> favMovieList = new ArrayList<>();

  private MoviesAdapter.OnClickItem onClickItem;

  public void setOnClickItem(MoviesAdapter.OnClickItem onClickItem) {
    this.onClickItem = onClickItem;
  }

  public void setFavMovieList(List<Movie> favMovieList) {
    this.favMovieList = favMovieList;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public MovieFavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.movie_item, parent, false);
    return new MovieFavViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MovieFavViewHolder holder, int position) {
    Movie movie = favMovieList.get(position);

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


    holder.itemView.setOnClickListener(view -> {
      if (onClickItem != null) {
        onClickItem.onClickItem(movie);
      }
    });

  }

  @Override
  public int getItemCount() {
    return favMovieList.size();
  }

  static class MovieFavViewHolder extends RecyclerView.ViewHolder {

    private final ImageView ivPoster;
    private final TextView tvRating;

    public MovieFavViewHolder(@NonNull View itemView) {
      super(itemView);
      ivPoster = itemView.findViewById(R.id.ivPoster);
      tvRating = itemView.findViewById(R.id.tvRating);
    }


  }
  interface OnClickItem {

    void onClickItem(Movie movie);
  }

}
