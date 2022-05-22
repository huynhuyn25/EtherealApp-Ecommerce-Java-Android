package com.huynhuyn25.etherealapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huynhuyn25.etherealapp.APIResource.OrderAPI;
import com.huynhuyn25.etherealapp.Adapter.OrderRecycleViewAdapter;
import com.huynhuyn25.etherealapp.Adapter.ProductOrderRecycleViewAdapter;
import com.huynhuyn25.etherealapp.Interface.ItemClickInterface;
import com.huynhuyn25.etherealapp.Model.Cart;
import com.huynhuyn25.etherealapp.Model.Order;
import com.huynhuyn25.etherealapp.Model.Product;
import com.huynhuyn25.etherealapp.Model.ProductOrder;
import com.huynhuyn25.etherealapp.Model.ProductPhoto;
import com.huynhuyn25.etherealapp.Model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListActivity extends AppCompatActivity {
    private RecyclerView recyclerViewOrder;
    private OrderRecycleViewAdapter orderRecycleViewAdapter;
    ArrayList<Order> orders;
    User user;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "mypref";
    private static final String KEY_USER = "USER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        getWidget();

    }
    public void getWidget(){
        recyclerViewOrder = findViewById(R.id.list);
        sharedPreferences =getSharedPreferences(SHARE_PREF_NAME,MODE_PRIVATE);
        Gson gson = new Gson();
        sharedPreferences =getSharedPreferences(SHARE_PREF_NAME,MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString(KEY_USER,null),User.class);
        getListProduct();

    }
    public void getListProduct(){
        OrderAPI.orderAPI.listOrder(user.getIdUser()).enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                try {
                    if (!response.isSuccessful()&&response.body().size()==0) throw new Exception();
                    orders = response.body();
                    setRecyclerViewProductAdapter();
                }catch (Exception e){
                    Log.d("test","error_null");
                    Toast.makeText(OrderListActivity.this, "Loi", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable throwable) {
                Log.d("test","loi");
                throwable.printStackTrace();
                Toast.makeText(OrderListActivity.this, "Loi server", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void setRecyclerViewProductAdapter(){
        orderRecycleViewAdapter = new OrderRecycleViewAdapter(orders, new ItemClickInterface() {
            @Override
            public void OnclickItem(int pos) {
                Order order = orders.get(pos);
                Intent intent = new Intent(OrderListActivity.this, OrderDetailActivity.class);
                intent.putExtra("order", order);
                startActivity(intent);
            }
        });
        recyclerViewOrder.setAdapter(orderRecycleViewAdapter);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(OrderListActivity.this));
    }
}