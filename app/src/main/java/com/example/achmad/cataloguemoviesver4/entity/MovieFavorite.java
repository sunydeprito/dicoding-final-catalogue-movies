package com.example.achmad.cataloguemoviesver4.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.example.achmad.cataloguemoviesver4.database.MovieContract.MovieColumns.MOVIE_ID;
import static com.example.achmad.cataloguemoviesver4.database.MovieContract.MovieColumns.MOVIE_IMAGE;
import static com.example.achmad.cataloguemoviesver4.database.MovieContract.MovieColumns.MOVIE_TITLE;
import static com.example.achmad.cataloguemoviesver4.database.MovieContract.getColumnInt;
import static com.example.achmad.cataloguemoviesver4.database.MovieContract.getColumnString;

/**
 * Created by Achmad on 23-08-2018
 **/

public class MovieFavorite implements Parcelable {

    private String id;
    private String title;
    private String image;

    public MovieFavorite(String id, String image, String title) {
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MovieFavorite(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.image);
    }

    private MovieFavorite(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<MovieFavorite> CREATOR = new Parcelable.Creator<MovieFavorite>() {
        @Override
        public MovieFavorite createFromParcel(Parcel source) {
            return new MovieFavorite(source);
        }

        @Override
        public MovieFavorite[] newArray(int size) {
            return new MovieFavorite[size];
        }
    };

    public MovieFavorite (Cursor cursor) {
        this.id = getColumnString(cursor, MOVIE_ID);
        this.title = getColumnString(cursor, MOVIE_TITLE);
        this.image = getColumnString(cursor, MOVIE_IMAGE);

    }
}
