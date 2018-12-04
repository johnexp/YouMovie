package br.com.devzero.youmovie.enumeration;

public enum EnumSortBy {

    POPULAR_ENDPOINT("popular"), //
    TOP_RATED_ENDPOINT("top_rated");

    private String path;

    EnumSortBy(String path) {
        this.setPath(path);
    }

    public String getPath() {
        return path;
    }

    private void setPath(String path) {
        this.path = path;
    }
}
