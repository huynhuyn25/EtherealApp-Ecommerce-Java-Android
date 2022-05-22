package com.huynhuyn25.etherealapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huynhuyn25.etherealapp.APIResource.ProductAPI;
import com.huynhuyn25.etherealapp.Adapter.ProductRecycleViewAdapter;
import com.huynhuyn25.etherealapp.Interface.ItemClickInterface;
import com.huynhuyn25.etherealapp.Model.Cart;
import com.huynhuyn25.etherealapp.Model.Order;
import com.huynhuyn25.etherealapp.Model.Product;
import com.huynhuyn25.etherealapp.Model.ProductPhoto;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuccessActivity extends AppCompatActivity {
    private RecyclerView recyclerViewProduct;
    ArrayList<Product> listProduct;
    private Button btnChitiet,btnHome;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        getWidget();
        getListProduct();
        setEvent();

    }
    public void getWidget(){
        btnChitiet =findViewById(R.id.btn_chitiet);
        btnHome =findViewById(R.id.btn_back);
        recyclerViewProduct = findViewById(R.id.list_product_more);
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");
        Log.d("TAG", "getWidget: "+order.getStatus());
    }
    public void setRecyclerViewProductAdapter(){
        ProductRecycleViewAdapter productRecycleViewAdapter = new ProductRecycleViewAdapter(listProduct, new ItemClickInterface() {
            @Override
            public void OnclickItem(int pos) {
                Product product = listProduct.get(pos);
                Intent intent = new Intent(SuccessActivity.this, ProductDetailActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });
        recyclerViewProduct.setAdapter(productRecycleViewAdapter);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(SuccessActivity.this,2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }
    public void getListProduct(){
        listProduct = new ArrayList<>();
        ProductAPI.productAPI.getAllProduct().enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                try {
                    if(!response.isSuccessful()||response.body().size()==0)
                        throw new Exception();
                    listProduct=response.body();
                    setRecyclerViewProductAdapter();
                }catch (Exception e){
                    Log.d("test","error_null");
                    Toast.makeText(SuccessActivity.this, "Khong co san pham ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable throwable) {
                Log.d("test","loi");
                throwable.printStackTrace();
                Toast.makeText(SuccessActivity.this, "Loi server", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void setEvent(){
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnChitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessActivity.this, OrderDetailActivity.class);
                intent.putExtra("order", order);
                startActivity(intent);
            }
        });
    }
}