package com.huynhuyn25.etherealapp.APIResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huynhuyn25.etherealapp.Model.Cart;
import com.huynhuyn25.etherealapp.Model.OrderInfor;
import com.huynhuyn25.etherealapp.Model.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderInforAPI {

    String Base_url ="http://192.168.1.239:8080/order_infor/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-mm-dd")
            .create();

    OrderInforAPI orderInforAPI = new Retrofit.Builder()
            .baseUrl(Base_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(OrderInforAPI.class);

    @POST("add")
    Call<OrderInfor> addOrderInfor(@Body OrderInfor orderInfor);

    @GET("get")
    Call<OrderInfor> getOrderInfor(@Query("id") int idOrderInfor);
}
