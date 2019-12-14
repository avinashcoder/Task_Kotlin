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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<Product> productsList;

    private ProductInterFace productInterFace;

    CartAdapter(ArrayList<Product> productsList, Context context, CartAdapter.ProductInterFace productInterFace) {
        this.mContext = context;
        this.productsList = productsList;
        this.productInterFace = productInterFace;

    }

    @NotNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.cart_item_layout, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, int position) {

        Product product = productsList.get(position);

        holder.productName.setText(product.getName());

        holder.productPrice.setText(Html.fromHtml(product.getPrice()));

        holder.quantity.setText(String.valueOf(product.getQuantity()));

        holder.description.setText(product.getDescription());

        Glide
                .with(mContext)
                .load(product.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


    interface ProductInterFace {

        void removeItem(int adapterPosition);

        void increaseQty(int position);

        void decreaseQty(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //TextView showLive;
        TextView productName, productPrice, quantity, description, removeItem, increaseQty, decreaseQty;
        ImageView productImage;

        ViewHolder(View view) {
            super(view);
            productPrice = view.findViewById(R.id.product_price);
            productName = view.findViewById(R.id.product_name);
            productImage = view.findViewById(R.id.productImage);
            quantity = view.findViewById(R.id.quantity);
            description = view.findViewById(R.id.product_description);
            removeItem = view.findViewById(R.id.removeItem);
            increaseQty = view.findViewById(R.id.increaseQty);
            decreaseQty = view.findViewById(R.id.decreaseQty);

            productImage.setOnClickListener(this);
            removeItem.setOnClickListener(this);
            increaseQty.setOnClickListener(this);
            decreaseQty.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();

            if (productsList.size() > position) {

                if (view == removeItem) {
                    productInterFace.removeItem(position);
                }
                if(view == increaseQty){
                    productInterFace.increaseQty(position);
                }

                if(view == decreaseQty){
                    productInterFace.decreaseQty(position);
                }
            }
        }
    }

}