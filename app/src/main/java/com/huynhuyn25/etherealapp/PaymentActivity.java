package com.huynhuyn25.etherealapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huynhuyn25.etherealapp.APIResource.CartAPI;
import com.huynhuyn25.etherealapp.APIResource.OrderAPI;
import com.huynhuyn25.etherealapp.APIResource.OrderInforAPI;
import com.huynhuyn25.etherealapp.APIResource.UserAPI;
import com.huynhuyn25.etherealapp.Adapter.ProductCartRecycleViewAdapter;
import com.huynhuyn25.etherealapp.Adapter.ProductOrderRecycleViewAdapter;
import com.huynhuyn25.etherealapp.Interface.ItemClickInterface;
import com.huynhuyn25.etherealapp.Model.Address;
import com.huynhuyn25.etherealapp.Model.Cart;
import com.huynhuyn25.etherealapp.Model.Order;
import com.huynhuyn25.etherealapp.Model.OrderInfor;
import com.huynhuyn25.etherealapp.Model.Product;
import com.huynhuyn25.etherealapp.Model.ProductCart;
import com.huynhuyn25.etherealapp.Model.ProductOrder;
import com.huynhuyn25.etherealapp.Model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    private Cart cart;
    private RecyclerView recyclerViewProductCart;
    private ProductOrderRecycleViewAdapter productOrderRecycleViewAdapter;
    ArrayList<ProductOrder> listProductOrder= new ArrayList<>();
    List<ProductCart> listProductCart;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "mypref";
    private static final String KEY_CART = "cartID";
    private Button btnDathang;
    private OrderInfor orderInfor= new OrderInfor();
    private CardView cvOrderInfor;
    private int idCart;
    private TextView tvOrderInfor,tvTotal;
    private Order order = new Order();
    double total=0;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent = getIntent();
        cart = (Cart) intent.getSerializableExtra("cart");
        Log.d("TAG", "onCreate: "+Double.toString(cart.getTotal()));
        getWidget();
        getListProductOrder();
        setEvent();

    }
    public void getWidget(){
        recyclerViewProductCart = findViewById(R.id.list);
        btnDathang = findViewById(R.id.btn_dat_hang);
        cvOrderInfor =findViewById(R.id.cv_order_infor);
        tvOrderInfor =findViewById(R.id.tv_orderInfor);
        tvTotal =findViewById(R.id.tv_total);
    }
    public void getListProductOrder(){
        listProductCart = new ArrayList<>();
        listProductCart = cart.getListProductCart();
        for(ProductCart productCart:listProductCart){
            ProductOrder productOrder = new ProductOrder(productCart.getProduct(),productCart.getSoLuong());
            listProductOrder.add(productOrder);
            total+=productOrder.getProduct().getPrice()*productOrder.getSoLuong();
        }
        tvTotal.setText("Tổng tiền: "+Double.toString(total));
        setRecyclerViewProductAdapter();
        setEvent();

    }
    public void setRecyclerViewProductAdapter(){
        productOrderRecycleViewAdapter = new ProductOrderRecycleViewAdapter((ArrayList<ProductOrder>) listProductOrder, new ItemClickInterface() {
            @Override
            public void OnclickItem(int pos) {
                Product product = listProductOrder.get(pos).getProduct();
                Intent intent = new Intent(PaymentActivity.this, ProductDetailActivity.class);
                intent.putExtra("product", product);
                PaymentActivity.this.startActivity(intent);
            }
        });
        recyclerViewProductCart.setAdapter(productOrderRecycleViewAdapter);
        recyclerViewProductCart.setLayoutManager(new LinearLayoutManager(PaymentActivity.this));
    }
    public void setEvent(){
        cvOrderInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(PaymentActivity.this);
                dialog.setContentView(R.layout.custom_dialog_address);
                EditText edName = dialog.findViewById(R.id.ed_name);
                EditText edSDT = dialog.findViewById(R.id.ed_sdt);
                EditText edTinh = dialog.findViewById(R.id.ed_city);
                EditText edQuan = dialog.findViewById(R.id.ed_district);
                EditText edPhuong = dialog.findViewById(R.id.ed_ward);
                EditText edDuong = dialog.findViewById(R.id.ed_strest);
                EditText edToaNha = dialog.findViewById(R.id.ed_building);
                EditText edSoNha = dialog.findViewById(R.id.ed_number_home);
                Button btnLuu = dialog.findViewById(R.id.btn_luu);
                btnLuu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if(edName.getText().toString().equals("")||edSDT.getText().toString().equals("")||
                                    edTinh.getText().toString().equals("")||edQuan.getText().toString().equals("")||
                                    edPhuong.getText().toString().equals("")||edDuong.getText().toString().equals("")) throw new Exception();
                            Address address = new Address(edTinh.getText().toString(),edQuan.getText().toString(),edPhuong.getText().toString(),edDuong.getText().toString(),
                                    edToaNha.getText().toString().equals("")?"":edToaNha.getText().toString(),
                                    edSoNha.getText().toString().equals("")?"":edSoNha.getText().toString());
                            orderInfor.setAddress(address);
                            orderInfor.setSdt(edSDT.getText().toString());
                            orderInfor.setTenNguoiNhan(edName.getText().toString());
                            Log.d("TAG", "onClick: "+orderInfor.getTenNguoiNhan());
                            tvOrderInfor.setText("Thông tin đặt hàng:\nTên người nhân: "+orderInfor.getTenNguoiNhan()+"\nSố điện thoại: "+orderInfor.getSdt()+"\nĐịa chỉ: "+orderInfor.getAddress().toString());
//                            tvTotal.setText(Double.toString(total));
                            dialog.dismiss();
                        }catch (Exception e){
                            Log.d("TAG", "onClick: loi");
                            e.printStackTrace();
                            Toast.makeText(PaymentActivity.this, "Chua dien du thong tin", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                dialog.show();
            }
        });
        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderInforAPI.orderInforAPI.addOrderInfor(orderInfor).enqueue(new Callback<OrderInfor>() {
                    @Override
                    public void onResponse(Call<OrderInfor> call, Response<OrderInfor> response) {
                        try {
                            if(!response.isSuccessful()||response.body().getTenNguoiNhan()==null) throw new Exception();
                            orderInfor = response.body();
                            addOrder();
                            Log.d("TAG", "onResponse: order_infor");
                        }catch (Exception e){
                            Log.d("test","error_null");
                            Toast.makeText(PaymentActivity.this, "Loi", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderInfor> call, Throwable throwable) {
                        Log.d("test","loi");
                        throwable.printStackTrace();
                        Toast.makeText(PaymentActivity.this, "Loi server", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    private void addOrder() {
        String KEY_USER = "USER";
        Gson gson = new Gson();
        SharedPreferences sharedPreferences =getSharedPreferences(SHARE_PREF_NAME,MODE_PRIVATE);
        User user = gson.fromJson(sharedPreferences.getString(KEY_USER,null),User.class);
        OrderAPI.orderAPI.addOrder(user.getIdUser(),orderInfor.getIdOrderInfor(),"update",total).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                try {

                    order =response.body();
                    addProduct();
                    Log.d("TAG", "onResponse: order"+order.getTotal());
                }catch (Exception e){
                    Log.d("test","error_null");
                    Toast.makeText(PaymentActivity.this, "Loi", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable throwable) {
                Log.d("test","loi");
                throwable.printStackTrace();
                Toast.makeText(PaymentActivity.this, "Loi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addProduct() {

        for(ProductOrder productOrder:listProductOrder){
            OrderAPI.orderAPI.addProduct(order.getIdOrder(),productOrder.getProduct().getIdProduct(),productOrder.getSoLuong()).enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    try {
                        if(!response.isSuccessful()||response.body().getStatus()==null) throw new Exception();
                        i++;
                        Log.d("TAG", "onResponse: "+i);
                        if(i==listProductOrder.size()){
                            OrderAPI.orderAPI.updateStatus(order.getIdOrder(),"Đã tiếp nhận").enqueue(new Callback<Order>() {
                                @Override
                                public void onResponse(Call<Order> call, Response<Order> response) {
                                    try {
                                        if(!response.isSuccessful()||response.body().getStatus()==null) throw new Exception();
                                        Log.d("TAG", "onResponse: "+response.body().toString());
                                        order=response.body();
                                        Intent intent = new Intent(PaymentActivity.this, SuccessActivity.class);
                                        intent.putExtra("order", order);
                                        PaymentActivity.this.startActivity(intent);
                                    }catch (Exception e){
                                        Log.d("test","error_null");
                                        Toast.makeText(PaymentActivity.this, "Loi", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Order> call, Throwable throwable) {
                                    Log.d("test","loi");
                                    throwable.printStackTrace();
                                    Toast.makeText(PaymentActivity.this, "Loi server", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }catch (Exception e){
                        Log.d("test","error_null");
                        Toast.makeText(PaymentActivity.this, "Loi", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Order> call, Throwable throwable) {
                    Log.d("test","loi");
                    throwable.printStackTrace();
                    Toast.makeText(PaymentActivity.this, "Loi server", Toast.LENGTH_LONG).show();
                }
            });


        }


    }

}