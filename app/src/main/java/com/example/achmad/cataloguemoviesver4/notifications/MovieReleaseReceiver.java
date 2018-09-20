package com.example.achmad.cataloguemoviesver4.notifications;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.achmad.cataloguemoviesver4.BuildConfig;
import com.example.achmad.cataloguemoviesver4.R;
import com.example.achmad.cataloguemoviesver4.activity.MainActivity;
import com.example.achmad.cataloguemoviesver4.activity.MovieDetailActivity;
import com.example.achmad.cataloguemoviesver4.entity.Movie;
import com.example.achmad.cataloguemoviesver4.entity.MovieResult;
import com.example.achmad.cataloguemoviesver4.rest.MovieClient;
import com.example.achmad.cataloguemoviesver4.rest.MovieInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.achmad.cataloguemoviesver4.utils.Utils.BASE_URL;
import static com.example.achmad.cataloguemoviesver4.utils.Utils.EXTRA_MESSAGE_RECIEVE;
import static com.example.achmad.cataloguemoviesver4.utils.Utils.EXTRA_TYPE_RECIEVE;
import static com.example.achmad.cataloguemoviesver4.utils.Utils.NOTIFICATION_ID_;


public class MovieReleaseReceiver extends BroadcastReceiver {
    public List<MovieResult> listMovie = new ArrayList<>();

    public MovieReleaseReceiver() {

    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        MovieInterface movieInterface = MovieClient.getClient(BASE_URL).create(MovieInterface.class);
        Call<Movie> call = movieInterface.getUpcomingMovie(BuildConfig.ApiKey);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                listMovie = response.body().getResults();
                List<MovieResult> items = response.body().getResults();
                int index = new Random().nextInt(items.size());
                MovieResult item = items.get(index);
                int notifId = 503;
                String title = items.get(index).getTitle();
                String message = items.get(index).getOverview();
                sendNotification(context, title, message, notifId);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("getUpCommingMovie", "onFailure: " + t.toString());
            }
        });
    }

    private void sendNotification(Context context, String title, String desc, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_movie)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true)
                .setSound(uriTone);
        if (notificationManager != null) {
            notificationManager.notify(id, builder.build());
        }
    }

    public void setAlarm(Context context, String type, String time, String message) {
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MovieReleaseReceiver.class);
        intent.putExtra(EXTRA_MESSAGE_RECIEVE, message);
        intent.putExtra(EXTRA_TYPE_RECIEVE, type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID_, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, R.string.on_movie_release_reminder, Toast.LENGTH_SHORT).show();
    }


    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MovieReleaseReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID_, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, R.string.off_movie_release_reminder, Toast.LENGTH_SHORT).show();
    }


}