package by.kos.moviesapp;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

public class MovieDetailViewModel extends AndroidViewModel {

  private static final String TAG = "MovieDetailViewModel";
  private final CompositeDisposable compositeDisposable = new CompositeDisposable();
  private final MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
  private final MutableLiveData<List<Review>> reviews = new MutableLiveData<>();

  public LiveData<List<Review>> getReviews() {
    return reviews;
  }

  public LiveData<List<Trailer>> getTrailers() {
    return trailers;
  }

  public MovieDetailViewModel(@NonNull Application application) {
    super(application);
  }

  public void loadTrailers(int id) {
    Disposable disposable = ApiFactory.getApiService()
        .loadTrailers(id)
        .subscribeOn(Schedulers.io())
        .map(trailerResponse -> trailerResponse.getTrailerList().getTrailers())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(trailerList -> trailers.setValue(trailerList),
            throwable -> Log.d(TAG, throwable.getMessage()));
    compositeDisposable.add(disposable);
  }

  public void loadReviews(int id) {
    Disposable disposable = ApiFactory.getApiService()
        .loadReviews(id)
        .subscribeOn(Schedulers.io())
        .map(reviewResponse -> reviewResponse.getReviewList())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(reviewList -> reviews.setValue(reviewList),
            throwable -> Log.d(TAG, throwable.getMessage()));
    compositeDisposable.add(disposable);
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    compositeDisposable.dispose();
  }
}
