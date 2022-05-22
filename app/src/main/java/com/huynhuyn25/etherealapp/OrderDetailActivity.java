package com.huynhuyn25.etherealapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huynhuyn25.etherealapp.APIResource.OrderAPI;
import com.huynhuyn25.etherealapp.APIResource.ProductAPI;
import com.huynhuyn25.etherealapp.Adapter.ProductOrderRecycleViewAdapter;
import com.huynhuyn25.etherealapp.Adapter.ProductRecycleViewAdapter;
import com.huynhuyn25.etherealapp.Interface.ItemClickInterface;
import com.huynhuyn25.etherealapp.Model.Order;
import com.huynhuyn25.etherealapp.Model.Product;
import com.huynhuyn25.etherealapp.Model.ProductOrder;
import com.huynhuyn25.etherealapp.Model.ProductPhoto;
import com.huynhuyn25.etherealapp.Model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerViewProductOrder;
    private ProductOrderRecycleViewAdapter productOrderRecycleViewAdapter;
    ArrayList<Product> listProduct;
    ArrayList<ProductPhoto> listProductPhoto;
    ArrayList<ProductOrder> productOrders;
    Order order;
    ArrayAdapter<String> adapterStatus;
    User user;
    private Button btnKhieunai,btnBack;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "mypref";
    private static final String KEY_USER = "USER";
    private TextView tvID,tvOrderInfor,tvTotal,tvStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");
        Log.d("TAG", "onCreate: "+order.getListProductOrder().get(0).getProduct().getName());
        Gson gson = new Gson();
        sharedPreferences =getSharedPreferences(SHARE_PREF_NAME,MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString(KEY_USER,null), User.class);
        getWidget();
        getListProduct();
        setEvent();


    }
    public void getWidget(){
        recyclerViewProductOrder = findViewById(R.id.list_product_order);
        tvID =findViewById(R.id.tv_idOrder);
        tvOrderInfor = findViewById(R.id.tv_orderInfor);
        tvTotal =findViewById(R.id.tv_total);
        tvStatus = findViewById(R.id.tv_status);
        btnKhieunai= findViewById(R.id.btn_khieunai);
        if(user.getVaitro()==0){
            btnKhieunai.setText("Đổi trạng thái");
        }else{
            btnKhieunai.setText("Khiếu nại");
        }
        btnBack =findViewById(R.id.btn_back);

    }
    public void getListProduct(){
        productOrders = new ArrayList<>();
        productOrders=order.getListProductOrder();
        setRecyclerViewProductAdapter();
        tvID.setText("Đơn hàng số: "+order.getIdOrder());
        tvOrderInfor.setText("Thông tin đặt hàng:\nTên người nhân: "+order.getOrderInfor().getTenNguoiNhan()+"\nSố điện thoại: "+order.getOrderInfor().getSdt()+"\nĐịa chỉ: "+order.getOrderInfor().getAddress().toString());
        tvTotal.setText("Tổng tiền: "+Double.toString(order.getTotal()));
        tvStatus.setText("Trạng thái đơn hàng: "+order.getStatus());

    }
    public void setRecyclerViewProductAdapter(){
        productOrderRecycleViewAdapter = new ProductOrderRecycleViewAdapter(productOrders, new ItemClickInterface() {
            @Override
            public void OnclickItem(int pos) {
                Product product = listProduct.get(pos);
                Intent intent = new Intent(OrderDetailActivity.this, ProductDetailActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });
        recyclerViewProductOrder.setAdapter(productOrderRecycleViewAdapter);
        recyclerViewProductOrder.setLayoutManager(new LinearLayoutManager(OrderDetailActivity.this));
    }
    public void setEvent(){
        if(user.getVaitro()==0){
            btnKhieunai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(OrderDetailActivity.this);
                    dialog.setContentView(R.layout.custom_dialog_status);
                    Spinner spinner = dialog.findViewById(R.id.spn_status);
                    ArrayList<String> listStatus=new ArrayList<>();
                    listStatus.add("Đã tiếp nhận");
                    listStatus.add("Đang vận chuyển");
                    listStatus.add("Hoàn thành");
                    listStatus.add("Đã hủy");
                    adapterStatus = new ArrayAdapter<String>(OrderDetailActivity.this,R.layout.support_simple_spinner_dropdown_item,listStatus);

                    spinner.setAdapter(adapterStatus);
                    Button btn = dialog.findViewById(R.id.btn_status);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            OrderAPI.orderAPI.updateStatus(order.getIdOrder(),spinner.getSelectedItem().toString()).enqueue(new Callback<Order>() {
                                @Override
                                public void onResponse(Call<Order> call, Response<Order> response) {
                                    try {
                                        if(!response.isSuccessful()||response.body().getStatus()==null) throw new Exception();
                                        Log.d("TAG", "onResponse: "+response.body().toString());
                                        order=response.body();
                                        tvStatus.setText("Trạng thái đơn hàng: "+order.getStatus());
                                        dialog.dismiss();
                                    }catch (Exception e){
                                        Log.d("test","error_null");
                                        Toast.makeText(OrderDetailActivity.this, "Loi", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Order> call, Throwable throwable) {
                                    Log.d("test","loi");
                                    throwable.printStackTrace();
                                    Toast.makeText(OrderDetailActivity.this, "Loi server", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    });
                    dialog.show();
                }
            });
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}