package br.com.devzero.youmovie;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.devzero.youmovie.domain.Movie;
import br.com.devzero.youmovie.utilities.NetworkUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

/**
 * Activity to show movies detail
 */
public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_movie_poster)
    protected ImageView ivPoster;

    @BindView(R.id.iv_movie_backdrop)
    protected ImageView ivBackdrop;

    @BindView(R.id.tv_movie_title)
    protected TextView tvTitle;

    @BindView(R.id.tv_movie_release_year)
    protected TextView tvReleaseYear;

    @BindView(R.id.tv_movie_runtime)
    protected TextView tvRuntime;

    @BindView(R.id.tv_movie_vote_average)
    protected TextView tvVoteAverage;

    @BindView(R.id.tv_movie_overview)
    protected TextView tvMovieOverview;

    @BindView(R.id.pb_loading_indicator)
    protected ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().hasExtra(Intent.EXTRA_INDEX)) {
            findMovie(getIntent().getLongExtra(Intent.EXTRA_INDEX, 0L));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void findMovie(Long movieId) {
        if (movieId != null && movieId > 0L) {
            URL moviesSearchUrl = NetworkUtils.buildFindMovieUrl(movieId);
            new FetchMovieTask().execute(moviesSearchUrl);
        }
    }

    private void setMovieProperties(Movie movie) {
        this.getMovieImages(movie);
        this.tvTitle.setText(movie.getTitle());
        Calendar cal = Calendar.getInstance();
        cal.setTime(movie.getReleaseDate());
        this.tvReleaseYear.setText(String.valueOf(cal.get(Calendar.YEAR)));
        this.tvRuntime.setText(String.valueOf(movie.getRuntime())
                .concat("m"));
        this.tvVoteAverage.setText(String.valueOf(movie.getVoteAverage())
                .concat("/10"));
        this.tvMovieOverview.setText(movie.getOverview());
        this.colorizeTextViewDrawables(this.tvReleaseYear);
        this.colorizeTextViewDrawables(this.tvRuntime);
        this.colorizeTextViewDrawables(this.tvVoteAverage);
    }

    private void colorizeTextViewDrawables(TextView textView) {
        Drawable[] drawables = textView.getCompoundDrawables();
        if (drawables.length > 0) {
            drawables[0].setColorFilter(getColor(R.color.colorAccent), Mode.SRC_ATOP);
        }
    }

    private void getMovieImages(Movie movie) {
        // gets the built movie poster uri
        Uri moviePosterUri = NetworkUtils.buildPosterUri(movie.getPosterPath());
        Uri movieBackdropUri = NetworkUtils.buildPosterUri(movie.getBackdropPath());

        // request options to set a placeholder for movie backdrop
        RequestOptions backdropRequestOptions = new RequestOptions().placeholder(R.drawable.backdrop_placeholder);

        // request options to make poster image corners rounded
        RequestOptions posterRequestOptions = new RequestOptions().transform(new RoundedCorners(10))
                .placeholder(R.drawable.poster_placeholder_light);

        // GLide loads the uri and put the downloaded image into the ImageView
        Glide.with(this)
                .setDefaultRequestOptions(posterRequestOptions)
                .load(moviePosterUri)
                .transition(withCrossFade())
                .into(this.ivPoster);
        Glide.with(this)
                .setDefaultRequestOptions(backdropRequestOptions)
                .load(movieBackdropUri)
                .into(this.ivBackdrop);
        this.ivBackdrop.setColorFilter(Color.GRAY, Mode.LIGHTEN);
    }

    class FetchMovieTask extends AsyncTask<URL, Void, Movie> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie doInBackground(URL... params) {
            URL searchUrl = params[0];
            try {
                return NetworkUtils.getMovie(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie movie) {
            if (movie != null) {
                setMovieProperties(movie);
            }
            pbLoading.setVisibility(View.INVISIBLE);
        }
    }
}
