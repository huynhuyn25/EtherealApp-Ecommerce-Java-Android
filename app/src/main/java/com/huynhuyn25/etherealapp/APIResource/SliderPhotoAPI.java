package com.huynhuyn25.etherealapp.APIResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huynhuyn25.etherealapp.Model.SliderPhoto;
import com.huynhuyn25.etherealapp.Model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SliderPhotoAPI {
    String Base_url ="http://192.168.1.239:8080/slider_photo/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-mm-dd")
            .create();

    SliderPhotoAPI sliderPhotoAPI = new Retrofit.Builder()
            .baseUrl(Base_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(SliderPhotoAPI.class);

    @GET("getAll")
    Call<ArrayList<SliderPhoto>> getAllSlider();
}
