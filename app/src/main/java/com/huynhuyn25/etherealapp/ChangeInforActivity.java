package com.huynhuyn25.etherealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.gson.Gson;
import com.huynhuyn25.etherealapp.APIResource.CartAPI;
import com.huynhuyn25.etherealapp.APIResource.UserAPI;
import com.huynhuyn25.etherealapp.Model.AvaPhoto;
import com.huynhuyn25.etherealapp.Model.Cart;
import com.huynhuyn25.etherealapp.Model.ProductCart;
import com.huynhuyn25.etherealapp.Model.User;
import com.huynhuyn25.etherealapp.Presenter.UserPresenter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeInforActivity extends AppCompatActivity {
    private CircleImageView img;
    private EditText Username,Name,Email,Password,phoneNumber,dateofBirth;
    private Button btnLuu;
    private static final String KEY_USER = "USER";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "password";
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "mypref";
    private static final String TAG = "Upload ###";
    private Bitmap bitmap,bitmapold;
    private Uri imagePath;
    private String URL;
    private Map config = new HashMap();
    User user;
    private int idCart;
    private static final String KEY_CART = "cartID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_infor);
        getWidget();
        setData();
        setEvent();
//        initCongif();
    }
    public void getWidget(){
        img= findViewById(R.id.img_person_ava);
        Username = findViewById(R.id.ed_username);
        Name = findViewById(R.id.ed_name);
        Email= findViewById(R.id.ed_email);
        Password = findViewById(R.id.ed_password);
        phoneNumber = findViewById(R.id.ed_number_phone);
        dateofBirth = findViewById(R.id.ed_birthday);
        btnLuu =findViewById(R.id.btn_luu);

    }
    public void setData(){
        Gson gson = new Gson();
        sharedPreferences =getSharedPreferences(SHARE_PREF_NAME,MODE_PRIVATE);
        user = gson.fromJson(sharedPreferences.getString(KEY_USER,null),User.class);
        idCart =Integer.parseInt(sharedPreferences.getString(KEY_CART,null));
        Username.setText(user.getUsername());
        Name.setText(user.getName());
        Email.setText(user.getEmail());
        Password.setText(user.getPassword());
        phoneNumber.setText(user.getNumberPhone());
        dateofBirth.setText(user.getDateOfBirth());
        CartAPI.cartAPI.getCart(idCart).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                try {
                    if(!response.isSuccessful()||response.body()==null)
                        throw new Exception();
                    Cart cart=response.body();
                    user.setCart(cart);
                }catch (Exception e){
                    Log.d("test","error_null");
                    Toast.makeText(ChangeInforActivity.this, "Khong co san pham ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable throwable) {
                Log.d("test","loi");
                throwable.printStackTrace();
                Toast.makeText(ChangeInforActivity.this, "Loi server", Toast.LENGTH_LONG).show();
            }
        });
        LoadImage loadImage = new LoadImage(img);
        loadImage.execute(user.getAvaPhoto().getUrl()!=null?user.getAvaPhoto().getUrl():"https://cdn.pixabay.com/photo/2016/08/31/11/54/icon-1633249_1280.png");
        Log.d("test",user.getUsername());

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
                bitmapold=bitmap;
            }catch (Exception e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            img.setImageBitmap(bitmap);
        }
    }
//    private void initCongif() {
//
//        config.put("cloud_name", "huynhuyn25");
//        config.put("api_key","452877137516899");
//        config.put("api_secret","RGUOTiIYYl_ylKHm7lm495CNqgo");
////        config.put("secure", true);
//        MediaManager.init(this, config);
//    }

    public void openImagepicker(){

        TedBottomPicker.with(ChangeInforActivity.this)
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        try {
                            imagePath = uri;
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            img.setImageBitmap(bitmap);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }
    public void setEvent(){
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagepicker();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, ": "+" button clicked");

                try {if(Username.getText().toString().equals("")||Name.getText().toString().equals("")||Password.getText().toString().equals("")
                        ||dateofBirth.getText().toString().equals("")||phoneNumber.getText().toString().equals("")||Email.getText().toString().equals(""))
                    throw new Exception();
                    UserPresenter userPresenter = new UserPresenter();
                    if(!userPresenter.validate(Email.getText().toString())) throw new Exception();
                    user.setUsername(Username.getText().toString());
                    user.setName(Name.getText().toString());
                    user.setPassword(Password.getText().toString());
                    user.setDateOfBirth(dateofBirth.getText().toString());
                    user.setNumberPhone( phoneNumber.getText().toString());
                    user.setEmail(Email.getText().toString());
                    user.setCart(user.getCart());
                    java.util.Date date=new java.util.Date();

                    if(bitmap!=bitmapold){
                        Log.d("TAG", "onClick: if"+bitmap.toString());
                        MediaManager.get().upload(imagePath).callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {
                                Log.d(TAG, "onStart: "+"started");
                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {
                                Log.d(TAG, "onStart: "+"uploading");
                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                Log.d(TAG, "onStart: "+"success");
                                Log.d(TAG, "onStart: data"+resultData.get("secure_url").toString());
                                URL = resultData.get("secure_url").toString();
                                user.setAvaPhoto(new AvaPhoto("ava_photo"+ date.toString(),URL));
                                Log.d(TAG, "onClick: "+user.toString());
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                UserAPI.userAPI.updateUser(user.getIdUser(),user).enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        try {
                                            if(!response.isSuccessful()||response.body().getUsername()==null)
                                                throw new Exception();
                                            editor.putString(KEY_NAME,response.body().getUsername());
                                            editor.putString(KEY_PASS,response.body().getPassword());
                                            editor.putString(KEY_USER,response.body().toString());
                                            Log.d("test",response.body().toString());
                                            editor.apply();
                                            Intent intent = new Intent(ChangeInforActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            Log.d("test",response.body().getName());
                                        }catch (Exception e){
                                            Log.d("test","error_null");
                                            Toast.makeText(ChangeInforActivity.this, "Loi dang ky", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable throwable) {
                                        Log.d("test","loi");
                                        throwable.printStackTrace();
                                        Toast.makeText(ChangeInforActivity.this, "Loi server", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                            @Override
                            public void onError(String requestId, ErrorInfo error) {
                                Log.d(TAG, "onStart: "+error);
                            }

                            @Override
                            public void onReschedule(String requestId, ErrorInfo error) {
                                Log.d(TAG, "onStart: "+error);
                            }
                        }).dispatch();
                    }else{
                        Log.d("TAG", "onClick: else");
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        UserAPI.userAPI.updateUser(user.getIdUser(),user).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                try {
                                    if(!response.isSuccessful()||response.body().getUsername()==null)
                                        throw new Exception();
                                    editor.putString(KEY_NAME,response.body().getUsername());
                                    editor.putString(KEY_PASS,response.body().getPassword());
                                    editor.putString(KEY_USER,response.body().toString());
                                    Log.d("test",response.body().toString());
                                    editor.apply();
                                    Intent intent = new Intent(ChangeInforActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    Log.d("test",response.body().getName());
                                }catch (Exception e){
                                    Log.d("test","error_null");
                                    Toast.makeText(ChangeInforActivity.this, "Loi dang ky", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable throwable) {
                                Log.d("test","loi");
                                throwable.printStackTrace();
                                Toast.makeText(ChangeInforActivity.this, "Loi server", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }catch (Exception e){
                    Toast.makeText(ChangeInforActivity.this, "Dien thong tin khong dung hoac chua dien du thong tin", Toast.LENGTH_LONG).show();
                }

            }
        });
        dateofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
//            Toast.makeText(this, day+""+month+""+year+"", Toast.LENGTH_LONG).show();
                DatePickerDialog datePicker = new DatePickerDialog(ChangeInforActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date = "";
                        if(month > 8){
                            date = day +"/"+ (month+1)+"/"+year;
                        }
                        else{
                            date = day +"/"+ "0" + (month+1)+ "/" +year;
                        }
                        dateofBirth.setText(date);
                    }
                }, year, month,day);
                datePicker.show();
            }
        });
    }
}