package by.kos.moviesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import by.kos.moviesapp.ReviewAdapter.ReviewViewHolder;
import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

  private static final String REVIEW_POSITIVE = "Позитивный";
  private static final String REVIEW_NEGATIVE = "Негативный";
  private List<Review> reviewList = new ArrayList<>();

  public void setReviewList(List<Review> reviewList) {
    this.reviewList = reviewList;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.review_item, parent, false);
    return new ReviewViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

    Review review = reviewList.get(position);
    String typeReview = review.getType();
    int backgroundId;

    holder.tvAuthorReview.setText(review.getAuthor());
    holder.tvReviewText.setText(review.getReview());
    switch (typeReview) {
      case REVIEW_POSITIVE:
        backgroundId = android.R.color.holo_green_light;
        break;
      case REVIEW_NEGATIVE:
        backgroundId = android.R.color.holo_red_light;
        break;
      default:
        backgroundId = android.R.color.holo_orange_light;
        break;
    }
    CardView cv = (CardView) holder.itemView;
    cv.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), backgroundId));
  }

  @Override
  public int getItemCount() {
    return reviewList.size();
  }

  static class ReviewViewHolder extends RecyclerView.ViewHolder {

    private TextView tvAuthorReview;
    private TextView tvReviewText;

    public ReviewViewHolder(@NonNull View itemView) {
      super(itemView);
      tvAuthorReview = itemView.findViewById(R.id.tvAuthorReview);
      tvReviewText = itemView.findViewById(R.id.tvReviewText);
    }

  }

}
