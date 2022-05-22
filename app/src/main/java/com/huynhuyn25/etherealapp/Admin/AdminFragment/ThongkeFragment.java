package com.huynhuyn25.etherealapp.Admin.AdminFragment;

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
import android.widget.ImageView;

import com.google.gson.Gson;
import com.huynhuyn25.etherealapp.ChangeInforActivity;
import com.huynhuyn25.etherealapp.FragmentMainActivity.MyFragment;
import com.huynhuyn25.etherealapp.Model.User;
import com.huynhuyn25.etherealapp.OrderListActivity;
import com.huynhuyn25.etherealapp.ProductCartActivity;
import com.huynhuyn25.etherealapp.R;
import com.huynhuyn25.etherealapp.SignInActivity;

import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongkeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongkeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CardView cvLogout;
    private SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "mypref";
    public ThongkeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongkeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongkeFragment newInstance(String param1, String param2) {
        ThongkeFragment fragment = new ThongkeFragment();
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
        View view =  inflater.inflate(R.layout.fragment_thongke, container, false);
        getWidget(view);
        setEvent(view);
        return view;
    }
    public void getWidget(View v){
        cvLogout = v.findViewById(R.id.card_logout);

    }

    public void setEvent(View v){
        cvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences =v.getContext().getSharedPreferences(SHARE_PREF_NAME,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }
}