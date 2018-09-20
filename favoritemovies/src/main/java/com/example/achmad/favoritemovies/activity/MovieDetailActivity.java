package com.example.achmad.favoritemovies.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.achmad.favoritemovies.R;
import com.example.achmad.favoritemovies.Utils.DateFormat;
import com.example.achmad.favoritemovies.Utils.Utils;
import com.example.achmad.favoritemovies.database.DatabaseContract;
import com.example.achmad.favoritemovies.entity.MovieResult;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Modified by Achmad on 23-08-2018
 **/

public class MovieDetailActivity extends AppCompatActivity {
    @BindView(R.id.detail_overview_tv)
    TextView tvOverview;
    @BindView(R.id.image_detail)
    ImageView backDrop;
    @BindView(R.id.item_date_detail)
    TextView tvDate;
    @BindView(R.id.item_title_detail)
    TextView tvTitle;
    @BindView(R.id.movie_poster_detail)
    ImageView poster;
    @BindView(R.id.item_rating_detail)
    TextView tvRating;
    @BindView(R.id.item_vote_detail)
    TextView tvVote;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    String id, title;

    MovieResult movie;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.detail_movie));
        }


        movie = getIntent().getParcelableExtra(Utils.MOVIE_DETAIL);
        updateImage(movie);
        id = movie.getId().toString();
        title = movie.getTitle();

        if (isFavorite(movie.getId().toString())) {
            if (floatingActionButton != null) {
                floatingActionButton.setImageResource(R.drawable.ic_star);
            }
        }

        if (floatingActionButton != null) {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isFavorite(movie.getId().toString())) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseContract.MovieColumns.MOVIE_ID, id);
                        contentValues.put(DatabaseContract.MovieColumns.MOVIE_TITLE, title);
                        getContentResolver().insert(DatabaseContract.CONTENT_URI, contentValues);
                        floatingActionButton.setImageResource(R.drawable.ic_star);
                        Snackbar.make(v, "This movie has been add to your favorite", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Uri uri = DatabaseContract.CONTENT_URI;
                        uri = uri.buildUpon().appendPath(id).build();

                        getContentResolver().delete(uri, null, null);
                        floatingActionButton.setImageResource(R.drawable.ic_star_check);
                        Log.v("MovieDetail", uri.toString());
                        Snackbar.make(v, "This movie has been remove from your favorite", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });
        }

    }


    void updateImage(MovieResult movie) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(movie.getTitle());
        }
        Picasso.get()
                .load(Utils.BASE_POSTER_URL + movie.getPosterPath())
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(poster);

        Picasso.get()
                .load(Utils.BASE_BACKDROP_URL + movie.getBackdropPath())
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(backDrop);
        tvTitle.setText(movie.getTitle());
        tvVote.setText(getResources().getString(R.string.vote,
                movie.getVoteCount()));
        tvOverview.setText(movie.getOverview());
        tvDate.setText(getResources().getString(R.string.release_date,
                DateFormat.getDateDay(movie.getReleaseDate())));
        tvRating.setText(getResources().getString(R.string.rating,
                movie.getVoteAverage().toString()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean isFavorite(String id) {
        String selection = " movie_id = ?";
        String[] selectionArgs = {id};
        String[] projection = {DatabaseContract.MovieColumns.MOVIE_ID};
        Uri uri = DatabaseContract.CONTENT_URI;
        uri = uri.buildUpon().appendPath(id).build();

        Cursor cursor = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            cursor = getContentResolver().query(uri, projection,
                    selection, selectionArgs, null, null);
        }


        boolean exists = false;
        if (cursor != null) {
            exists = (Objects.requireNonNull(cursor).getCount() > 0);
        }
        if (cursor != null) {
            cursor.close();
        }
        return exists;
    }
}
