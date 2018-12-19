package br.com.devzero.youmovie.constants;

import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AnnotationSortBy {

    public static final String POPULAR_ENDPOINT = "popular";
    public static final String TOP_RATED_ENDPOINT = "top_rated";

    public AnnotationSortBy(@SortBy String sortBy) {
        System.out.println("SortBy :" + sortBy);
    }

    @StringDef({POPULAR_ENDPOINT, TOP_RATED_ENDPOINT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SortBy {

    }

    public static void main(String[] args) {
        AnnotationSortBy annotationSortBy = new AnnotationSortBy(POPULAR_ENDPOINT);
    }
}
