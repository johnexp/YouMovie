package br.com.devzero.youmovie;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.devzero.youmovie.MoviesAdapter.MoviesViewHolder;
import br.com.devzero.youmovie.domain.Movie;
import br.com.devzero.youmovie.utilities.NetworkUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;

/**
 * Movies list adapter for handling user interactions to the list items
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    private List<Movie> moviesList = new ArrayList<>();

    private MoviesAdapterOnClickHandler mMovieClickHandler;

    private Context context;

    /**
     * Constructor
     *
     * @param movieClickHandler - List items click handler
     */
    public MoviesAdapter(MoviesAdapterOnClickHandler movieClickHandler) {
        this.mMovieClickHandler = movieClickHandler;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.movies_list_item, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.tvMovieName.setText(movie.getTitle());
        // gets the built movie poster uri
        Uri moviePosterUri = NetworkUtils.buildPosterUri(movie.getPosterPath());
        // request options to make image corners rounded
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.poster_placeholder_dark);
        // GLide loads the uri and put the downloaded image into the ImageView
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(moviePosterUri)
                .transition(withCrossFade())
                .into(holder.ivMoviePoster);
    }

    @Override
    public int getItemCount() {
        return getMoviesList().size();
    }

    public List<Movie> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(List<Movie> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    public interface MoviesAdapterOnClickHandler {

        void onClick(Long movieId);
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        @BindView(R.id.tv_movie_name)
        TextView tvMovieName;

        @BindView(R.id.iv_movie_poster)
        ImageView ivMoviePoster;

        MoviesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie clickedMovie = moviesList.get(adapterPosition);
            mMovieClickHandler.onClick(clickedMovie.getId());
        }
    }

}
