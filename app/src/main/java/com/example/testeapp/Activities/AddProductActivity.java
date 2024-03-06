package com.example.testeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.testeapp.Models.Product;
import com.example.testeapp.R;
import com.example.testeapp.database.ProductDAO;

public class AddProductActivity extends AppCompatActivity {
    EditText tvName, tvPrice, tvImage;
    Button btnSaveProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        tvName = findViewById(R.id.etName);
        tvPrice = findViewById(R.id.etPrice);
        tvImage = findViewById(R.id.etImage);
        btnSaveProd = findViewById(R.id.btnAddProduct);
        btnSaveProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDAO productDAO = new ProductDAO(AddProductActivity.this);
                Product product = new Product(tvName.getText().toString(),tvImage.getText().toString(),Long.parseLong(tvPrice.getText().toString()));
                productDAO.addProduct(product);
                Intent intent = new Intent(AddProductActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

