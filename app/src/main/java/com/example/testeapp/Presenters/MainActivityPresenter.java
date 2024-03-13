package com.example.testeapp.Presenters;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.testeapp.Activities.MainActivity;
import com.example.testeapp.Interfaces.InterfaceRetrofit;
import com.example.testeapp.Models.PaymentModel;
import com.example.testeapp.Models.Product;
import com.example.testeapp.MyClient;
import com.example.testeapp.Utils.IPaymentAsyncTask;
import com.example.testeapp.Utils.LioUtil;
import com.example.testeapp.Utils.PaymentAsyncTask;
import com.example.testeapp.database.DatabaseHelper;
import com.example.testeapp.database.ReprocessDAO;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cielo.sdk.order.payment.PaymentCode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements IPaymentAsyncTask {

    PaymentAsyncTask paymentAsyncTask;
    public String totalValue(ArrayList<Product> product_list){
        long totalPrice = 0;
        for (int i = 0; i < product_list.size(); i++) {
            Product product = product_list.get(i);
            totalPrice += product.getPrice() * product.getQuantity();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###,##0.00");
        String priceFormat = decimalFormat.format(totalPrice/100.00);
        return priceFormat;
    }

    public void makePayment(ArrayList<Product> cart_list, String payments, String installments, Context context, LioUtil apiLio){
        long totalPrice = 0;
        for (int i = 0; i < cart_list.size(); i++) {
            Product product = cart_list.get(i);
            totalPrice += product.getPrice() * product.getQuantity();
        }
        try {

            final PaymentCode paymentCode;
            final int installmentsInt;
            if (installments.equals("A vista")){
                installmentsInt = 1;
                switch (payments){
                    case "Credito":
                        paymentCode = PaymentCode.CREDITO_AVISTA;
                        break;
                    case "Debito":
                        paymentCode = PaymentCode.DEBITO_AVISTA;
                        break;
                    default:
                        paymentCode = PaymentCode.DEBITO_AVISTA;
                }
            }
            else {
                installmentsInt = 1;
                paymentCode = PaymentCode.CREDITO_AVISTA;
            }
            new PaymentAsyncTask(
                    apiLio,
                    installmentsInt ,
                    paymentCode,
                    context,
                    totalPrice,
                    this
            ).execute();
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("ERRO PAYMENT",String.valueOf(e));
        }

    }

    @Override
    public void sendRequest(PaymentModel paymentModel, Context context){
        InterfaceRetrofit apiService = MyClient.getClient().create(InterfaceRetrofit.class);
        Call<Void> call = apiService.postData(paymentModel);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("REQUEST", "A REQUEST FOI UM SUCESSO");
                }
                else{
                    ReprocessDAO reprocessDAO = new ReprocessDAO(context);
                    reprocessDAO.addPayment(paymentModel);
                    Log.d("REQUEST","ADICIONADO A TABLE DE REPROCESSAMENTO");
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ReprocessDAO reprocessDAO = new ReprocessDAO(context);
                reprocessDAO.addPayment(paymentModel);
                Log.d("REQUEST","ADICIONADO A TABLE DE REPROCESSAMENTO VIA FAILUIRE");
            }
        });
    }
}
