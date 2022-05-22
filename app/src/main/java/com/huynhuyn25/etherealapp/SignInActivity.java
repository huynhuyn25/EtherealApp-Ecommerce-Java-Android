package com.huynhuyn25.etherealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.google.gson.Gson;
import com.huynhuyn25.etherealapp.APIResource.UserAPI;
import com.huynhuyn25.etherealapp.Admin.AdminActivity;
import com.huynhuyn25.etherealapp.Model.User;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private EditText edName,edPassword;
    private Button btnSignin;
    private TextView tvSignup;
    private Map config = new HashMap();
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "password";
    private static final String KEY_CART = "cartID";
    private static final String KEY_USER = "USER";
    private static int KEY = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getWidget();
        setEvent();

    }
    private void initCongif() {

        config.put("cloud_name", "huynhuyn25");
        config.put("api_key","452877137516899");
        config.put("api_secret","RGUOTiIYYl_ylKHm7lm495CNqgo");
//        config.put("secure", true);
        MediaManager.init(this, config);
        KEY=1;
    }
    public void getWidget(){
        edName =findViewById(R.id.ed_username);
        edPassword=findViewById(R.id.ed_password);
        btnSignin =findViewById(R.id.btn_signin);
        tvSignup=findViewById(R.id.tv_Signup);
//        tvSignup.setPaintFlags(tvSignup.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        sharedPreferences =getSharedPreferences(SHARE_PREF_NAME,MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME,null);
        String pass = sharedPreferences.getString(KEY_PASS,null);
        Gson gson = new Gson();
        sharedPreferences =getSharedPreferences(SHARE_PREF_NAME,MODE_PRIVATE);
        if(KEY==0){
            initCongif();
        }

        User user = gson.fromJson(sharedPreferences.getString(KEY_USER,null),User.class);
        if(name!=null&&pass!=null){
            Log.d("TAG", "getWidget: "+user.getUsername()+user.getVaitro());

            switch (user.getVaitro()){
                case 1:
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case 0:
                    Intent intent1 = new Intent(SignInActivity.this, AdminActivity.class);
                    startActivity(intent1);
                    break;
                default:
                    Intent intent2 = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent2);
            }

        }


    }
    public void setEvent(){
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {

                    UserAPI.userAPI.getUserByNamePassword(edName.getText().toString(),edPassword.getText().toString()).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            try {
                                if(!response.isSuccessful()||response.body().getUsername()==null)
                                    throw new Exception();
                                editor.putString(KEY_NAME,response.body().getUsername());
                                editor.putString(KEY_PASS,response.body().getPassword());
                                editor.putString(KEY_USER,response.body().toString());
                                editor.putString(KEY_CART,Integer.toString(response.body().getCart().getIdCart()));
                                Log.d("test",response.body().toString());
                                editor.apply();
                                if(response.body().getVaitro()==0){
                                    Intent intent = new Intent(SignInActivity.this, AdminActivity.class);
                                    startActivity(intent);
                                }else {
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    Log.d("test",response.body().getName());

                                }

                            }catch (Exception e){
                                Log.d("test","error_null");
                                Toast.makeText(SignInActivity.this, "Thong tin dang nhap khong dung", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable throwable) {
                            Log.d("test","loi");
                            throwable.printStackTrace();
                            Toast.makeText(SignInActivity.this, "Loi server", Toast.LENGTH_LONG).show();
                        }
                    });


                }catch (Exception e){
                    Toast.makeText(SignInActivity.this, "Dien thong tin khong dung hoac chua dien du thong tin", Toast.LENGTH_LONG).show();
                }

            }
        });
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}