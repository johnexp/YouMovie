package br.com.devzero.youmovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff.Mode;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.devzero.youmovie.MoviesAdapter.MoviesAdapterOnClickHandler;
import br.com.devzero.youmovie.domain.Movie;
import br.com.devzero.youmovie.utilities.NetworkUtils;
import br.com.devzero.youmovie.enumeration.EnumSortBy;
import br.com.devzero.youmovie.utilities.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Application's Main activity. This is the application's entry point and holds the movies list.
 */
public class MainActivity extends AppCompatActivity implements MoviesAdapterOnClickHandler {

    @BindView(R.id.rc_movies)
    protected RecyclerView mRecyclerView;

    private MoviesAdapter mMoviesAdapter;

    private GridLayoutManager mLayoutManager;

    @BindView(R.id.iv_sort_title_icon)
    protected ImageView mSortTitleIcon;

    @BindView(R.id.tv_sort_title)
    protected TextView mSortTitle;

    private static final String SORT_PREFERENCES = "SORT_BY";

    private EnumSortBy sortBy = EnumSortBy.POPULAR_ENDPOINT;

    @BindView(R.id.sr_movies_list)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mMoviesAdapter = new MoviesAdapter(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMoviesAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                findMovies(getSortPreference());
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorPrimaryDark);
    }

    @Override
    protected void onStart() {
        super.onStart();
        findMovies(getSortPreference());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            switch (item.getItemId()) {
                case R.id.action_popular:
                    if (getSortPreference().equals(EnumSortBy.TOP_RATED_ENDPOINT)) {
                        this.findMovies(EnumSortBy.POPULAR_ENDPOINT);
                    }
                    return true;
                case R.id.action_top_rated:
                    if (getSortPreference().equals(EnumSortBy.POPULAR_ENDPOINT)) {
                        this.findMovies(EnumSortBy.TOP_RATED_ENDPOINT);
                    }
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Saves user's list type into android preferences to keep what the user last listed
     */
    private void saveSortPreference(EnumSortBy sortBy) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(SORT_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SORT_PREFERENCES, sortBy.toString());
        editor.apply();
        editor.commit();
    }

    /**
     * Retrieves list type from android preferences
     */
    private EnumSortBy getSortPreference() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(SORT_PREFERENCES, Context.MODE_PRIVATE);
        try {
            return EnumSortBy.valueOf(sharedPreferences.getString(SORT_PREFERENCES, null));
        } catch (Exception e) {
            e.printStackTrace();
            return EnumSortBy.POPULAR_ENDPOINT;
        }
    }

    /**
     * Fetch movies according to sort type
     */
    private void findMovies(EnumSortBy sortBy) {
        if (!hasInternetConnection()) {
            ToastUtils.showToast(this, R.string.not_connected_message, Toast.LENGTH_LONG);
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        URL moviesSearchUrl = NetworkUtils.buildFindMoviesUrl(sortBy);
        this.sortBy = sortBy;
        setSortTitle();
        invalidateOptionsMenu();
        new FetchMoviesTask().execute(moviesSearchUrl);
    }

    /**
     * Changes activity subtitle according to sort type
     */
    private void setSortTitle() {
        StringBuilder stringBuilder = new StringBuilder();
        if (sortBy.equals(EnumSortBy.TOP_RATED_ENDPOINT)) {
            stringBuilder.append(getString(R.string.top_rated));
            mSortTitleIcon.setImageResource(R.drawable.baseline_star_black_48);
        } else {
            stringBuilder.append(getString(R.string.popular));
            mSortTitleIcon.setImageResource(R.drawable.baseline_thumb_up_alt_black_48);
        }
        mSortTitle.setText(stringBuilder.toString());
        mSortTitleIcon.setColorFilter(getColor(R.color.colorAccent), Mode.SRC_ATOP);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (sortBy.equals(EnumSortBy.TOP_RATED_ENDPOINT)) {
            MenuItem menuItemTopRated = menu.findItem(R.id.action_top_rated);
            menuItemTopRated.setIcon(R.drawable.baseline_star_white_48);
            MenuItem menuItemPopular = menu.findItem(R.id.action_popular);
            menuItemPopular.setIcon(R.drawable.outline_thumb_up_alt_white_48);
        } else {
            MenuItem menuItemTopRated = menu.findItem(R.id.action_top_rated);
            menuItemTopRated.setIcon(R.drawable.outline_star_border_white_48);
            MenuItem menuItemPopular = menu.findItem(R.id.action_popular);
            menuItemPopular.setIcon(R.drawable.baseline_thumb_up_alt_white_48);
        }
        return true;
    }

    @Override
    public void onClick(Long movieId) {
        if (!hasInternetConnection()) {
            ToastUtils.showToast(this, R.string.not_connected_message, Toast.LENGTH_LONG);
            return;
        }
        Intent movieDetailActivityIntent = new Intent(this, MovieDetailActivity.class);
        movieDetailActivityIntent.putExtra(Intent.EXTRA_INDEX, movieId);
        startActivity(movieDetailActivityIntent);
    }

    /**
     * Async task for handling requests to the movies API
     */
    class FetchMoviesTask extends AsyncTask<URL, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout.setRefreshing(true);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected List<Movie> doInBackground(URL... params) {
            URL searchUrl = params[0];
            try {
                return NetworkUtils.getMoviesList(searchUrl);
            } catch (IOException e) {
                ToastUtils.showToast(MainActivity.this, R.string.not_able_to_get_data, Toast.LENGTH_LONG);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            mSwipeRefreshLayout.setRefreshing(false);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            saveSortPreference(sortBy);
            if (movieList != null) {
                mMoviesAdapter.setMoviesList(movieList);
                mRecyclerView.stopScroll();
                mLayoutManager.scrollToPosition(0);
            }
        }
    }

    /**
     * Check device internet connection
     *
     * @return true If it has internet connection
     */
    public Boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
