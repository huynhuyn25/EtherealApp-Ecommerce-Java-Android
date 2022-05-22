package com.huynhuyn25.etherealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huynhuyn25.etherealapp.APIResource.UserAPI;
import com.huynhuyn25.etherealapp.Model.AvaPhoto;
import com.huynhuyn25.etherealapp.Model.Cart;
import com.huynhuyn25.etherealapp.Model.ProductCart;
import com.huynhuyn25.etherealapp.Model.User;
import com.huynhuyn25.etherealapp.Presenter.UserPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private EditText Username,Name,Email,Password,phoneNumber,dateofBirth;
    private Button btnLuu;
    private TextView tvSignin;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "password";
    private static final String KEY_USER = "USER";
    private static final String KEY_CART = "cartID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWidget();
        setEvent();
    }
    public void getWidget(){
        Username = findViewById(R.id.ed_username);
        Name = findViewById(R.id.ed_name);
        Email= findViewById(R.id.ed_email);
        Password = findViewById(R.id.ed_password);
        phoneNumber = findViewById(R.id.ed_number_phone);
        dateofBirth = findViewById(R.id.ed_birthday);
        btnLuu =findViewById(R.id.btn_luu);
        tvSignin = findViewById(R.id.tv_signin);
        sharedPreferences =getSharedPreferences(SHARE_PREF_NAME,MODE_PRIVATE);
//        String name = sharedPreferences.getString(KEY_NAME,null);
//        String pass = sharedPreferences.getString(KEY_PASS,null);
//        if(name!=null&&pass!=null){
//            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//            startActivity(intent);
//        }


    }
    public void setEvent(){
        dateofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
//            Toast.makeText(this, day+""+month+""+year+"", Toast.LENGTH_LONG).show();
                DatePickerDialog datePicker = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    User user= new User();
                    if(Username.getText().toString().equals("")||Name.getText().toString().equals("")||Password.getText().toString().equals("")
                            ||dateofBirth.getText().toString().equals("")||phoneNumber.getText().toString().equals("")||Email.getText().toString().equals(""))
                        throw new Exception();
                    UserPresenter userPresenter = new UserPresenter();
                    if(!userPresenter.validate(Email.getText().toString())) throw new Exception();
                    if(Password.getText().toString().length()<6) throw new Exception();
                    user.setUsername(Username.getText().toString());
                    user.setName(Name.getText().toString());
                    user.setPassword(Password.getText().toString());
                    user.setDateOfBirth(dateofBirth.getText().toString());
                    user.setNumberPhone( phoneNumber.getText().toString());
                    user.setEmail(Email.getText().toString());
                    java.util.Date date=new java.util.Date();
                    user.setCart(new Cart(new ArrayList<ProductCart>(),0));
                    user.setAvaPhoto(new AvaPhoto("ava_photo"+ date.toString(),"https://cdn.pixabay.com/photo/2016/08/31/11/54/icon-1633249_1280.png"));
                    user.setVaitro(1);
                    UserAPI.userAPI.addUser(user).enqueue(new Callback<User>() {
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
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                Log.d("test",response.body().getName());
                            }catch (Exception e){
                                Log.d("test","error_null");
                                Toast.makeText(SignUpActivity.this, "Loi dang ky", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable throwable) {
                            Log.d("test","loi");
                            throwable.printStackTrace();
                            Toast.makeText(SignUpActivity.this, "Loi server", Toast.LENGTH_LONG).show();
                        }
                    });


                }catch (Exception e){
                    Toast.makeText(SignUpActivity.this, "Dien thong tin khong dung hoac chua dien du thong tin", Toast.LENGTH_LONG).show();
                }

            }
        });
        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}