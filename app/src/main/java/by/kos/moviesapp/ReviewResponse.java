package by.kos.moviesapp;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class ReviewResponse implements Serializable {

  @SerializedName("docs")
  private List<Review> reviewList;

  public ReviewResponse(List<Review> reviewList) {
    this.reviewList = reviewList;
  }

  public List<Review> getReviewList() {
    return reviewList;
  }

  @Override
  public String toString() {
    return "ReviewList{" +
        "reviewList=" + reviewList +
        '}';
  }
}
