package com.huynhuyn25.etherealapp.APIResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huynhuyn25.etherealapp.Model.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserAPI {
    String Base_url ="http://192.168.1.239:8080/user/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-mm-dd")
            .create();

    UserAPI userAPI = new Retrofit.Builder()
            .baseUrl(Base_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UserAPI.class);

    @GET("login")
    Call<User> getUserByNamePassword(@Query("username") String username,@Query("Password") String Password);

    @POST("signup")
    Call<User>addUser(@Body User user );

    @PUT("update")
    Call<User>updateUser(@Query("id") int id,@Body User user);
}
