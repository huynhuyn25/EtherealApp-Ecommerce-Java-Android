package com.huynhuyn25.etherealapp.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.huynhuyn25.etherealapp.Interface.ItemClickInterface;
import com.huynhuyn25.etherealapp.Model.ProductCart;
import com.huynhuyn25.etherealapp.Model.ProductOrder;
import com.huynhuyn25.etherealapp.R;

import java.io.InputStream;
import java.util.ArrayList;

public class ProductCartRecycleViewAdapter extends RecyclerView.Adapter<ProductCartRecycleViewAdapter.ProductCartRecycleViewHolder>{
    ArrayList<ProductCart> listProductCart;
    ItemClickInterface itemClickInterface;
    private Bitmap bitmap;
    public ProductCartRecycleViewAdapter(ArrayList<ProductCart> listProductCart, ItemClickInterface itemClickInterface) {
        this.listProductCart = listProductCart;
        this.itemClickInterface = itemClickInterface;
    }

    @NonNull
    @Override
    public ProductCartRecycleViewAdapter.ProductCartRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custom_layout_item_cart_linear,parent,false);
        return new ProductCartRecycleViewAdapter.ProductCartRecycleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCartRecycleViewAdapter.ProductCartRecycleViewHolder holder, int position) {
        ProductCart productCart = listProductCart.get(position);

        holder.tvPrice.setText(Double.toString(productCart.getProduct().getPrice())+"₫");
        holder.tvName.setText(productCart.getProduct().getName());
        holder.tvAmount.setText("x"+Integer.toString(productCart.getSoLuong()));
        LoadImage loadImage = new LoadImage(holder.imgProductAva);
        loadImage.execute(productCart.getProduct().getListProductPhoto().get(0).getUrl());
        holder.imgProductAva.setImageBitmap(bitmap);
        int pos = position;
        holder.itemProductCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemClickInterface.OnclickItem(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProductCart.size();
    }
    public class ProductCartRecycleViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvPrice;
        private TextView tvAmount;
        private CardView itemProductCart;
        private ImageView imgProductAva;
//        private CheckBox cbSanpham;

        public ProductCartRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvPrice = itemView.findViewById(R.id.tv_product_price);
            itemProductCart = itemView.findViewById(R.id.layout_item_cart);
            imgProductAva = itemView.findViewById(R.id.img_product_ava);
            tvAmount = itemView.findViewById(R.id.btn_product_amount);
//            cbSanpham = itemView.findViewById(R.id.cb_sanpham);
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
