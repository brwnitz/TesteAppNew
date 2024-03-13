package com.example.testeapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testeapp.Models.PaymentModel;
import com.example.testeapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReprocessAdapter extends RecyclerView.Adapter<ReprocessAdapter.MyViewHolder> {
    Context context;
    ArrayList<PaymentModel> paymentModels;
    public ReprocessAdapter(Context context, ArrayList<PaymentModel> paymentModels){
        this.context = context;
        this.paymentModels = paymentModels;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvAuth, tvPrice, tvPaycode;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvId = itemView.findViewById(R.id.tvId);
            this.tvAuth = itemView.findViewById(R.id.tvAuthcode);
            this.tvPrice = itemView.findViewById(R.id.tvPrice);
            this.tvPaycode = itemView.findViewById(R.id.tvPaycode);
        }
    }

    @NonNull
    @Override
    public ReprocessAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reprocess_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReprocessAdapter.MyViewHolder holder, int position) {
        PaymentModel reprocessObj = paymentModels.get(position);
        holder.tvId.setText(String.valueOf(reprocessObj.getId()));
        holder.tvAuth.setText(String.valueOf(reprocessObj.getAuthCode()));
        holder.tvPaycode.setText(String.valueOf(reprocessObj.getPaymentCode()));
        long rawPrice = reprocessObj.getTotalPrice();
        Double value = Double.parseDouble(String.valueOf(rawPrice/100));
        DecimalFormat decimalFormat = new DecimalFormat("#,###,##0.00");
        String priceFormat = decimalFormat.format(value);
        holder.tvPrice.setText(priceFormat);
    }

    @Override
    public int getItemCount() {
        return paymentModels.size();
    }


}
