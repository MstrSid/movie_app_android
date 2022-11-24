package by.kos.moviesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import by.kos.moviesapp.TrailerAdapter.TrailerViewHolder;
import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

  List<Trailer> trailerList = new ArrayList<>();
  private OnTrailerClickListener onTrailerClickListener;

  public void setOnTrailerClickListener(
      OnTrailerClickListener onTrailerClickListener) {
    this.onTrailerClickListener = onTrailerClickListener;
  }

  public void setTrailerList(List<Trailer> trailerList) {
    this.trailerList = trailerList;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.trailer_item, parent, false);
    return new TrailerViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
    Trailer trailer = trailerList.get(position);
    holder.tvTrailerTitle.setText(trailer.getName());
    holder.itemView.setOnClickListener(view -> {
      if (onTrailerClickListener != null) {
        onTrailerClickListener.onTrailerClick(trailer);
      }
    });
  }

  @Override
  public int getItemCount() {
    return trailerList.size();
  }

  static class TrailerViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvTrailerTitle;

    public TrailerViewHolder(@NonNull View itemView) {
      super(itemView);
      tvTrailerTitle = itemView.findViewById(R.id.tvTrailerTitle);
    }
  }

  interface OnTrailerClickListener {

    void onTrailerClick(Trailer trailer);
  }

}
