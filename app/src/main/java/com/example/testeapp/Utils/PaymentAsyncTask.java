package com.example.testeapp.Utils;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.testeapp.Models.PaymentModel;
import com.example.testeapp.Presenters.MainActivityInterface;

import cielo.orders.domain.Order;
import cielo.sdk.order.OrderManager;
import cielo.sdk.order.payment.PaymentCode;
import cielo.sdk.order.payment.PaymentError;
import cielo.sdk.order.payment.PaymentListener;

public class PaymentAsyncTask extends AsyncTask<Void, Void, Void> {

    LioUtil apiLio;
    int installments;
    long totalPrice;
PaymentCode paymentCode;
OrderManager orderManager;
String nsu, authCode;
Context context;
IPaymentAsyncTask mainInterface;
PaymentAsyncTask listener;
    public PaymentAsyncTask(LioUtil apiLio, int installments, PaymentCode paymentCode, Context context, long totalPrice, IPaymentAsyncTask mainInterface) {
        this.apiLio = apiLio;
        this.totalPrice = totalPrice;
        this.installments = installments;
        this.paymentCode = paymentCode;
        this.context = context;
        this.mainInterface = mainInterface;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            apiLio.initOrder();
            apiLio.addItemToPay(totalPrice,"001");
            apiLio.placeOrder();
            apiLio.payment(new PaymentListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onPayment(@NonNull Order order) {
                    order.markAsPaid();
                    apiLio.updateOrder(order);
                    String nsu = order.getPayments().get(0).getCieloCode();
                    String authCode = order.getPayments().get(0).getAuthCode();
                    PaymentModel paymentModel = new PaymentModel(installments,paymentCode.toString(),totalPrice,nsu,authCode);
                    mainInterface.sendRequest(paymentModel,context);
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(@NonNull PaymentError paymentError) {

                }
            },installments,paymentCode);

        }
        catch (Exception e){

        }
        return null;
    }
}
