package com.example.achmad.cataloguemoviesver4.rest;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.achmad.cataloguemoviesver4.utils.Utils.BASE_URL;

/**
 * Created by Achmad on 23-08-2018
 **/

public class MovieClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
