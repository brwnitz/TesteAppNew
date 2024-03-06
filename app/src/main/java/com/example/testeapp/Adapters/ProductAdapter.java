package com.example.testeapp.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testeapp.Activities.MainActivity;
import com.example.testeapp.Models.Product;
import com.example.testeapp.Presenters.MainActivityInterface;
import com.example.testeapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    public Context context;
    public ArrayList<Product> product_list,cart_list;
    public MainActivityInterface mainInterface;

    Activity activity;

    public ProductAdapter(Context context,ArrayList<Product> product_list,ArrayList<Product> cart_list, Activity activity, MainActivityInterface myInterface) {
        this.context = context;
        this.product_list = product_list;
        this.activity = activity;
        this.cart_list = cart_list;
        this.mainInterface = myInterface;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idView, nameView, priceView, counterView;
        Button addButton, removeButton;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idView = itemView.findViewById(R.id.idView);
            nameView = itemView.findViewById(R.id.nameView);
            priceView = itemView.findViewById(R.id.priceView);
            addButton = itemView.findViewById(R.id.btnAddQnt);
            removeButton = itemView.findViewById(R.id.btnRemoveQnt);
            mainLayout = itemView.findViewById(R.id.mainProductLayout);
            counterView = itemView.findViewById(R.id.counterView);
        }
    }
    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {
        Product product = product_list.get(position);
        holder.idView.setText(String.valueOf(product.getId()));
        holder.nameView.setText(String.valueOf(product.getNome()));
        String priceRaw = String.valueOf(product.getPrice());
        Double value = Double.parseDouble(priceRaw)/100;
        DecimalFormat decimalFormat = new DecimalFormat("#,###,##0.00");
        String priceFormat = decimalFormat.format(value);
        holder.priceView.setText(String.valueOf("R$"+priceFormat));
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setQuantity(product.getQuantity() + 1);
                mainInterface.updateItemCart(product);
                holder.counterView.setText(String.valueOf(product.getQuantity()));
            }
        });
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getQuantity() > 0){
                    product.setQuantity(product.getQuantity() - 1);
                    holder.counterView.setText(String.valueOf(product.getQuantity()));
                }
                mainInterface.updateItemCart(product);
            }
        });


    }
    @Override
    public int getItemCount() {
        return product_list.size();
    }

}
