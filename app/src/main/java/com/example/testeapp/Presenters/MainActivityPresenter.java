package com.example.testeapp.Presenters;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.testeapp.Models.Product;
import com.example.testeapp.Utils.IPaymentAsyncTask;
import com.example.testeapp.Utils.LioUtil;
import com.example.testeapp.Utils.PaymentAsyncTask;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cielo.sdk.order.payment.PaymentCode;

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
                    totalPrice
            ).execute();
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("ERRO PAYMENT",String.valueOf(e));
        }

    }
}
