package com.example.testeapp.Utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.UUID;

import cielo.orders.domain.CancellationRequest;
import cielo.orders.domain.CheckoutRequest;
import cielo.orders.domain.Credentials;
import cielo.orders.domain.Order;
import cielo.orders.domain.ResultOrders;
import cielo.orders.domain.product.PrimaryProduct;
import cielo.sdk.order.OrderManager;
import cielo.sdk.order.ServiceBindListener;
import cielo.sdk.order.cancellation.CancellationListener;
import cielo.sdk.order.payment.PaymentCode;
import cielo.sdk.order.payment.PaymentListener;

public class LioUtil {
    private Order order;
    private OrderManager orderManager;


    private ResultOrders resultOrders;
    private Credentials credentials;
    public volatile static LioUtil instance;

    public static LioUtil getInstance(){
        if (instance == null)
            instance = new LioUtil();
        return instance;
    }
    public ResultOrders getResultOrders() {
        return resultOrders;
    }

    public void setResultOrders(ResultOrders resultOrders) {
        this.resultOrders = resultOrders;
    }

    public void InitSDK(Context context){
        credentials = new Credentials("loIDeZOSihHEaDmutRHUPl3dxJzKIQTgHJ5yncMHqLUNf241vz","ZjpVpQiUNcO4BLUsnSmwqFCL8AExTqTEZ9kl9vm9lDAdnd3MOy");
        orderManager = new OrderManager(credentials, context);
        ServiceBindListener serviceBindListener = new ServiceBindListener() {
            @Override
            public void onServiceBound() {
                Log.e("onServiceBound","Conectado");
                setResultOrders(orderManager.retrieveOrders(200,0));

            }

            @Override
            public void onServiceBoundError(@NonNull Throwable throwable) {
                Log.e("onServiceBoundError",throwable.getMessage());
                Exception exception = new Exception("Falha de conexao com API da Cielo: " + throwable.getMessage());
                try{
                    throw exception;
                }
                catch (Exception e){
                    Log.d("ERRO",String.valueOf(e));
                }
            }

            @Override
            public void onServiceUnbound() {
                Log.e("onServiceUnbound","Desconectado");
            }
        };
        orderManager.bind(context,serviceBindListener);
    }

    public void initOrder() throws Exception{
        order = orderManager.createDraftOrder(String.valueOf(UUID.randomUUID()));
    }

    public void SetReference(String reference){

    }
    public void addItemToPay(long value, String sku) throws Exception{
        order.addItem(sku,"ORDER_APP_TESTE",value,1,"UNIDADE");
    }
    public void placeOrder(){
        orderManager.placeOrder(order);
    }
    public void payment(PaymentListener callbackPayment, int installments, PaymentCode paymentCode) throws Exception{
        CheckoutRequest request = new CheckoutRequest.Builder()
                .orderId(order.getId())
                .amount(order.getPrice())
                .installments(installments)
                .paymentCode(paymentCode)
                .build();
        orderManager.checkoutOrder(request,callbackPayment);
    }

    public void paymentMultCard(PaymentListener callbackPaymnet) throws Exception{
        CheckoutRequest request = new CheckoutRequest.Builder()
                .orderId(order.getId())
                .amount(order.getPrice())
                .installments(1)
                .paymentCode(PaymentCode.CREDITO_AVISTA)
                .build();
        orderManager.checkoutOrder(request, callbackPaymnet);
    }

    public void unBind(){
        orderManager.unbind();
    }

    public void updateOrder(Order order){
        this.order = order;
        orderManager.updateOrder(this.order);
    }

    public void cancellation(Order order, final CancellationListener cancellationListener){
        CancellationRequest request = new CancellationRequest.Builder()
                .orderId(order.getId())
                .authCode(order.getPayments().get(0).getAuthCode())
                .cieloCode(order.getPayments().get(0).getCieloCode())
                .value(order.getPayments().get(0).getAmount())
                .build();

        orderManager.cancelOrder(request, cancellationListener);
    }

    public void cancellationEmptyPayment(String orderId, long price, final CancellationListener cancellationListener, String nsu, String authcode){
        CancellationRequest request = new CancellationRequest.Builder()
                .orderId(orderId)
                .authCode(authcode)
                .cieloCode(nsu)
                .value(price)
                .build();
    }
    public ArrayList<PrimaryProduct> paymentsEnabled(Context ctxt){
        ArrayList<PrimaryProduct> paymentTypes = (ArrayList<PrimaryProduct>) orderManager.retrievePaymentType(ctxt);
        return  paymentTypes;
    }

    public Order getOrder() {
        return this.order;
    }

    public void updateResultOrders() {
        setResultOrders(orderManager.retrieveOrders(200, 0));
    }
}
