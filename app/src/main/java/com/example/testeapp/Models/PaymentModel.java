package com.example.testeapp.Models;

import cielo.sdk.order.payment.PaymentCode;

public class PaymentModel {

    private int id;
    private int installments;
    private String paymentCode;
    private long totalPrice;
    private String nsu;
    private String authCode;

    public PaymentModel(int installments, String paymentCode, long totalPrice, String nsu, String authCode) {
        this.installments = installments;
        this.paymentCode = paymentCode;
        this.totalPrice = totalPrice;
        this.nsu = nsu;
        this.authCode = authCode;
    }

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNsu() {
        return nsu;
    }

    public void setNsu(String nsu) {
        this.nsu = nsu;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
