package com.example.achmad.cataloguemoviesver4.activity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.achmad.cataloguemoviesver4.R;
import com.example.achmad.cataloguemoviesver4.adapter.ViewPagerAdapter;
import com.example.achmad.cataloguemoviesver4.database.MovieContract;
import com.example.achmad.cataloguemoviesver4.entity.MovieFavorite;
import com.example.achmad.cataloguemoviesver4.fragment.NowPlayingFragment;
import com.example.achmad.cataloguemoviesver4.fragment.UpcomingFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.achmad.cataloguemoviesver4.utils.Utils.INTENT_DETAIL;
import static com.example.achmad.cataloguemoviesver4.utils.Utils.INTENT_SEARCH;
import static com.example.achmad.cataloguemoviesver4.utils.Utils.INTENT_TAG;

/**
 * Modified by Achmad on 23-08-2018
 **/

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    CircleImageView profileCircleImageView;
    String profileImageUrl = "https://lh3.googleusercontent.com/-ZqGz9LNXGTM/UxF4-Qx_CjI/AAAAAAAAABE/ltYj7V8XMyEqVrWwwbS4U8GOuRbDFbhDwCEwYBhgL/w139-h140-p/DSC_0095.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setUpViewpager(viewPager);
        tab.setupWithViewPager(viewPager);
         
         setActionBar("Welcome to Movie Catalogue);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        profileCircleImageView = navigationView.getHeaderView(0).findViewById(R.id.imageProfile);
        Picasso.get()
                .load(profileImageUrl)
                .into(profileCircleImageView);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra(INTENT_SEARCH, query);
                intent.putExtra(INTENT_TAG, "search");
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent mIntent = new Intent(this, SettingActivity.class);
            startActivity(mIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();

        } else if (id == R.id.nav_favorite) {
            ArrayList<MovieFavorite> movieFavoriteArrayList = new ArrayList<>();
            Cursor cursor = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                cursor = getContentResolver().query(MovieContract.CONTENT_URI, null,
                        null, null, null, null);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(cursor).moveToFirst();
            }
            MovieFavorite favorite;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (Objects.requireNonNull(cursor).getCount() > 0) {
                    do {
                        favorite = new MovieFavorite(cursor.getString(cursor.getColumnIndexOrThrow(
                                MovieContract.MovieColumns.MOVIE_ID)));
                        movieFavoriteArrayList.add(favorite);
                        cursor.moveToNext();
                    } while (!cursor.isAfterLast());
                }
            }
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra(INTENT_DETAIL, movieFavoriteArrayList);
            intent.putExtra(INTENT_TAG, "detail");
            startActivity(intent);
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void setUpViewpager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.populateFragment(new NowPlayingFragment(), getString(R.string.now_playing));
        adapter.populateFragment(new UpcomingFragment(), getString(R.string.upcoming));
        viewPager.setAdapter(adapter);
    }
    
    private void setActionBar(String title){
		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle(title);
		}
	}

}
