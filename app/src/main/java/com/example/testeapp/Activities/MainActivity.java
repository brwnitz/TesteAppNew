package com.example.testeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.testeapp.Adapters.ProductAdapter;
import com.example.testeapp.Models.Product;
import com.example.testeapp.Presenters.MainActivityInterface;
import com.example.testeapp.Presenters.MainActivityPresenter;
import com.example.testeapp.R;
import com.example.testeapp.Utils.LioUtil;
import com.example.testeapp.database.DatabaseHelper;
import com.example.testeapp.database.ProductDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    RecyclerView rv;
    ArrayList<Product> product_list,cart_list;
    MainActivityPresenter presenter;
    TextView tvFinalPrice;
    FloatingActionButton addButton, finishBuy;
    ProductAdapter productAdapter;
    Spinner spinnerPayment,spinnerInstallment;
    LioUtil lioApi;
    DatabaseHelper myDb;
    ProductDAO productDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lioApi = new LioUtil();
        lioApi.InitSDK(getApplicationContext());
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenter();
        spinnerPayment = findViewById(R.id.payments);
        spinnerInstallment = findViewById(R.id.installments);
        ArrayAdapter<CharSequence> adapterPayment = ArrayAdapter.createFromResource(this,R.array.payments, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterInstallment = ArrayAdapter.createFromResource(this,R.array.installments, android.R.layout.simple_spinner_item);
        adapterPayment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterInstallment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInstallment.setAdapter(adapterInstallment);
        spinnerPayment.setAdapter(adapterPayment);
        finishBuy = findViewById(R.id.btnFinishBuy);
        rv = findViewById(R.id.rvProduct);
        tvFinalPrice = findViewById(R.id.tvTotalPrice);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddProductActivity.class);
                startActivity(intent);
            }
        });
        myDb = new DatabaseHelper(MainActivity.this);
        productDAO = new ProductDAO(MainActivity.this);
        product_list = new ArrayList<Product>();
        cart_list = new ArrayList<Product>();
        displayData();
        productAdapter = new ProductAdapter(MainActivity.this,product_list,cart_list,this,this);
        rv.setAdapter(productAdapter);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        finishBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.makePayment(cart_list,spinnerPayment.getItemAtPosition(0).toString(),spinnerInstallment.getItemAtPosition(0).toString(),MainActivity.this,lioApi);
            }
        });
    }



    void displayData(){
        Cursor cursor = productDAO.readAllData();
        if(cursor.getCount() == 0){
            Log.d("ERROR","NAO TEM DADOS");
        }
        else {
            while(cursor.moveToNext()){
                Product product = new Product(cursor.getString(1),cursor.getString(2),cursor.getLong(3));
                product.setId(cursor.getInt(0));
                product_list.add(product);
            }
        }
    }

    @Override
    public void updateItemCart(Product product){
            for (Product productCart : cart_list){
                if (productCart.getId() == product.getId()) {
                productCart.setQuantity(product.getQuantity());
                tvFinalPrice.setText("R$"+presenter.totalValue(cart_list));
                return;
                }
            }
            if (cart_list.contains(product))
                return;
        cart_list.add(product);
        tvFinalPrice.setText("R$"+presenter.totalValue(cart_list));
    }
}