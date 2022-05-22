package com.huynhuyn25.etherealapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huynhuyn25.etherealapp.APIResource.CartAPI;
import com.huynhuyn25.etherealapp.APIResource.ProductAPI;
import com.huynhuyn25.etherealapp.Adapter.ProductRecycleViewAdapter;
import com.huynhuyn25.etherealapp.Interface.ItemClickInterface;
import com.huynhuyn25.etherealapp.Model.Cart;
import com.huynhuyn25.etherealapp.Model.Product;
import com.huynhuyn25.etherealapp.Model.User;

import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    private Product product;
    private Button btnAddCart,btnBack;
    ImageView imgAva;
    private Bitmap bitmap;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "mypref";
    private static final String KEY_CART = "cartID";
    private static final String KEY_USER = "USER";
    int soLuong = 1;
    User user;
    private int idCart;
    private RecyclerView recyclerViewProduct;
    ArrayList<Product> listProduct;
    TextView tvTen,tvGia,tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        getWidget();

        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");
        Log.d("TAG", "onCreateProduct: "+product.getName());
        sharedPreferences =getSharedPreferences(SHARE_PREF_NAME,MODE_PRIVATE);
        idCart =Integer.parseInt(sharedPreferences.getString(KEY_CART,null));

        getListProduct();
        setEvent();
        tvTen.setText(product.getName());
        tvGia.setText(Double.toString(product.getPrice()));
        tvDescription.setText(product.toString());
        LoadImage loadImage = new LoadImage(imgAva);
        loadImage.execute(product.getListProductPhoto().get(0).getUrl());
    }
    public void getWidget(){
        btnAddCart =findViewById(R.id.btn_add_cart);
        recyclerViewProduct = findViewById(R.id.list_product);
        imgAva =findViewById(R.id.img_product_ava);
        tvTen= findViewById(R.id.tv_ten);
        tvGia=findViewById(R.id.tv_price);
        tvDescription=findViewById(R.id.tv_product);
        btnBack =findViewById(R.id.btn_back);


    }
    public void setRecyclerViewProductAdapter(){
        ProductRecycleViewAdapter productRecycleViewAdapter = new ProductRecycleViewAdapter(listProduct, new ItemClickInterface() {
            @Override
            public void OnclickItem(int pos) {
                Product product = listProduct.get(pos);
                Intent intent = new Intent(ProductDetailActivity.this, ProductDetailActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });
        recyclerViewProduct.setAdapter(productRecycleViewAdapter);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(ProductDetailActivity.this,3){
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
                    Toast.makeText(ProductDetailActivity.this, "Khong co san pham ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable throwable) {
                Log.d("test","loi");
                throwable.printStackTrace();
                Toast.makeText(ProductDetailActivity.this, "Loi server", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void setEvent(){
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idProduct = product.getIdProduct();

                Dialog dialog = new Dialog(ProductDetailActivity.this);
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
                        CartAPI.cartAPI.addProduct(idCart,idProduct,soLuong).enqueue(new Callback<Cart>() {
                            @Override
                            public void onResponse(Call<Cart> call, Response<Cart> response) {
                                try {
                                    if(!response.isSuccessful()||response.body()==null)
                                        throw new Exception();
                                    dialog.dismiss();
                                    Toast.makeText(ProductDetailActivity.this, "Đã thêm sản phẩm vào giỏ", Toast.LENGTH_LONG).show();
                                    Log.d("test",Double.toString(response.body().getTotal()));

                                }catch (Exception e){
                                    Log.d("test","error_null");
                                    Toast.makeText(ProductDetailActivity.this, "Loi cap nhat gio hang", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Cart> call, Throwable throwable) {
                                Log.d("test","loi");
                                throwable.printStackTrace();
                                Toast.makeText(ProductDetailActivity.this, "Loi server", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                dialog.show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private class LoadImage extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;
        public LoadImage (ImageView img){
            imageView=img;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            bitmap=null;
            try {
                InputStream inputStream = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }catch (Exception e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imgAva.setImageBitmap(bitmap);
        }
    }
}