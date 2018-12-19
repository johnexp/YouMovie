package br.com.devzero.youmovie.utilities;

public interface OnEventListener<T> {

    void onPreExecute();

    void onSuccess(T object);

    void onFailure(Exception e);
}
