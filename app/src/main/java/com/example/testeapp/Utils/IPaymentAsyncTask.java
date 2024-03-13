package com.example.testeapp.Utils;

import android.content.Context;

import com.example.testeapp.Models.PaymentModel;
import com.example.testeapp.Models.Product;

import java.util.ArrayList;

public interface IPaymentAsyncTask {
    void makePayment(ArrayList<Product> cart_list, String payments, String installments, Context context, LioUtil apiLio);
    void sendRequest(PaymentModel paymentModel, Context context);

}
