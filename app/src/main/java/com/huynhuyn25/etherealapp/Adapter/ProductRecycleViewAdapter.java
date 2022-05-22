package com.huynhuyn25.etherealapp.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.huynhuyn25.etherealapp.FragmentMainActivity.MyFragment;
import com.huynhuyn25.etherealapp.Interface.ItemClickInterface;
import com.huynhuyn25.etherealapp.Model.Product;
import com.huynhuyn25.etherealapp.R;

import java.io.InputStream;
import java.util.ArrayList;

public class ProductRecycleViewAdapter extends RecyclerView.Adapter<ProductRecycleViewAdapter.ProductRecycleViewHolder> implements Filterable {

    ArrayList<Product> listProduct;
    ArrayList<Product> listProductOld;
    ItemClickInterface itemClickInterface;
    private Bitmap bitmap;
    public ProductRecycleViewAdapter(ArrayList<Product> listProduct, ItemClickInterface itemClickInterface) {
        this.listProduct = listProduct;
        this.listProductOld = listProduct;
        this.itemClickInterface = itemClickInterface;
    }
    public void setList(ArrayList<Product> products){
        listProduct=products;
        this.listProductOld=products;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProductRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custom_layout_item_product_grid,parent,false);
        return new ProductRecycleViewAdapter.ProductRecycleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecycleViewHolder holder, int position) {
        Product product = listProduct.get(position);

        holder.tvPrice.setText(Double.toString(product.getPrice()));
        holder.tvName.setText(product.getName());
        LoadImage loadImage = new LoadImage(holder.imgProductAva);
        loadImage.execute(product.getListProductPhoto().get(0).getUrl());

        Log.d("TAG", "onBindViewHolder: "+product.getListProductPhoto().get(0).getUrl());
        holder.imgProductAva.setImageBitmap(bitmap);
        int pos = position;
        holder.itemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemClickInterface.OnclickItem(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    listProduct = listProductOld;
                }else {
                    ArrayList<Product> products = new ArrayList<>();
                    for(Product product:listProductOld){
                        if(product.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            products.add(product);
                        }
                    }
                    listProduct = products;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listProduct;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listProduct = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ProductRecycleViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvPrice;
        private CardView itemProduct;
        private ImageView imgProductAva;

        public ProductRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvPrice = itemView.findViewById(R.id.tv_product_price);
            itemProduct = itemView.findViewById(R.id.layout_item_product);
            imgProductAva = itemView.findViewById(R.id.img_product_ava);
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
