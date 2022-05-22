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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huynhuyn25.etherealapp.APIResource.CartAPI;
import com.huynhuyn25.etherealapp.APIResource.ProductAPI;
import com.huynhuyn25.etherealapp.Adapter.OrderRecycleViewAdapter;
import com.huynhuyn25.etherealapp.Adapter.ProductCartRecycleViewAdapter;
import com.huynhuyn25.etherealapp.Adapter.ProductRecycleViewAdapter;
import com.huynhuyn25.etherealapp.Interface.ItemClickInterface;
import com.huynhuyn25.etherealapp.Model.Cart;
import com.huynhuyn25.etherealapp.Model.Order;
import com.huynhuyn25.etherealapp.Model.Product;
import com.huynhuyn25.etherealapp.Model.ProductCart;
import com.huynhuyn25.etherealapp.Model.ProductOrder;
import com.huynhuyn25.etherealapp.Model.ProductPhoto;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCartActivity extends AppCompatActivity {
    private RecyclerView recyclerViewProductCart;
    private ProductCartRecycleViewAdapter productCartRecycleViewAdapter;
    ArrayList<Product> listProduct;
    ArrayList<ProductCart> listProductCart;
    private Cart cart;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "mypref";
    private static final String KEY_CART = "cartID";
    private Button btnDathang;
    int soLuong=1;
    private TextView tvTotal;
    private int idCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart);
        getWidget();
        sharedPreferences =getSharedPreferences(SHARE_PREF_NAME,MODE_PRIVATE);
        idCart =Integer.parseInt(sharedPreferences.getString(KEY_CART,null));
        getListProductCart();

    }
    public void getWidget(){
        recyclerViewProductCart = findViewById(R.id.list);
        btnDathang = findViewById(R.id.btn_dat_hang);
        tvTotal =findViewById(R.id.tv_total);
    }
    public void getListProductCart(){
        listProductCart = new ArrayList<>();
        CartAPI.cartAPI.getCart(idCart).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                try {
                    if(!response.isSuccessful()||response.body()==null)
                        throw new Exception();
                    cart=response.body();
                    listProductCart=(ArrayList)cart.getListProductCart();
                    setRecyclerViewProductAdapter();
                    if(cart.getListProductCart().size()!=0){
                        tvTotal.setText("Tổng tiền: "+Double.toString(cart.getTotal()));
                    }
                    else{
                        tvTotal.setText("Tổng tiền: 0");
                    }
                    setEvent();
                }catch (Exception e){
                    Log.d("test","error_null");
                    Toast.makeText(ProductCartActivity.this, "Khong co san pham ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable throwable) {
                Log.d("test","loi");
                throwable.printStackTrace();
                Toast.makeText(ProductCartActivity.this, "Loi server", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void setRecyclerViewProductAdapter(){
        productCartRecycleViewAdapter = new ProductCartRecycleViewAdapter(listProductCart, new ItemClickInterface() {
            @Override
            public void OnclickItem(int pos) {
                Dialog dialog = new Dialog(ProductCartActivity.this);
                dialog.setContentView(R.layout.custom_dialog_item);
                Button btnUpdate = dialog.findViewById(R.id.btn_update);
                Button btnRemove = dialog.findViewById(R.id.btn_remove);
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateSoluong(pos);
                        dialog.dismiss();
                    }
                });
                btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteSanpham(pos);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        recyclerViewProductCart.setAdapter(productCartRecycleViewAdapter);
        recyclerViewProductCart.setLayoutManager(new LinearLayoutManager(ProductCartActivity.this));
    }
    public void setEvent(){
        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductCartActivity.this, PaymentActivity.class);
                intent.putExtra("cart", cart);
                startActivity(intent);
            }
        });
    }
    public void updateSoluong(int pos){
        int idProduct = listProductCart.get(pos).getIdProductCart();
        Log.d("TAG", "updateSoluong: "+Integer.toString(pos));
        Dialog dialog = new Dialog(ProductCartActivity.this);
        dialog.setContentView(R.layout.custom_dialog_soluong);
        Button btnAddToCart = dialog.findViewById(R.id.btn_add_cart);
        EditText edSoluong = dialog.findViewById(R.id.ed_soluong);
        Button btnAdd= dialog.findViewById(R.id.btn_add);
        Button btnSub= dialog.findViewById(R.id.btn_sub);
        edSoluong.setText(Integer.toString(soLuong));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soLuong++;
                edSoluong.setText(Integer.toString(soLuong));
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soLuong--;
                edSoluong.setText(Integer.toString(soLuong));
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartAPI.cartAPI.updateCart(idCart,idProduct,soLuong).enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        try {
                            if(!response.isSuccessful()||response.body()==null)
                                throw new Exception();
                            productCartRecycleViewAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(ProductCartActivity.this, "Đã sua sản phẩm vào giỏ", Toast.LENGTH_LONG).show();
                            Log.d("test",Double.toString(response.body().getTotal()));

                        }catch (Exception e){
                            Log.d("test","error_null");
                            Toast.makeText(ProductCartActivity.this, "Loi cap nhat gio hang", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable throwable) {
                        Log.d("test","loi");
                        throwable.printStackTrace();
                        Toast.makeText(ProductCartActivity.this, "Loi server", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        dialog.show();
    }
    public void deleteSanpham(int pos){
        Log.d("TAG", "updateSoluong: "+Integer.toString(pos));
        int idProduct = listProductCart.get(pos).getIdProductCart();
        CartAPI.cartAPI.deleteCart(idProduct).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                try {
                    if(!response.isSuccessful()||response.body()==false)
                        throw new Exception();
                    productCartRecycleViewAdapter.notifyDataSetChanged();
                    Toast.makeText(ProductCartActivity.this, "Đã xoa sản phẩm ", Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    Log.d("test","error_null");
                    Toast.makeText(ProductCartActivity.this, "Loi cap nhat gio hang", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {
                Log.d("test","loi");
                throwable.printStackTrace();
                Toast.makeText(ProductCartActivity.this, "Loi server", Toast.LENGTH_LONG).show();
            }
        });
    }
}