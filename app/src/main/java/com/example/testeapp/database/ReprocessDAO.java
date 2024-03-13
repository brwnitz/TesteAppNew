package com.example.testeapp.database;

import static com.example.testeapp.database.DatabaseHelper.COLUMN_IMAGE;
import static com.example.testeapp.database.DatabaseHelper.COLUMN_NAME;
import static com.example.testeapp.database.DatabaseHelper.COLUMN_PRICE;
import static com.example.testeapp.database.DatabaseHelper.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.testeapp.Models.PaymentModel;
import com.example.testeapp.Models.Product;

public class ReprocessDAO {
    private SQLiteDatabase writer;
    private SQLiteDatabase reader;
    private DatabaseHelper helper;

    public ReprocessDAO(Context context){
        helper = new DatabaseHelper(context);
        writer = helper.getWritableDatabase();
        reader = helper.getReadableDatabase();

    }

    public boolean addPayment(PaymentModel paymentModel){
        ContentValues cv = new ContentValues();
        cv.put("reprocess_auth", paymentModel.getAuthCode());
        cv.put("reprocess_installments", paymentModel.getInstallments());
        cv.put("reprocess_nsu", paymentModel.getNsu());
        cv.put("reprocess_paycode", paymentModel.getPaymentCode().toString());
        cv.put("reprocess_price", paymentModel.getTotalPrice());
        try {
            long result = writer.insert("table_reprocess",null,cv);
            return result != -1;
        }
        catch (Exception e){
            Log.d("ERRO ADD", String.valueOf(e));
            return false;
        }
    }

    public boolean deletePayment(String row_id){
        try {
            long result = writer.delete("table_reprocess","id=?", new String[]{row_id});
            return result != -1;
        }
        catch (Exception e){
            Log.d("ERRO DELETE", String.valueOf(e));
            return false;
        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + "table_reprocess";
        Cursor cursor = null;
        if (reader != null) {
            cursor = reader.rawQuery(query,null);
        }
        return cursor;
    }

}
