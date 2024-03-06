package com.example.testeapp.database;

import static com.example.testeapp.database.DatabaseHelper.COLUMN_IMAGE;
import static com.example.testeapp.database.DatabaseHelper.COLUMN_NAME;
import static com.example.testeapp.database.DatabaseHelper.COLUMN_PRICE;
import static com.example.testeapp.database.DatabaseHelper.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.testeapp.Models.Product;

public class ProductDAO {
    private SQLiteDatabase writer;
    private SQLiteDatabase reader;
    private DatabaseHelper helper;

    public ProductDAO(Context context){
        helper = new DatabaseHelper(context);
        writer = helper.getWritableDatabase();
        reader = helper.getReadableDatabase();

    }

    public boolean addProduct(Product product){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, product.getNome());
        cv.put(COLUMN_IMAGE,product.getImageUrl());
        cv.put(COLUMN_PRICE,product.getPrice());
        try {
            long result = writer.insert(TABLE_NAME,null,cv);
            return result != -1;
        }
        catch (Exception e){
            Log.d("ERRO ADD", String.valueOf(e));
            return false;
        }
    }

    public boolean updateProduct(Product product, String row_id){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, product.getNome());
        cv.put(COLUMN_IMAGE,product.getImageUrl());
        cv.put(COLUMN_PRICE,product.getPrice());
        try {
            long result = writer.update(TABLE_NAME,cv,"_id=?",new String[]{row_id});
            return result != -1;
        }
        catch (Exception e){
            Log.d("ERRO UPDATE", String.valueOf(e));
            return false;
        }
    }

    public boolean deleteProduct(Product product, String row_id){
        try {
            long result = writer.delete(TABLE_NAME,"_id=?", new String[]{row_id});
            return result != -1;
        }
        catch (Exception e){
            Log.d("ERRO DELETE", String.valueOf(e));
            return false;
        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = null;
        if (reader != null) {
            cursor = reader.rawQuery(query,null);
        }
        return cursor;
    }

}
