package com.example.achmad.cataloguemoviesver4.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.achmad.cataloguemoviesver4.R;
import com.example.achmad.cataloguemoviesver4.entity.MovieResult;
import com.example.achmad.cataloguemoviesver4.utils.Utils;

import java.util.concurrent.ExecutionException;

import static com.example.achmad.cataloguemoviesver4.database.MovieContract.CONTENT_URI;
import static com.example.achmad.cataloguemoviesver4.utils.Utils.EXTRA_ITEM;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    int mAppWidgetId;
    private Cursor cursor;
    MovieResult movieResult;

    public StackRemoteViewFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public void onCreate() {
        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onDataSetChanged() {
        final long token = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(token);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.movie_widget_item);
        if (cursor.moveToPosition(position)) {
            movieResult = new MovieResult(cursor);
            Bitmap bmp;
            try {
                bmp = Glide.with(mContext)
                        .asBitmap()
                        .load(Utils.BASE_BACKDROP_URL_WIDGET + movieResult.getPosterPath())
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                rv.setImageViewBitmap(R.id.img_widget, bmp);
                rv.setTextViewText(R.id.tv_movie_title, movieResult.getTitle());
            } catch (InterruptedException | ExecutionException e) {
                Log.d("Widget Load Error", "error");
            }
        }

        Bundle extras = new Bundle();
        extras.putInt(EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.img_widget, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
