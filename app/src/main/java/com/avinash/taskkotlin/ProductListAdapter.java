package com.avinash.taskkotlin;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductsViewHolder> {


    Context mContext;
    private ArrayList<Product> productsList = new ArrayList<>();

    private ProductInterFace productInterFace;

    ProductListAdapter(ArrayList<Product> productsList, Context context, ProductInterFace productInterFace) {
        this.mContext = context;
        this.productsList = productsList;
        this.productInterFace = productInterFace;

    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.product_layout, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {

        Product product = productsList.get(position);

        holder.productName.setText(product.getName());

        holder.productPrice.setText(Html.fromHtml(product.getPrice()));

        Glide
                .with(mContext)
                .load(product.getImageUrl())
                .centerCrop()
                .placeholder(null)
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


    interface ProductInterFace {
        void viewProduct(int position);
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //TextView showLive;
        TextView productName, productPrice;
        ImageView productImage;

        public ProductsViewHolder(View view) {
            super(view);
            productPrice = view.findViewById(R.id.product_price);
            productName = view.findViewById(R.id.product_name);

            productImage = view.findViewById(R.id.productImage);

            productImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();

            if (productsList.size() > position) {
                Product product = productsList.get(position);

                if (view == productImage) {

                    productInterFace.viewProduct(getAdapterPosition());
                }
            }
        }
    }

}
