# Final Catalogue Movies
This is the last project of MADE course in dicoding.com academy. This project is a continuation of the project [catalogue movie 1](https://github.com/sunydeprito/CatalogueMovie), [catalogue movie 2](https://github.com/sunydeprito/CatalogueMoviesVer2) and [catalogue movie 3](https://github.com/sunydeprito/catalogue-movie-ver3). The additional features of this last project are stackwidget and notification alarm.

## Screenshot
![20180920_192324](https://user-images.githubusercontent.com/26306746/45818071-cc514180-bd0a-11e8-8a71-0c2aa5fe8683.gif)

## Requirements or features in application
1. Add a movie's favorite widget using StackWidget.
2. Add scheduler to:
    * Daily Reminder, remind you to return to the catalog movie. Daily reminder must always run every 7 o'clock in the morning.
    * Release Today Reminder, featuring a release film today. Release reminder must always run every 8 o'clock in the morning.
3. Must be able to adjust portrait and landscape orientation.
4. Must be able to keep the data that has been loaded / displayed.

## API
[TheMovideDB](https://www.themoviedb.org/) - A movie lists API

1. to get movie data - api.themoviedb.org/3/search/movie?api_key=(Api Key)&language=en-US&query=(Movie Name)
2. to get movie poster - http://<i></i>image.tmdb.org/t/p/ 

   There are several sizes that you can use w92, w154, w185, w342, w500, w780, and original.
3. to get nowplaying movie data - api.themoviedb.org/3/movie/now_playing?api_key=(API Key)&language=en-US
4. to get upcoming movie data - api.themoviedb.org/3/movie/upcoming?api_key=(API key)&language=en-US

## Library
1. [Picasso](http://square.github.io/picasso/) - A powerful image downloading and caching library for Android
2. [JustifiedTextView](https://github.com/amilcar-sr/JustifiedTextView) - JustifiedTextView is an Android View that justifies the Text!
3. [CircleImageView by hdodenhof](https://github.com/hdodenhof/CircleImageView) - A fast circular ImageView perfect for profile images
4. [Butter Knife](http://jakewharton.github.io/butterknife/) - Field and method binding for Android views
5. [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java
6. [Glide](https://github.com/bumptech/glide) - An image loading and caching library for Android focused on smooth scrolling


# Please be Noted
For those who still do the project in MADE course please use this as reference only. Hope this will help you to develop more project.
