package br.com.devzero.youmovie;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import br.com.devzero.youmovie.domain.Movie;
import br.com.devzero.youmovie.utilities.NetworkUtils;
import br.com.devzero.youmovie.utilities.OnEventListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Async task for handling requests to the movies API
 */
public class FetchMoviesTask extends AsyncTaskLoader<List<Movie>> {

    private OnEventListener<List<Movie>> mCallback;

    // TODO: Tratar exceção corretamente
    private Exception mException;

    private Bundle mArgs;

    private List<Movie> mMoviesList;

    /* A constant to save and restore the URL that is being displayed */
    static final String SEARCH_QUERY_URL = "query";

    /**
     * Constructor
     */
    FetchMoviesTask(Context context, OnEventListener<List<Movie>> callback, Bundle args) {
        super(context);
        this.mCallback = callback;
        this.mArgs = args;
    }

    @Override
    protected void onStartLoading() {
        if (mArgs == null) {
            return;
        }

        mCallback.onStart();

        if (mMoviesList != null) {
            deliverResult(mMoviesList);
        } else {
            forceLoad();
        }
    }

    @Nullable
    @Override
    public List<Movie> loadInBackground() {
        String searchUrlString = mArgs.getString(SEARCH_QUERY_URL);

        if (TextUtils.isEmpty(searchUrlString)) {
            return null;
        }

        try {
            URL searchUrl = new URL(searchUrlString);
            return NetworkUtils.getMoviesList(searchUrl);
        } catch (IOException e) {
            mException = e;
            return null;
        }
    }

    @Override
    public void deliverResult(@Nullable List<Movie> data) {
        mMoviesList = data;
        super.deliverResult(data);
    }
}