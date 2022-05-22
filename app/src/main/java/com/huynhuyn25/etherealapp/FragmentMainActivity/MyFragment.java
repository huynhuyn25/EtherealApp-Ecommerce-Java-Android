package com.huynhuyn25.etherealapp.FragmentMainActivity;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huynhuyn25.etherealapp.ChangeInforActivity;
import com.huynhuyn25.etherealapp.MainActivity;
import com.huynhuyn25.etherealapp.Model.User;
import com.huynhuyn25.etherealapp.OrderListActivity;
import com.huynhuyn25.etherealapp.ProductCartActivity;
import com.huynhuyn25.etherealapp.R;
import com.huynhuyn25.etherealapp.SignInActivity;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "password";
    private CardView cvLogout;
    private CardView cvChangeInfor;
    private CardView cvCart;
    private CardView cvOrder;
    private User user;
    private Bitmap bitmap;
    private static final String KEY_USER = "USER";
    private TextView tvName,tvUsername;
    private CircleImageView imgAva;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        getWidget(view);
        setEvent(view);
        return view;
    }
    public void getWidget(View v){
        cvLogout = v.findViewById(R.id.card_logout);
        cvChangeInfor = v.findViewById(R.id.card_changeInfor);
        cvCart = v.findViewById(R.id.cv_Cart);
        cvOrder = v.findViewById(R.id.cv_Order);
        tvName = v.findViewById(R.id.tv_name);
        tvUsername = v.findViewById(R.id.tv_username);
        imgAva = v.findViewById(R.id.img_person_ava);
        sharedPreferences =v.getContext().getSharedPreferences(SHARE_PREF_NAME,MODE_PRIVATE);
        Gson gson = new Gson();
        user = gson.fromJson(sharedPreferences.getString(KEY_USER,null),User.class);
        tvName.setText(user.getName());
        LoadImage loadImage = new LoadImage(imgAva);
        loadImage.execute(user.getAvaPhoto().getUrl()!=null?user.getAvaPhoto().getUrl():"https://cdn.pixabay.com/photo/2016/08/31/11/54/icon-1633249_1280.png");

    }
    private class LoadImage extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;
        public LoadImage (ImageView imgAva){
            imageView=imgAva;
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
    public void setEvent(View v){
        cvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        cvChangeInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangeInforActivity.class);
                startActivity(intent);
            }
        });
        cvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProductCartActivity.class);
                startActivity(intent);
            }
        });
        cvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                startActivity(intent);
            }
        });
    }
}