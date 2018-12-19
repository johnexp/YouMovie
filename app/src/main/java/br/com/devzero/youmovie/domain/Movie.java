package br.com.devzero.youmovie.domain;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"adult", "backdrop_path", "belongs_to_collection", "budget", "genres", "homepage", "id", "imdb_id",
        "original_language", "original_title", "overview", "popularity", "poster_path", "production_companies",
        "production_countries", "release_date", "revenue", "runtime", "spoken_languages", "status", "tagline", "title",
        "video", "vote_average", "vote_count"})
public class Movie implements Parcelable {

    @JsonProperty("adult")
    private Boolean adult;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("belongs_to_collection")
    private Object belongsToCollection;

    @JsonProperty("budget")
    private Long budget;

    @JsonProperty("genres")
    private List<Genre> genres = new ArrayList<>();

    @JsonProperty("homepage")
    private String homepage;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("imdb_id")
    private String imdbId;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("popularity")
    private Double popularity;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("production_companies")
    private List<ProductionCompany> productionCompanies = new ArrayList<>();

    @JsonProperty("production_countries")
    private List<ProductionCountry> productionCountries = new ArrayList<>();

    @JsonProperty("release_date")
    private Date releaseDate;

    @JsonProperty("revenue")
    private Long revenue;

    @JsonProperty("runtime")
    private Long runtime;

    @JsonProperty("spoken_languages")
    private List<SpokenLanguage> spokenLanguages = new ArrayList<>();

    @JsonProperty("status")
    private String status;

    @JsonProperty("tagline")
    private String tagline;

    @JsonProperty("title")
    private String title;

    @JsonProperty("video")
    private Boolean video;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("vote_count")
    private Long voteCount;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public Movie() {

    }

    public Movie(Parcel parcel) {
        byte tmpAdult = parcel.readByte();
        adult = tmpAdult == 0 ? null : tmpAdult == 1;
        backdropPath = parcel.readString();
        if (parcel.readByte() == 0) {
            budget = null;
        } else {
            budget = parcel.readLong();
        }
        parcel.readList(genres, Genre.class.getClassLoader());
        homepage = parcel.readString();
        if (parcel.readByte() == 0) {
            id = null;
        } else {
            id = parcel.readLong();
        }
        imdbId = parcel.readString();
        originalLanguage = parcel.readString();
        originalTitle = parcel.readString();
        overview = parcel.readString();
        if (parcel.readByte() == 0) {
            popularity = null;
        } else {
            popularity = parcel.readDouble();
        }
        posterPath = parcel.readString();
        parcel.readList(productionCompanies, ProductionCompany.class.getClassLoader());
        parcel.readList(productionCountries, ProductionCountry.class.getClassLoader());
        releaseDate = (Date) parcel.readSerializable();
        if (parcel.readByte() == 0) {
            revenue = null;
        } else {
            revenue = parcel.readLong();
        }
        if (parcel.readByte() == 0) {
            runtime = null;
        } else {
            runtime = parcel.readLong();
        }
        parcel.readList(spokenLanguages, SpokenLanguage.class.getClassLoader());
        status = parcel.readString();
        tagline = parcel.readString();
        title = parcel.readString();
        byte tmpVideo = parcel.readByte();
        video = tmpVideo == 0 ? null : tmpVideo == 1;
        if (parcel.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = parcel.readDouble();
        }
        if (parcel.readByte() == 0) {
            voteCount = null;
        } else {
            voteCount = parcel.readLong();
        }
        parcel.readMap(additionalProperties, Map.class.getClassLoader());
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @JsonProperty("adult")
    public Boolean getAdult() {
        return adult;
    }

    @JsonProperty("adult")
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    @JsonProperty("backdrop_path")
    public String getBackdropPath() {
        return backdropPath;
    }

    @JsonProperty("backdrop_path")
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @JsonProperty("belongs_to_collection")
    public Object getBelongsToCollection() {
        return belongsToCollection;
    }

    @JsonProperty("belongs_to_collection")
    public void setBelongsToCollection(Object belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    @JsonProperty("budget")
    public Long getBudget() {
        return budget;
    }

    @JsonProperty("budget")
    public void setBudget(Long budget) {
        this.budget = budget;
    }

    @JsonProperty("genres")
    public List<Genre> getGenres() {
        return genres;
    }

    @JsonProperty("genres")
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @JsonProperty("homepage")
    public String getHomepage() {
        return homepage;
    }

    @JsonProperty("homepage")
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("imdb_id")
    public String getImdbId() {
        return imdbId;
    }

    @JsonProperty("imdb_id")
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    @JsonProperty("original_language")
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    @JsonProperty("original_language")
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @JsonProperty("original_title")
    public String getOriginalTitle() {
        return originalTitle;
    }

    @JsonProperty("original_title")
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    @JsonProperty("overview")
    public String getOverview() {
        return overview;
    }

    @JsonProperty("overview")
    public void setOverview(String overview) {
        this.overview = overview;
    }

    @JsonProperty("popularity")
    public Double getPopularity() {
        return popularity;
    }

    @JsonProperty("popularity")
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    @JsonProperty("poster_path")
    public String getPosterPath() {
        return posterPath;
    }

    @JsonProperty("poster_path")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @JsonProperty("production_companies")
    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    @JsonProperty("production_companies")
    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    @JsonProperty("production_countries")
    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    @JsonProperty("production_countries")
    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    @JsonProperty("release_date")
    public Date getReleaseDate() {
        return releaseDate;
    }

    @JsonProperty("release_date")
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @JsonProperty("revenue")
    public Long getRevenue() {
        return revenue;
    }

    @JsonProperty("revenue")
    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    @JsonProperty("runtime")
    public Long getRuntime() {
        return runtime;
    }

    @JsonProperty("runtime")
    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    @JsonProperty("spoken_languages")
    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    @JsonProperty("spoken_languages")
    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("tagline")
    public String getTagline() {
        return tagline;
    }

    @JsonProperty("tagline")
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("video")
    public Boolean getVideo() {
        return video;
    }

    @JsonProperty("video")
    public void setVideo(Boolean video) {
        this.video = video;
    }

    @JsonProperty("vote_average")
    public Double getVoteAverage() {
        return voteAverage;
    }

    @JsonProperty("vote_average")
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @JsonProperty("vote_count")
    public Long getVoteCount() {
        return voteCount;
    }

    @JsonProperty("vote_count")
    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (adult != null && adult ? 1 : 0));
        parcel.writeString(backdropPath);
        parcel.writeValue(belongsToCollection);
        parcel.writeLong(budget);
        parcel.writeList(genres);
        parcel.writeString(homepage);
        parcel.writeLong(id);
        parcel.writeString(imdbId);
        parcel.writeString(originalLanguage);
        parcel.writeString(originalTitle);
        parcel.writeString(overview);
        parcel.writeDouble(popularity);
        parcel.writeString(posterPath);
        parcel.writeList(productionCompanies);
        parcel.writeList(productionCountries);
        parcel.writeSerializable(releaseDate);
        parcel.writeLong(revenue);
        parcel.writeLong(runtime);
        parcel.writeList(spokenLanguages);
        parcel.writeString(status);
        parcel.writeString(tagline);
        parcel.writeString(title);
        parcel.writeByte((byte) (video != null && video ? 1 : 0));
        parcel.writeDouble(voteAverage);
        parcel.writeLong(voteCount);
        parcel.writeMap(additionalProperties);
    }
}
