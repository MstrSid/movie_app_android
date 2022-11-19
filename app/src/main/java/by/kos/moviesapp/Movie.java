package by.kos.moviesapp;

import com.google.gson.annotations.SerializedName;

public class Movie {

  @SerializedName("id")
  private int id;

  @SerializedName("name")
  private String name;

  @SerializedName("description")
  private String description;

  @SerializedName("year")
  private int year;

  @SerializedName("rating")
  private Rating rating;

  @SerializedName("poster")
  private Poster poster;

  public Movie(int id, String name, String description, int year, Rating rating,
      Poster poster) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.year = year;
    this.rating = rating;
    this.poster = poster;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getYear() {
    return year;
  }

  public Rating getRating() {
    return rating;
  }

  public Poster getPoster() {
    return poster;
  }
}
