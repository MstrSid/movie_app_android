package by.kos.moviesapp;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

  public static final String TAG = "MainViewModel";
  private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
  private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
  private final CompositeDisposable compositeDisposable = new CompositeDisposable();
  private int page = 1;

  public MainViewModel(@NonNull Application application) {

    super(application);
    loadMovies();
  }

  public LiveData<List<Movie>> getMovies() {
    return movies;
  }

  public LiveData<Boolean> getIsLoading() {
    return isLoading;
  }

  public void loadMovies() {
    Boolean loading = isLoading.getValue();
    if (loading != null && loading) {
      return;
    }
    Disposable disposable = ApiFactory.getApiService()
        .loadMovies(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(d -> isLoading.setValue(true))
        .doAfterTerminate(() -> isLoading.setValue(false))
        .subscribe(
            movieResponse -> {
              List<Movie> loadedMovies = movies.getValue();
              if (loadedMovies != null) {
                loadedMovies.addAll(movieResponse.getMovies());
              } else {
                movies.setValue(movieResponse.getMovies());
              }
              page++;
            },
            throwable -> {
              Log.d(TAG, throwable.toString());
            }
        );
    compositeDisposable.add(disposable);
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    compositeDisposable.dispose();
  }
}
