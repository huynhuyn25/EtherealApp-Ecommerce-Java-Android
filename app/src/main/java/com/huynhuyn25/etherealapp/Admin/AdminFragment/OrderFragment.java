package com.huynhuyn25.etherealapp.Admin.AdminFragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huynhuyn25.etherealapp.APIResource.OrderAPI;
import com.huynhuyn25.etherealapp.Adapter.OrderRecycleViewAdapter;
import com.huynhuyn25.etherealapp.Interface.ItemClickInterface;
import com.huynhuyn25.etherealapp.Model.Order;
import com.huynhuyn25.etherealapp.Model.User;
import com.huynhuyn25.etherealapp.OrderDetailActivity;
import com.huynhuyn25.etherealapp.OrderListActivity;
import com.huynhuyn25.etherealapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerViewOrder;
    private OrderRecycleViewAdapter orderRecycleViewAdapter;
    ArrayList<Order> orders= new ArrayList<>();
    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        getWidget(view);
        return view;
    }
    public void getWidget(View view){
        recyclerViewOrder = view.findViewById(R.id.list);
        getListProduct();

    }
    public void getListProduct(){
        OrderAPI.orderAPI.getAllOrder().enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                try {
                    if (!response.isSuccessful()||response.body().size()==0) throw new Exception();
                    orders = response.body();
                    if(orderRecycleViewAdapter==null){
                        setRecyclerViewProductAdapter();
                    }else {
                        Log.d("TAG", "onResponse: setlist");
                        orderRecycleViewAdapter.setList(orders);
                    }
//                    setRecyclerViewProductAdapter();
                }catch (Exception e){
                    Log.d("test","error_null");
                    Toast.makeText(getContext(), "Loi", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable throwable) {
                Log.d("test","loi");
                throwable.printStackTrace();
                Toast.makeText(getContext(), "Loi server", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void setRecyclerViewProductAdapter(){
        orderRecycleViewAdapter = new OrderRecycleViewAdapter(orders, new ItemClickInterface() {
            @Override
            public void OnclickItem(int pos) {
                Order order = orders.get(pos);
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra("order", order);
                startActivity(intent);
            }
        });
        recyclerViewOrder.setAdapter(orderRecycleViewAdapter);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    @Override
    public void onResume() {
        super.onResume();
        getListProduct();
    }
}