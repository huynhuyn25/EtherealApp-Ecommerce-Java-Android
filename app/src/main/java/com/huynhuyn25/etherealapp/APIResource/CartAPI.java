package com.huynhuyn25.etherealapp.APIResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huynhuyn25.etherealapp.Model.Cart;
import com.huynhuyn25.etherealapp.Model.Product;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartAPI {
    String Base_url ="http://192.168.1.239:8080/cart/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-mm-dd")
            .create();

    CartAPI cartAPI = new Retrofit.Builder()
            .baseUrl(Base_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CartAPI.class);

    @POST("add")
    Call<Cart>addProduct(@Query("idCart") int idCart, @Query("idProduct") int idProduct, @Query("soLuong") int soLuong);

    @GET("get")
    Call<Cart>getCart(@Query("id") int idCart);

    @PUT("update")
    Call<Cart>updateCart(@Query("idCart") int idCart, @Query("idProductCart") int idProductCart, @Query("soLuong") int soLuong);

    @DELETE("delete/{id}")
    Call<Boolean>deleteCart(@Path("id") int id);
}
