package com.huynhuyn25.etherealapp.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.huynhuyn25.etherealapp.Interface.ItemClickInterface;
import com.huynhuyn25.etherealapp.Model.Order;
import com.huynhuyn25.etherealapp.Model.Product;
import com.huynhuyn25.etherealapp.Model.ProductOrder;
import com.huynhuyn25.etherealapp.R;

import java.io.InputStream;
import java.util.ArrayList;

public class OrderRecycleViewAdapter extends RecyclerView.Adapter<OrderRecycleViewAdapter.OrderRecycleViewHolder>{

    ArrayList<Order> listOrder;
    ItemClickInterface itemClickInterface;
    private Bitmap bitmap;
    public OrderRecycleViewAdapter(ArrayList<Order> listOrder, ItemClickInterface itemClickInterface) {
        this.listOrder = listOrder;
        this.itemClickInterface = itemClickInterface;
    }
    public void setList(ArrayList<Order> products){
        listOrder=products;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrderRecycleViewAdapter.OrderRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custom_layout_item_order_linear,parent,false);
        return new OrderRecycleViewAdapter.OrderRecycleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecycleViewAdapter.OrderRecycleViewHolder holder, int position) {
       Order order = listOrder.get(position);

        holder.tvPrice.setText(Double.toString(order.getTotal())+"₫");
        holder.tvName.setText(order.getListProductOrder().get(0).getProduct().getName());
        LoadImage loadImage = new LoadImage(holder.imgProductAva);
        loadImage.execute(order.getListProductOrder().get(0).getProduct().getListProductPhoto().get(0).getUrl());
        holder.imgProductAva.setImageBitmap(bitmap);
        holder.tvProductAmount.setText("x"+Integer.toString(order.getListProductOrder().get(0).getSoLuong()));
        Log.d("TAG", "onBindViewHolder: "+Integer.toString(order.getListProductOrder().get(0).getSoLuong()));
//        holder.tvAmount.setText(Integer.toString(order.getListProductOrder().size()-1));
        if(order.getListProductOrder().size()>1){
            holder.tvAmount.setText("Xem thêm "+Integer.toString(order.getListProductOrder().size()-1)+" sản phẩm");
        }else{
            holder.tvAmount.setText("");
        }
        int pos = position;
        holder.itemOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemClickInterface.OnclickItem(pos);
            }
        });
    }
    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public class OrderRecycleViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvPrice;
        private TextView tvProductAmount;
        private TextView tvAmount;
        private CardView itemOrder;
        private ImageView imgProductAva;

        public OrderRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvPrice = itemView.findViewById(R.id.tv_product_price);
            itemOrder = itemView.findViewById(R.id.layout_item_order);
            imgProductAva = itemView.findViewById(R.id.img_product_ava);
            tvProductAmount = itemView.findViewById(R.id.tv_product_amount);
            tvAmount = itemView.findViewById(R.id.tv_amount);
        }
    }
    private class LoadImage extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;
        public LoadImage (ImageView imgProductAva){
            imageView=imgProductAva;
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
}
