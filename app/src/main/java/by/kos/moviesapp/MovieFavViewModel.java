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

public class MovieFavViewModel extends AndroidViewModel {

  private final CompositeDisposable compositeDisposable = new CompositeDisposable();
  private final MovieDao movieDao;

  public MovieFavViewModel(@NonNull Application application) {
    super(application);
    movieDao = MovieDatabase.getInstance(getApplication()).movieDao();
  }

  public LiveData<List<Movie>> getMovies() {
    return movieDao.getAllFavMovies();
  }



  @Override
  protected void onCleared() {
    super.onCleared();
    compositeDisposable.dispose();
  }
}
