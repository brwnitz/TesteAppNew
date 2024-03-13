package com.example.testeapp.Interfaces;

import com.example.testeapp.Models.PaymentModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface InterfaceRetrofit {
    @POST("product/data")
    Call<Void> postData(@Body PaymentModel paymentModels);
}
