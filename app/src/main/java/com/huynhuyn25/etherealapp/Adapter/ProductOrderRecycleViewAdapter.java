package com.huynhuyn25.etherealapp.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.huynhuyn25.etherealapp.Interface.ItemClickInterface;
import com.huynhuyn25.etherealapp.Model.Product;
import com.huynhuyn25.etherealapp.Model.ProductCart;
import com.huynhuyn25.etherealapp.Model.ProductOrder;
import com.huynhuyn25.etherealapp.R;

import java.io.InputStream;
import java.util.ArrayList;

public class ProductOrderRecycleViewAdapter extends RecyclerView.Adapter<ProductOrderRecycleViewAdapter.ProductOrderRecycleViewHolder> {
    ArrayList<ProductOrder> listProductOrder;
    ItemClickInterface itemClickInterface;
    private Bitmap bitmap;
    public ProductOrderRecycleViewAdapter(ArrayList<ProductOrder> listProductOrder, ItemClickInterface itemClickInterface) {
        this.listProductOrder = listProductOrder;
        this.itemClickInterface = itemClickInterface;
    }
    public void setList(ArrayList<ProductOrder> products){
        listProductOrder=products;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProductOrderRecycleViewAdapter.ProductOrderRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custom_layout_product_order,parent,false);
        return new ProductOrderRecycleViewAdapter.ProductOrderRecycleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderRecycleViewAdapter.ProductOrderRecycleViewHolder holder, int position) {
        ProductOrder productOrder = listProductOrder.get(position);

        holder.tvPrice.setText(Double.toString(productOrder.getProduct().getPrice()*productOrder.getSoLuong())+"₫");
        LoadImage loadImage = new LoadImage(holder.imgProductAva);
        loadImage.execute(productOrder.getProduct().getListProductPhoto().get(0).getUrl());
        holder.imgProductAva.setImageBitmap(bitmap);
        holder.tvName.setText(productOrder.getProduct().getName());

//        holder.imgProductAva.setImageResource(productOrder.getProduct().getListProductPhoto().get(0).getPhoto());
        holder.tvProductAmount.setText("x"+Integer.toString(productOrder.getSoLuong()));


        int pos = position;
        holder.itemProductOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemClickInterface.OnclickItem(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProductOrder.size();
    }

    public class ProductOrderRecycleViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvPrice;
        private TextView tvProductAmount;
        private CardView itemProductOrder;
        private ImageView imgProductAva;

        public ProductOrderRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvPrice = itemView.findViewById(R.id.tv_product_price);
            itemProductOrder = itemView.findViewById(R.id.layout_item_product_order);
            imgProductAva = itemView.findViewById(R.id.img_product_ava);
            tvProductAmount = itemView.findViewById(R.id.tv_product_amount);
//            tvAmount=itemView.findViewById(R.id.tv_amount);
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
