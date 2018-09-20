package com.example.achmad.cataloguemoviesver4.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.achmad.cataloguemoviesver4.database.MovieContract.MovieColumns.TABLE_MOVIE;

/**
 * Created by Achmad on 23-08-2018
 **/

public class MovieContract {

    public static final class MovieColumns implements BaseColumns {

        public static final String TABLE_MOVIE = "movie";
        public static String MOVIE_ID = "movie_id";
        public static String MOVIE_TITLE = "title";
        public static String MOVIE_IMAGE = "image";
    }

    public static final String AUTHORITY = "com.example.achmad.cataloguemoviesver4";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
