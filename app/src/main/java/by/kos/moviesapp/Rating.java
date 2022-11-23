package by.kos.moviesapp;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Rating implements Serializable {

  @SerializedName("kp")
  private Double kp;

  @SerializedName("imdb")
  private Double imdb;

  public Rating(Double kp, Double imdb) {
    this.kp = kp;
    this.imdb = imdb;
  }

  public Double getKp() {
    return kp;
  }

  public Double getImdb() {
    return imdb;
  }

  @Override
  public String toString() {
    return "Rating{" +
        "kp='" + kp + '\'' +
        ", imdb='" + imdb + '\'' +
        '}';
  }
}
