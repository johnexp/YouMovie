package br.com.devzero.youmovie.utilities;

import android.net.Uri;
import br.com.devzero.youmovie.BuildConfig;
import br.com.devzero.youmovie.constants.AnnotationSortBy.SortBy;
import br.com.devzero.youmovie.domain.Movie;
import br.com.devzero.youmovie.domain.QueryResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Utility class to handling requests to "The Movie Database"
 */
public class NetworkUtils {

    private final static String TMDB_API_BASE_URL = "https://api.themoviedb.org";

    private final static String TMDB_BASE_URL = "https://image.tmdb.org/t/p";

    private final static String IMAGE_SIZE = "w185";

    private final static String MOVIE_QUERY_PATH = "movie";

    private final static String API_VERSION = "3";

    private final static String API_QUERY = "api_key";

    private static final String LANGUAGE = "language";

    /**
     * Builds URL to get a movies list according to the user's selected sort type
     */
    public static URL buildFindMoviesUrl(@SortBy String sortBy) {
        Uri builtUri = Uri.parse(TMDB_API_BASE_URL)
                .buildUpon()
                .appendPath(API_VERSION)
                .appendPath(MOVIE_QUERY_PATH)
                .appendEncodedPath(sortBy)
                .appendQueryParameter(API_QUERY, BuildConfig.TmdbApiKey)
                .appendQueryParameter(LANGUAGE, Locale.getDefault()
                        .toLanguageTag())
                .build();
        try {
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Builds URL to get a specific movie details according to a movie id
     */
    public static URL buildFindMovieUrl(Long movieId) {
        Uri builtUri = Uri.parse(TMDB_API_BASE_URL)
                .buildUpon()
                .appendPath(API_VERSION)
                .appendPath(MOVIE_QUERY_PATH)
                .appendPath(movieId.toString())
                .appendQueryParameter(API_QUERY, BuildConfig.TmdbApiKey)
                .appendQueryParameter(LANGUAGE, Locale.getDefault()
                        .toLanguageTag())
                .build();
        try {
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Builds URL to get a specific movie poster
     */
    public static Uri buildPosterUri(String posterPath) {
        return Uri.parse(TMDB_BASE_URL)
                .buildUpon()
                .appendPath(IMAGE_SIZE)
                .appendEncodedPath(posterPath)
                .build();
    }

    /**
     * Request to get a movie list from the API
     */
    public static List<Movie> getMoviesList(URL url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        QueryResult queryResult = mapper.readValue(url, QueryResult.class);
        if (queryResult != null && queryResult.getResults() != null) {
            return queryResult.getResults();
        }
        return Collections.emptyList();
    }

    /**
     * Request to get a specific movie from the API
     */
    public static Movie getMovie(URL url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(url, Movie.class);
    }
}
