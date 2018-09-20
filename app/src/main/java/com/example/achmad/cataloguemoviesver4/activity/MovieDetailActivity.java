package com.example.achmad.cataloguemoviesver4.activity;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.codesgood.views.JustifiedTextView;
import com.example.achmad.cataloguemoviesver4.R;
import com.example.achmad.cataloguemoviesver4.database.MovieContract;
import com.example.achmad.cataloguemoviesver4.entity.MovieResult;
import com.example.achmad.cataloguemoviesver4.utils.DateFormat;
import com.example.achmad.cataloguemoviesver4.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Modified by Achmad on 23-08-2018
 **/

public class MovieDetailActivity extends AppCompatActivity {
    @BindView(R.id.detail_movie_overview)
    JustifiedTextView tvOverview;
    @BindView(R.id.detail_movie_image)
    ImageView backDrop;
    @BindView(R.id.detail_movie_release_date)
    TextView tvDate;
    @BindView(R.id.detail_movie_title)
    TextView tvTitle;
    @BindView(R.id.detail_movie_poster)
    ImageView poster;
    @BindView(R.id.detail_movie_vote)
    TextView tvVote;
    @BindView(R.id.detail_movie_rating)
    TextView tvRating;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.button_mark_as_favorite)
    Button buttonMarkAsFavorite;
    @BindView(R.id.button_remove_from_favorites)
    Button buttonRemoveFromFavorites;
    String id, title, image;
    MovieResult movie;
    private boolean isfavorite;
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

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
        id = movie.getId();
        title = movie.getTitle();
        image = movie.getBackdropPath();

        if (isFavorite(id)) {
            isfavorite = true;
            buttonRemoveFromFavorites.setVisibility(View.VISIBLE);
            buttonMarkAsFavorite.setVisibility(View.GONE);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void saveInFavorite() {
        if (isfavorite) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.MovieColumns.MOVIE_ID, id);
            contentValues.put(MovieContract.MovieColumns.MOVIE_TITLE, title);
            contentValues.put(MovieContract.MovieColumns.MOVIE_IMAGE, image);
            Uri uri = getContentResolver().insert(MovieContract.CONTENT_URI, contentValues);
            if (uri != null) {
                Log.d(TAG, "Uri " + uri);
            }
        } else {
            Uri uri = MovieContract.CONTENT_URI.buildUpon().appendPath(id).build();
            getContentResolver().delete(uri, null, null);
        }
    }

    private boolean isFavorite(String id) {
        Uri uri = MovieContract.CONTENT_URI.buildUpon().appendPath(id).build();
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        return cursor.moveToFirst();
    }

    public void onClickButton(View view) {
        if (!isfavorite) {
            isfavorite = true;
            buttonRemoveFromFavorites.setVisibility(View.VISIBLE);
            buttonMarkAsFavorite.setVisibility(View.GONE);
            Snackbar.make(view, "This movie has been add to your favorite", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else {
            isfavorite = false;
            buttonMarkAsFavorite.setVisibility(View.VISIBLE);
            buttonRemoveFromFavorites.setVisibility(View.GONE);
            Snackbar.make(view, "This movie has been remove from your favorite", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        saveInFavorite();

    }

    @SuppressLint("StringFormatMatches")
    void updateImage(MovieResult movie) {
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
        tvRating.setText(getResources().getString(R.string.rating,
                movie.getVoteAverage()));
        tvOverview.setText(movie.getOverview());
        tvDate.setText(getResources().getString(R.string.release_date,
                DateFormat.getDateDay(movie.getReleaseDate())));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
