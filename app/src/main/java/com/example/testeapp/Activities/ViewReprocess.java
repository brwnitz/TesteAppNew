package com.example.testeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.testeapp.Adapters.ProductAdapter;
import com.example.testeapp.Adapters.ReprocessAdapter;
import com.example.testeapp.Models.PaymentModel;
import com.example.testeapp.Models.Product;
import com.example.testeapp.Presenters.MainActivityPresenter;
import com.example.testeapp.R;
import com.example.testeapp.database.ReprocessDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewReprocess extends AppCompatActivity {

    ReprocessAdapter reprocessAdapter;
    ArrayList<PaymentModel> reprocessList;
    ReprocessDAO reprocessDAO;
    MainActivityPresenter presenter;
    RecyclerView rv;
    FloatingActionButton homeBtn, reprocessBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reprocessList = new ArrayList<>();
        presenter = new MainActivityPresenter();
        reprocessDAO = new ReprocessDAO(ViewReprocess.this);
        setContentView(R.layout.activity_view_reprocess);
        rv = findViewById(R.id.rvReprocess);
        reprocessBtn = findViewById(R.id.reprocessBtn);
        homeBtn = findViewById(R.id.homeReprocess);
        displayData();
        reprocessAdapter = new ReprocessAdapter(ViewReprocess.this,reprocessList);
        rv.setAdapter(reprocessAdapter);
        rv.setLayoutManager(new LinearLayoutManager(ViewReprocess.this));

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewReprocess.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        reprocessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryReprocess();
                Intent intent = new Intent(ViewReprocess.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    void displayData(){
        Cursor cursor = reprocessDAO.readAllData();
        if(cursor.getCount() == 0){
            Log.d("ERROR","NAO TEM DADOS");
        }
        else {
            while(cursor.moveToNext()){
                PaymentModel paymentModel = new PaymentModel(cursor.getInt(2),cursor.getString(4),cursor.getLong(5),cursor.getString(3), cursor.getString(1));
                paymentModel.setId(cursor.getInt(0));
                reprocessList.add(paymentModel);
            }
        }
    }

    void tryReprocess(){
        if (reprocessList.size() == 0){

        }
        for (int i = 0; i < reprocessList.size(); i++) {
            presenter.sendRequest(reprocessList.get(i),ViewReprocess.this);
            reprocessDAO.deletePayment(String.valueOf(reprocessList.get(i).getId()));
        }
    }
}