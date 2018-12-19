package br.com.devzero.youmovie;

import android.os.AsyncTask;
import br.com.devzero.youmovie.domain.Movie;
import br.com.devzero.youmovie.utilities.NetworkUtils;
import br.com.devzero.youmovie.utilities.OnEventListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Async task for handling requests to the movies API
 */
public class FetchMoviesTask extends AsyncTask<URL, Void, List<Movie>> {

    private OnEventListener<List<Movie>> mCallback;

    private Exception mException;

    /**
     * Constructor
     */
    FetchMoviesTask(OnEventListener<List<Movie>> callback) {
        this.mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.mCallback.onPreExecute();
    }

    @Override
    protected List<Movie> doInBackground(URL... params) {
        URL searchUrl = params[0];
        try {
            return NetworkUtils.getMoviesList(searchUrl);
        } catch (IOException e) {
            mException = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movieList) {
        if (mCallback != null) {
            if (mException == null) {
                this.mCallback.onSuccess(movieList);
            } else {
                this.mCallback.onFailure(mException);
            }
        }
    }
}