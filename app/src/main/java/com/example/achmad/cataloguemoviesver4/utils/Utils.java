package com.example.achmad.cataloguemoviesver4.utils;

import com.example.achmad.cataloguemoviesver4.BuildConfig;

/**
 * Created by Achmad on 23-08-2018
 **/

public class Utils {
    public final static String DATE_FORMAT = "dd MMMM yyyy";
    public final static String DATE_FORMAT_DAY = "EEEE, MMM d, yyyy";
    public final static String BASE_URL = "http://api.themoviedb.org/3/";
    public final static String API_KEY = BuildConfig.ApiKey;
    public final static String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185";
    public final static String BASE_BACKDROP_URL = "http://image.tmdb.org/t/p/w780";
    public final static String MOVIE_DETAIL = "movie_detail";
    public static final String INTENT_SEARCH = "intent_search";
    public static final String INTENT_TAG = "tag";
    public static final String INTENT_DETAIL = "detail";
    public final static int NOTIFICATION_ID = 501;
    public final static int NOTIFICATION_ID_ = 502;
    public final static String NOTIFICATION_CHANNEL_ID = "601";
    public final static String BASE_BACKDROP_URL_WIDGET = "http://image.tmdb.org/t/p/w500";
    public final static String KEY_REMINDER_MESSAGE_Release = "reminderMessageRelease";
    public final static String KEY_REMINDER_MESSAGE_Daily = "reminderMessageDaily";
    public static final String EXTRA_MESSAGE_PREF = "message";
    public static final String EXTRA_TYPE_PREF = "type";
    public static final String TYPE_REMINDER_PREF = "reminderAlarm";
    public static final String EXTRA_MESSAGE_RECIEVE = "messageRelease";
    public static final String EXTRA_TYPE_RECIEVE = "typeRelease";
    public static final String TYPE_REMINDER_RECIEVE = "reminderAlarmRelease";
    public final static String PREF_NAME = "reminderPreferences";
    public final static String KEY_REMINDER_DAILY = "DailyReminder";
    public static final String KEY_HEADER_UPCOMING_REMINDER = "upcomingReminder";
    public static final String KEY_HEADER_DAILY_REMINDER = "dailyReminder";
    public static final String KEY_FIELD_UPCOMING_REMINDER = "checkedUpcoming";
    public static final String KEY_FIELD_DAILY_REMINDER = "checkedDaily";
    public static final String TOAST_ACTION = "com.example.achmad.cataloguemoviesver4.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.achmad.cataloguemoviesver4.EXTRA_ITEM";
}
