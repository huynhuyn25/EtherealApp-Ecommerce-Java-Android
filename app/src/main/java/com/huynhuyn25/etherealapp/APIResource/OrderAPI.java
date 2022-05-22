package com.huynhuyn25.etherealapp.APIResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huynhuyn25.etherealapp.Model.Cart;
import com.huynhuyn25.etherealapp.Model.Order;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface OrderAPI {
    String Base_url ="http://192.168.1.239:8080/order/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-mm-dd")
            .create();

    OrderAPI orderAPI = new Retrofit.Builder()
            .baseUrl(Base_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(OrderAPI.class);

    @POST("add")
    Call<Order> addOrder(@Query("idUser") int idUser,@Query("idOrderInfor") int idOrderInfor,@Query("status") String status,@Query("tongTien") double tongTien);

//    @GET("get")
//    Call<Order>getCart(@Query("id") int idCart);

    @PUT("addProduct")
    Call<Order> addProduct(@Query("idOrder") int idOrder, @Query("idProduct") int idProduct, @Query("soLuong") int soLuong);

    @PUT("update")
    Call<Order> updateStatus(@Query("idOrder") int idOrder,@Query("status") String status);

    @GET("listOrder")
    Call<ArrayList<Order>> listOrder(@Query("id") int id);

    @GET("getAll")
    Call<ArrayList<Order>> getAllOrder();
}
