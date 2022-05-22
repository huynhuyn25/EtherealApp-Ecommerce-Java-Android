package com.huynhuyn25.etherealapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.huynhuyn25.etherealapp.APIResource.CartAPI;
import com.huynhuyn25.etherealapp.APIResource.ProductAPI;
import com.huynhuyn25.etherealapp.Model.Address;
import com.huynhuyn25.etherealapp.Model.Cart;
import com.huynhuyn25.etherealapp.Model.Category;
import com.huynhuyn25.etherealapp.Model.Manufacturer;
import com.huynhuyn25.etherealapp.Model.Product;
import com.huynhuyn25.etherealapp.Model.ProductPhoto;
import com.huynhuyn25.etherealapp.ProductCartActivity;
import com.huynhuyn25.etherealapp.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeleteProductActivity extends AppCompatActivity {
    private Spinner spinnerNCC,spinnerLSP;
    private Product product = new Product();
    private Button btnUpdate,btnDelete,btnHuy;
    private EditText edTen,edGia,edSoluong;
    private Bitmap bitmap,bitmapold;
    private Uri imagePath;
    private String URL;
    private Map config = new HashMap();
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_product);
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");
        getWidget();
//        initCongif();
        setEvent();
    }
    public void getWidget(){
        spinnerNCC = findViewById(R.id.spinner_nhacungcap);
        spinnerNCC.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.Nhacungcap)));
        spinnerLSP = findViewById(R.id.spinner_loaisanpham);
        spinnerLSP.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.Loaisanpham)));
        btnUpdate = findViewById(R.id.btn_sua);
        btnDelete = findViewById(R.id.btn_xoa);
        edTen = findViewById(R.id.edt_ten);
        btnHuy =findViewById(R.id.btn_huy);
        edGia =findViewById(R.id.edt_gia);
        edSoluong =findViewById(R.id.edt_soluong);
        imageView =findViewById(R.id.img_product_ava);
        LoadImage loadImage = new LoadImage(imageView);
        loadImage.execute(product.getListProductPhoto().get(0).getUrl());
        imageView.setImageBitmap(bitmap);
        edTen.setText(product.getName());
        edGia.setText(Double.toString(product.getPrice()));
        edSoluong.setText(Integer.toString(product.getSoluongCon()));
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
            imageView.setImageBitmap(bitmap);
        }
    }
//    private void initCongif() {
//
//        config.put("cloud_name", "huynhuyn25");
//        config.put("api_key","452877137516899");
//        config.put("api_secret","RGUOTiIYYl_ylKHm7lm495CNqgo");
////        config.put("secure", true);
//
//        MediaManager.init(this, config);
//
//
//    }

    public void openImagepicker(){

        TedBottomPicker.with(UpdateDeleteProductActivity.this)
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        try {
                            imagePath = uri;
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            imageView.setImageBitmap(bitmap);
                            bitmapold=bitmap;
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }
    public void setEvent(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagepicker();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(edGia.getText().toString().equals("")||edTen.getText().toString().equals("")) throw new Exception();
                    product.setName(edTen.getText().toString());
                    product.setPrice(Double.parseDouble(edGia.getText().toString()));
                    product.setSoluongCon(Integer.parseInt(edSoluong.getText().toString()));
                    getDataLoaiSP();
                    getDataNCC();
                    Log.d("TAG", "onClick: if"+bitmap.toString());
                    if(bitmap==bitmapold){
                        MediaManager.get().upload(imagePath).callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {
                                Log.d("TAG", "onStart: "+"started");
                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {
                                Log.d("TAG", "onStart: "+"uploading");
                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                java.util.Date date=new java.util.Date();
                                URL = resultData.get("secure_url").toString();
                                ArrayList<ProductPhoto> productPhotos = new ArrayList<>();
                                productPhotos.add(new ProductPhoto(date.toString(),URL));
                                product.setListProductPhoto(productPhotos);
                                ProductAPI.productAPI.updateProduct(product.getIdProduct(),product).enqueue(new Callback<Product>() {
                                    @Override
                                    public void onResponse(Call<Product> call, Response<Product> response) {
                                        try {
                                            if(!response.isSuccessful()||response.body().getName()==null)
                                                throw new Exception();
                                            finish();
                                            Log.d("test",response.body().getName());
                                        }catch (Exception e){
                                            Log.d("test","error_null");
                                            Toast.makeText(UpdateDeleteProductActivity.this, "Loi dang ky", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Product> call, Throwable throwable) {
                                        Log.d("test","loi");
                                        throwable.printStackTrace();
                                        Toast.makeText(UpdateDeleteProductActivity.this, "Loi server", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                            @Override
                            public void onError(String requestId, ErrorInfo error) {
                                Log.d("TAG", "onStart: "+error);
                            }

                            @Override
                            public void onReschedule(String requestId, ErrorInfo error) {
                                Log.d("TAG", "onStart: "+error);
                            }
                        }).dispatch();

                    }else{
                        ProductAPI.productAPI.updateProduct(product.getIdProduct(),product).enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                try {
                                    if(!response.isSuccessful()||response.body().getName()==null)
                                        throw new Exception();
                                    finish();
                                    Log.d("test",response.body().getName());
                                }catch (Exception e){
                                    Log.d("test","error_null");
                                    Toast.makeText(UpdateDeleteProductActivity.this, "Loi dang ky", Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<Product> call, Throwable throwable) {
                                Log.d("test","loi");
                                throwable.printStackTrace();
                                Toast.makeText(UpdateDeleteProductActivity.this, "Loi server", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(UpdateDeleteProductActivity.this, "Dien thong tin khong dung hoac chua dien du thong tin", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductAPI.productAPI.deleteProduct(product.getIdProduct()).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        try {
                            if(!response.isSuccessful()||response.body()==false)
                                throw new Exception();
                            Toast.makeText(UpdateDeleteProductActivity.this, "Đã xoa sản phẩm ", Toast.LENGTH_LONG).show();
                            finish();

                        }catch (Exception e){
                            Log.d("test","error_null");
                            Toast.makeText(UpdateDeleteProductActivity.this, "Loi cap nhat gio hang", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable throwable) {
                        Log.d("test","loi");
                        throwable.printStackTrace();
                        Toast.makeText(UpdateDeleteProductActivity.this, "Loi server", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    public void getDataLoaiSP(){
        Category category = new Category(spinnerLSP.getSelectedItem().toString(),null);
        product.setCategory(category);
    }
    public void getDataNCC(){
        Manufacturer manufacturer1 = new Manufacturer("Romand",new Address("Han Quoc",null,null,null,null,null),"Romand","romand@gmail.com","0123456789");
        Manufacturer manufacturer2 = new Manufacturer("Perfect Diary",new Address("Trung Quoc",null,null,null,null,null),"Romand","romand@gmail.com","0123456789");
        Manufacturer manufacturer3 = new Manufacturer("3CE",new Address("Han Quoc",null,null,null,null,null),"Romand","romand@gmail.com","0123456789");
        Manufacturer manufacturer4 = new Manufacturer("Blackrouge",new Address("Han Quoc",null,null,null,null,null),"Romand","romand@gmail.com","0123456789");
        Manufacturer manufacturer5 = new Manufacturer("Loreal",new Address("Phap",null,null,null,null,null),"Romand","romand@gmail.com","0123456789");
        Manufacturer manufacturer6 = new Manufacturer("Cocoon",new Address("Viet Nam",null,null,null,null,null),"Romand","romand@gmail.com","0123456789");
        Manufacturer manufacturer7 = new Manufacturer("Mamonde",new Address("Han Quoc",null,null,null,null,null),"Romand","romand@gmail.com","0123456789");
        Manufacturer manufacturer8 = new Manufacturer("Innisfree",new Address("Han Quoc",null,null,null,null,null),"Romand","romand@gmail.com","0123456789");
        switch (spinnerNCC.getSelectedItem().toString()){
            case "Romand":
                product.setManufacturer(manufacturer1);
                break;
            case "Perfect Diary":
                product.setManufacturer(manufacturer2);
                break;
            case "3CE":
                product.setManufacturer(manufacturer3);
                break;
            case "Blackrouge":
                product.setManufacturer(manufacturer4);
                break;
            case "Loreal":
                product.setManufacturer(manufacturer5);
                break;
            case "Cocoon":
                product.setManufacturer(manufacturer6);
                break;
            case "Mamonde":
                product.setManufacturer(manufacturer7);
                break;
            case "Innisfree":
                product.setManufacturer(manufacturer8);
                break;
        }

    }

}