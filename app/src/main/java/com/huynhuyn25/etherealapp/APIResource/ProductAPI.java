package com.huynhuyn25.etherealapp.APIResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huynhuyn25.etherealapp.Model.Product;
import com.huynhuyn25.etherealapp.Model.SliderPhoto;
import com.huynhuyn25.etherealapp.Model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductAPI {
    String Base_url ="http://192.168.1.239:8080/product/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-mm-dd")
            .create();

    ProductAPI productAPI = new Retrofit.Builder()
            .baseUrl(Base_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductAPI.class);

    @GET("getAll")
    Call<ArrayList<Product>> getAllProduct();

    @GET("get")
    Call<Product> getOneProductByID(@Query("id") int id);

    @POST("add")
    Call<Product>addProduct(@Body Product product);

    @PUT("update")
    Call<Product>updateProduct(@Query("id") int id,@Body Product product);

    @DELETE("delete/{id}")
    Call<Boolean>deleteProduct(@Path("id") int id);
}
